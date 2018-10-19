package com.yosua.homie.service.impl;

import com.yosua.homie.dao.TVRepository;
import com.yosua.homie.dao.UserRepository;
import com.yosua.homie.entity.constant.ApiPath;
import com.yosua.homie.entity.constant.enums.DeviceStatus;
import com.yosua.homie.entity.constant.enums.ResponseCode;
import com.yosua.homie.entity.dao.Hub;
import com.yosua.homie.entity.dao.TV;
import com.yosua.homie.entity.dao.TVBuilder;
import com.yosua.homie.entity.dao.User;
import com.yosua.homie.libraries.exception.BusinessLogicException;
import com.yosua.homie.rest.web.model.request.TVRequest;
import com.yosua.homie.rest.web.model.response.*;
import com.yosua.homie.service.api.TVService;
import javafx.util.Pair;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TVServiceImpl implements TVService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TVServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    TVRepository tvRepository;

    @Override
    public TV addTV(TVRequest tvRequest){
        Validate.notNull(tvRequest,"TV Request to be added is required");
        TV newTV;
        User user = userRepository.findUserByHubsURL(tvRequest.getHubURL());
        if(Objects.isNull(user))
        {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "Hub does not exist!");
        }
        TV existingTVithSameName = tvRepository.findTVByNameAndHubURL(tvRequest.getName(), tvRequest.getHubURL());
        if(!Objects.isNull(existingTVithSameName)) {
            throw new BusinessLogicException(ResponseCode.DUPLICATE_DATA.getCode(),
                    "TV with the same name already exist in your hub!");
        }
        newTV = new TVBuilder()
                .withHubURL(tvRequest.getHubURL())
                .withName(tvRequest.getName())
                .withStatus(tvRequest.getStatus())
                .withChannelNumber(tvRequest.getChannelNumber())
                .withVolume(tvRequest.getVolume())
                .withMuted(tvRequest.getMuted())
                .build();
        try{
            LOGGER.info("added tv " + newTV.getHubURL());
            return tvRepository.save(newTV);
        } catch (Exception e) {
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }
    }

    @Override
    public List<TV> getAllUsersTV(String userID){
        Validate.notNull(userID,"UserID is required");
        User user = userRepository.findUserById(userID);
        if(Objects.isNull(user))
        {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "User does not exist!");
        }
        List<TV> TVs = new ArrayList<>();
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
                TVs.addAll(tvRepository.findTVByHubURL(URLs));
            }
        }
        return TVs;
    }

    @Override
    public TVResponse toTVResponse(TV tv){
        Validate.notNull(tv,"TV is required");
        return new TVResponseBuilder()
                .withId(tv.getId())
                .withHubURL(tv.getHubURL())
                .withName(tv.getName())
                .withStatus(tv.getStatus())
                .withChannelNumber(tv.getChannelNumber())
                .withVolume(tv.getVolume())
                .withMuted(tv.getMuted())
                .build();
    }

    @Override
    public TV getTVFromDeviceID(String deviceID) {
        Validate.notNull(deviceID,"DeviceID is required");
        return tvRepository.findTVById(deviceID);
    }

    @Override
    public List<TVResponse> toTVResponse(List<TV> TVList){
        Validate.notNull(TVList, "TV List is required");
        List<TVResponse> tvResponses= new ArrayList<>();
        for(TV TVs: TVList){
            tvResponses.add(new TVResponseBuilder()
                    .withId(TVs.getId())
                    .withHubURL(TVs.getHubURL())
                    .withName(TVs.getName())
                    .withStatus(TVs.getStatus())
                    .withChannelNumber(TVs.getChannelNumber())
                    .withVolume(TVs.getVolume())
                    .withMuted(TVs.getMuted())
                    .build());
        }
        return tvResponses;
    }
    @Override
    public FlaskTVResponse turnOnTV(String deviceID){
        Validate.notNull(deviceID, "Device ID is required");
        TV tv = tvRepository.findTVById(deviceID);
        TV newTV;
        FlaskBaseResponse flaskBaseResponse;
        if(Objects.isNull(tv)) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "TV does not exist!");
        }
        tv.setStatus(DeviceStatus.ON);
        try {
            newTV = tvRepository.save(tv);
        }catch (Exception e){
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }

        final String url = ApiPath.HTTP + tv.getHubURL() + ApiPath.FLASK_TURN_ON_TV + deviceID + "/";
        LOGGER.info(url);
        RestTemplate restTemplate = new RestTemplate();
        try {
            flaskBaseResponse = restTemplate.getForObject(url, FlaskTVResponse.class);
        }catch (Exception e){
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }
        return new FlaskTVResponseBuilder()
                .withCode(flaskBaseResponse.getCode())
                .withMessage(flaskBaseResponse.getMessage())
                .withName(newTV.getName())
                .withHubURL(newTV.getHubURL())
                .withStatus(newTV.getStatus())
                .withChannelNumber(newTV.getChannelNumber())
                .withVolume(newTV.getVolume())
                .withMuted(newTV.getMuted())
                .build();
    }
    @Override
    public FlaskTVResponse turnOffTV(String deviceID){
        Validate.notNull(deviceID, "Device ID is required");
        TV tv = tvRepository.findTVById(deviceID);
        TV newTV;
        FlaskBaseResponse flaskBaseResponse;
        if(Objects.isNull(tv)) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "TV does not exist!");
        }
        tv.setStatus(DeviceStatus.OFF);
        try{
            newTV = tvRepository.save(tv);
        }catch (Exception e){
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }
        final String url = ApiPath.HTTP + tv.getHubURL() + ApiPath.FLASK_TURN_OFF_TV+ "/" + deviceID + "/";
        LOGGER.info(url);
        RestTemplate restTemplate = new RestTemplate();
        try{
            flaskBaseResponse = restTemplate.getForObject(url, FlaskTVResponse.class);
        }catch (Exception e){
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }
        return new FlaskTVResponseBuilder()
                .withCode(flaskBaseResponse.getCode())
                .withMessage(flaskBaseResponse.getMessage())
                .withName(newTV.getName())
                .withHubURL(newTV.getHubURL())
                .withStatus(newTV.getStatus())
                .withChannelNumber(newTV.getChannelNumber())
                .withVolume(newTV.getVolume())
                .withMuted(newTV.getMuted())
                .build();
    }
    @Override
    public FlaskTVResponse volumeUpTV(String deviceID){
        Validate.notNull(deviceID, "Device ID is required");
        TV tv = tvRepository.findTVById(deviceID);
        TV newTV;
        FlaskBaseResponse flaskBaseResponse;
        if(Objects.isNull(tv)) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "TV does not exist!");
        }
        tv.setVolume(tv.getVolume()+1);
        try {
            newTV = tvRepository.save(tv);
        }catch (Exception e){
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }
        final String url = ApiPath.HTTP + tv.getHubURL() + ApiPath.FLASK_VOLUME_UP_TV + "/" + deviceID + "/";
        LOGGER.info(url);
        RestTemplate restTemplate = new RestTemplate();

        try{
            flaskBaseResponse = restTemplate.getForObject(url, FlaskTVResponse.class);
        }catch (Exception e){
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }
        return new FlaskTVResponseBuilder()
                .withCode(flaskBaseResponse.getCode())
                .withMessage(flaskBaseResponse.getMessage())
                .withName(newTV.getName())
                .withHubURL(newTV.getHubURL())
                .withStatus(newTV.getStatus())
                .withChannelNumber(newTV.getChannelNumber())
                .withVolume(newTV.getVolume())
                .withMuted(newTV.getMuted())
                .build();
    }
    @Override
    public FlaskTVResponse volumeDownTV(String deviceID){
        Validate.notNull(deviceID, "Device ID is required");
        TV tv = tvRepository.findTVById(deviceID);
        TV newTV;
        FlaskBaseResponse flaskBaseResponse;
        if(Objects.isNull(tv)) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "TV does not exist!");
        }
        tv.setVolume(tv.getVolume()-1);
        try {
            newTV = tvRepository.save(tv);
        }catch (Exception e){
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }
        final String url = ApiPath.HTTP + tv.getHubURL() + ApiPath.FLASK_VOLUME_DOWN_TV + "/"+ deviceID + "/";
        LOGGER.info(url);
        RestTemplate restTemplate = new RestTemplate();

        try{
            flaskBaseResponse = restTemplate.getForObject(url, FlaskTVResponse.class);
        }catch (Exception e){
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }
        return new FlaskTVResponseBuilder()
                .withCode(flaskBaseResponse.getCode())
                .withMessage(flaskBaseResponse.getMessage())
                .withName(newTV.getName())
                .withHubURL(newTV.getHubURL())
                .withStatus(newTV.getStatus())
                .withChannelNumber(newTV.getChannelNumber())
                .withVolume(newTV.getVolume())
                .withMuted(newTV.getMuted())
                .build();
    }
    @Override
    public FlaskTVResponse programUpTV(String deviceID){
        Validate.notNull(deviceID, "Device ID is required");
        TV tv = tvRepository.findTVById(deviceID);
        TV newTV;
        FlaskBaseResponse flaskBaseResponse;
        if(Objects.isNull(tv)) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "TV does not exist!");
        }
        tv.setVolume(tv.getChannelNumber()+1);
        try{
            newTV = tvRepository.save(tv);
        }catch (Exception e){
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }
        final String url = ApiPath.HTTP + tv.getHubURL() + ApiPath.FLASK_PROGRAM_UP_TV + "/"+ deviceID + "/";
        LOGGER.info(url);
        RestTemplate restTemplate = new RestTemplate();

        try {
            flaskBaseResponse = restTemplate.getForObject(url, FlaskTVResponse.class);
        }catch (Exception e){
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }
        return new FlaskTVResponseBuilder()
                .withCode(flaskBaseResponse.getCode())
                .withMessage(flaskBaseResponse.getMessage())
                .withName(newTV.getName())
                .withHubURL(newTV.getHubURL())
                .withStatus(newTV.getStatus())
                .withChannelNumber(newTV.getChannelNumber())
                .withVolume(newTV.getVolume())
                .withMuted(newTV.getMuted())
                .build();
    }
    @Override
    public FlaskTVResponse programDownTV(String deviceID){
        Validate.notNull(deviceID, "Device ID is required");
        TV tv = tvRepository.findTVById(deviceID);
        TV newTV;
        FlaskBaseResponse flaskBaseResponse;
        if(Objects.isNull(tv)) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "TV does not exist!");
        }
        tv.setVolume(tv.getChannelNumber()-1);
        try{
            newTV = tvRepository.save(tv);
        }catch (Exception e){
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }
        final String url = ApiPath.HTTP + tv.getHubURL() + ApiPath.FLASK_PROGRAM_DOWN_TV + "/"+ deviceID + "/";
        LOGGER.info(url);
        RestTemplate restTemplate = new RestTemplate();
        try {
            flaskBaseResponse = restTemplate.getForObject(url, FlaskTVResponse.class);
        }catch (Exception e){
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }

        return new FlaskTVResponseBuilder()
                .withCode(flaskBaseResponse.getCode())
                .withMessage(flaskBaseResponse.getMessage())
                .withName(newTV.getName())
                .withHubURL(newTV.getHubURL())
                .withStatus(newTV.getStatus())
                .withChannelNumber(newTV.getChannelNumber())
                .withVolume(newTV.getVolume())
                .withMuted(newTV.getMuted())
                .build();

    }

    @Override
    public FlaskTVResponse muteTV(String deviceID){
        Validate.notNull(deviceID, "Device ID is required");
        TV tv = tvRepository.findTVById(deviceID);
        TV newTV;
        FlaskBaseResponse flaskBaseResponse;
        if(Objects.isNull(tv)) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "TV does not exist!");
        }
        tv.setMuted(!tv.getMuted());
        try {
            newTV = tvRepository.save(tv);
        }catch (Exception e){
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }
        final String url = ApiPath.HTTP + tv.getHubURL() + ApiPath.FLASK_MUTE_TV + "/"+ deviceID + "/";
        LOGGER.info(url);
        RestTemplate restTemplate = new RestTemplate();
        try{
            flaskBaseResponse = restTemplate.getForObject(url, FlaskTVResponse.class);
        }catch (Exception e){
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }

        return new FlaskTVResponseBuilder()
                .withCode(flaskBaseResponse.getCode())
                .withMessage(flaskBaseResponse.getMessage())
                .withName(newTV.getName())
                .withHubURL(newTV.getHubURL())
                .withStatus(newTV.getStatus())
                .withChannelNumber(newTV.getChannelNumber())
                .withVolume(newTV.getVolume())
                .withMuted(newTV.getMuted())
                .build();
    }

    @Override
    public void scheduledTurnOnTV(String deviceID) {
        Validate.notNull(deviceID, "Device ID is required");
        TV tv = tvRepository.findTVById(deviceID);
        LOGGER.info("turning on TV");
        TV newTV;
        FlaskBaseResponse flaskBaseResponse = null;
        if(Objects.isNull(tv)) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "TV does not exist!");
        }
        tv.setStatus(DeviceStatus.ON);
        try{
            newTV = tvRepository.save(tv);
        }catch (Exception e){
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }

        final String url = ApiPath.HTTP + tv.getHubURL() + ApiPath.FLASK_TURN_ON_TV + "/" + deviceID + "/";
        LOGGER.info(url);
        RestTemplate restTemplate = new RestTemplate();
        try {
            if (tv.getStatus().equals(DeviceStatus.OFF)) {
                flaskBaseResponse = restTemplate.getForObject(url, FlaskTVResponse.class);
            }
        }catch (Exception e){
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }

    }
    @Override
    public void  scheduledTurnOffTV(String deviceID){
        LOGGER.info("turning off TV");
        Validate.notNull(deviceID, "Device ID is required");
        TV tv = tvRepository.findTVById(deviceID);
        TV newTV;
        FlaskBaseResponse flaskBaseResponse;
        if(Objects.isNull(tv)) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "TV does not exist!");
        }
        tv.setStatus(DeviceStatus.OFF);
        try{
            tvRepository.save(tv);
        }catch (Exception e){
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }

        final String url = ApiPath.HTTP + tv.getHubURL() + ApiPath.FLASK_TURN_OFF_TV + "/" + deviceID + "/";
        LOGGER.info(url);
        RestTemplate restTemplate = new RestTemplate();
        try {
            if (tv.getStatus().equals(DeviceStatus.OFF)) {
                flaskBaseResponse = restTemplate.getForObject(url, FlaskTVResponse.class);
            }
        }catch (Exception e)
        {
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }

    }

    Map<String, Pair<Timer, Timer>> deviceIDtoTimer = new HashMap<String, Pair<Timer, Timer>>();

    @Override
    public void setTimerTV(String deviceID, Date start, Date end){
        Validate.notNull(deviceID, "Device ID is required");
        TV tv = tvRepository.findTVById(deviceID);
        FlaskBaseResponse flaskBaseResponse;
        if(Objects.isNull(tv)) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "TV does not exist!");
        }
        LOGGER.info(start.toString() + " " + end.toString());
        tv.setStartTimer(start);
        tv.setEndTimer(end);
        tvRepository.save(tv);

        //Now create the time and schedule it
        Timer timerStart = new Timer();
        //Use this if you want to execute it once
        timerStart.schedule(new TimerTask() {
            public void run() {
                scheduledTurnOnTV(deviceID);
            }
        }, start);

        //Now create the time and schedule it
        Timer timerEnd = new Timer();
        //Use this if you want to execute it once
        timerEnd.schedule(new TimerTask() {
            public void run() {
                scheduledTurnOffTV(deviceID);
            }
        }, end);

        if (deviceIDtoTimer.containsKey(deviceID)){
            Pair<Timer, Timer> timers = deviceIDtoTimer.get(deviceID);
            timers.getKey().cancel();
            timers.getKey().purge();
            timers.getValue().cancel();
            timers.getValue().purge();
        }

        deviceIDtoTimer.put(deviceID, new Pair<>(timerStart, timerEnd));
    }
}
