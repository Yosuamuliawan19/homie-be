package com.yosua.homie.service.impl;

import com.yosua.homie.dao.LampRepository;
import com.yosua.homie.dao.UserRepository;
import com.yosua.homie.entity.constant.ApiPath;
import com.yosua.homie.entity.constant.enums.DeviceStatus;
import com.yosua.homie.entity.constant.enums.ResponseCode;
import com.yosua.homie.entity.dao.Hub;
import com.yosua.homie.entity.dao.Lamp;
import com.yosua.homie.entity.dao.LampBuilder;
import com.yosua.homie.entity.dao.User;
import com.yosua.homie.libraries.exception.BusinessLogicException;
import com.yosua.homie.rest.web.model.request.LampRequest;
import com.yosua.homie.rest.web.model.response.*;
import com.yosua.homie.service.api.LampService;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LampServiceImpl implements LampService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LampServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    LampRepository lampRepository;

    private Map<String, Pair<Timer, Timer>> deviceIDtoTimer=new HashMap<String, Pair<Timer, Timer>>();


    @Override
    public Lamp addLamp(LampRequest lampRequest){
        Validate.notNull(lampRequest,"Lamp Request to be added is required");
        Lamp newLamp;
        User user = userRepository.findUserByHubsURL(lampRequest.getHubURL());
        if(Objects.isNull(user))
        {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "Hub does not exist!");
        }
        Lamp existingLampWithSameName = lampRepository.findLampByNameAndHubURL(lampRequest.getName(), lampRequest.getHubURL());
        if(!Objects.isNull(existingLampWithSameName)) {
            throw new BusinessLogicException(ResponseCode.DUPLICATE_DATA.getCode(),
                    "Lamp with the same name already exist in your hub!");
        }
        newLamp = new LampBuilder()
                .withHubURL(lampRequest.getHubURL())
                .withName(lampRequest.getName())
                .withStatus(lampRequest.getStatus())
                .build();
        try{
            return lampRepository.save(newLamp);
        } catch (Exception e) {
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }
    }

    @Override
    public LampResponse toLampResponse(Lamp lamp){
        Validate.notNull(lamp,"Lamp is required");
        return new LampResponseBuilder()
                .withId(lamp.getId())
                .withHubURL(lamp.getHubURL())
                .withName(lamp.getName())
                .withStatus(lamp.getStatus())
                .build();
    }
    @Override
    public Lamp getLampFromDeviceID(String deviceID) {
        Validate.notNull(deviceID,"DeviceID is required");
        return lampRepository.findLampById(deviceID);
    }
    @Override
    public List<Lamp> getAllUsersLamp(String userID){
        Validate.notNull(userID,"UserID is required");
        User user = userRepository.findUserById(userID);
        if(Objects.isNull(user))
        {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "User does not exist!");
        }
        List<Lamp> Lamps = new ArrayList<>();
        List<Hub> userHubs = user.getHubs();
        List<String> hubsURL = new ArrayList<>();
        if(!Objects.isNull(userHubs) && !userHubs.isEmpty()){
            for(Hub hubs: userHubs)
            {
                hubsURL.add(hubs.getURL());
            }
        }
        if(!hubsURL.isEmpty()){
            for(String URLs: hubsURL)
            {
                Lamps.addAll(lampRepository.findLampsByHubURL(URLs));
            }
        }
        return Lamps;
    }

    @Override
    public List<LampResponse> toLampResponse(List<Lamp> lampList){
        Validate.notNull(lampList, "lamp List is required");
        List<LampResponse> lampResponses= new ArrayList<>();
        for(Lamp lamps: lampList){
            lampResponses.add(new LampResponseBuilder()
                    .withId(lamps.getId())
                    .withHubURL(lamps.getHubURL())
                    .withName(lamps.getName())
                    .withStatus(lamps.getStatus())
                    .build());
        }
        return lampResponses;
    }

    @Override
    public FlaskLampResponse turnOnLamp(String deviceID){
        Validate.notNull(deviceID, "Device ID is required");
        Lamp lamp = lampRepository.findLampById(deviceID);
        FlaskBaseResponse flaskBaseResponse;
        if(Objects.isNull(lamp)) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "Lamp does not exist!");
        }
        final String url = ApiPath.HTTP + lamp.getHubURL() + ApiPath.FLASK_TURN_ON_LAMP + "/" + deviceID + "/";
        LOGGER.info(url);
        RestTemplate restTemplate = new RestTemplate();
        try {
            flaskBaseResponse = restTemplate.getForObject(url, FlaskLampResponse.class);
        }catch (Exception e){
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }
        if(flaskBaseResponse.getCode().equals("200")){
            lamp.setStatus(DeviceStatus.ON);
            try{
                lamp = lampRepository.save(lamp);
            }catch (Exception e){
                throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                        ResponseCode.SYSTEM_ERROR.getMessage());
            }
        }
        return new FlaskLampResponseBuilder()
                .withCode(flaskBaseResponse.getCode())
                .withMessage(flaskBaseResponse.getMessage())
                .withHubURL(lamp.getHubURL())
                .withName(lamp.getName())
                .withStatus(lamp.getStatus())
                .build();
    }
    @Override
    public FlaskLampResponse turnOffLamp(String deviceID){
        Validate.notNull(deviceID, "Device ID is required");
        Lamp lamp = lampRepository.findLampById(deviceID);
        FlaskBaseResponse flaskBaseResponse;
        if(Objects.isNull(lamp)) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "Lamp does not exist!");
        }

        final String url = ApiPath.HTTP + lamp.getHubURL() + ApiPath.FLASK_TURN_OFF_LAMP + "/" + deviceID + "/";
        LOGGER.info(url);
        RestTemplate restTemplate = new RestTemplate();
        try {
            flaskBaseResponse = restTemplate.getForObject(url, FlaskLampResponse.class);
        }catch (Exception e)
        {
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }
        if(flaskBaseResponse.getCode().equals("200")){
            lamp.setStatus(DeviceStatus.OFF);
            try{
                lamp = lampRepository.save(lamp);
            }catch (Exception e){
                throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                        ResponseCode.SYSTEM_ERROR.getMessage());
            }
        }
        return new FlaskLampResponseBuilder()
                .withCode(flaskBaseResponse.getCode())
                .withMessage(flaskBaseResponse.getMessage())
                .withHubURL(lamp.getHubURL())
                .withName(lamp.getName())
                .withStatus(lamp.getStatus())
                .build();
    }
    @Override
    public void scheduledTurnOnLamp(String deviceID) {
        Validate.notNull(deviceID, "Device ID is required");
        Lamp lamp = lampRepository.findLampById(deviceID);
        LOGGER.info("turning on lamp");
        Lamp newLamp;
        FlaskBaseResponse flaskBaseResponse = null;
        if(Objects.isNull(lamp)) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "Lamp does not exist!");
        }
        lamp.setStatus(DeviceStatus.ON);
        try{
            newLamp = lampRepository.save(lamp);
        }catch (Exception e){
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }

        final String url = ApiPath.HTTP + lamp.getHubURL() + ApiPath.FLASK_TURN_ON_LAMP + "/" + deviceID + "/";
        LOGGER.info(url);
        RestTemplate restTemplate = new RestTemplate();
        try {
            if (lamp.getStatus().equals(DeviceStatus.OFF)) {
                flaskBaseResponse = restTemplate.getForObject(url, FlaskLampResponse.class);
            }
        }catch (Exception e){
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }

    }
    @Override
    public void  scheduledTurnOffLamp(String deviceID){
        LOGGER.info("turning off lamp");
        Validate.notNull(deviceID, "Device ID is required");
        Lamp lamp = lampRepository.findLampById(deviceID);
        Lamp newLamp;
        FlaskBaseResponse flaskBaseResponse;
        if(Objects.isNull(lamp)) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "Lamp does not exist!");
        }
        lamp.setStatus(DeviceStatus.OFF);
        try{
            lampRepository.save(lamp);
        }catch (Exception e){
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }

        final String url = ApiPath.HTTP + lamp.getHubURL() + ApiPath.FLASK_TURN_OFF_LAMP + "/" + deviceID + "/";
        LOGGER.info(url);
        RestTemplate restTemplate = new RestTemplate();
        try {
            if (lamp.getStatus().equals(DeviceStatus.OFF)) {
                flaskBaseResponse = restTemplate.getForObject(url, FlaskLampResponse.class);
            }
        }catch (Exception e)
        {
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }

    }


    @Override
    public void setTimerLamp(String deviceID, Date start, Date end){
        Validate.notNull(deviceID, "Device ID is required");
        Lamp lamp = lampRepository.findLampById(deviceID);
        if(Objects.isNull(lamp)) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "Lamp does not exist!");
        }
        LOGGER.info(start.toString() + " " + end.toString());
        lamp.setStartTimer(start);
        lamp.setEndTimer(end);
        lampRepository.save(lamp);
        Timer timerStart = new Timer();
        timerStart.schedule(new TimerTask() {
            public void run() {
                scheduledTurnOnLamp(deviceID);
            }
        }, start);
        Timer timerEnd = new Timer();
        timerEnd.schedule(new TimerTask() {
            public void run() {
                scheduledTurnOffLamp(deviceID);
            }
        }, end);
        if (deviceIDtoTimer.containsKey(deviceID)){
            Pair<Timer, Timer> timers = deviceIDtoTimer.get(deviceID);
            timers.getFirst().cancel();
            timers.getFirst().purge();
            timers.getSecond().cancel();
            timers.getSecond().purge();
            deviceIDtoTimer.remove(deviceID);
        }
        deviceIDtoTimer.put(deviceID, Pair.of(timerStart, timerEnd));
    }
}
