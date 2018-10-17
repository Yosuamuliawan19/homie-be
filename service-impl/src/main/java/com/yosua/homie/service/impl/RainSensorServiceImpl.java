package com.yosua.homie.service.impl;

import com.yosua.homie.dao.RainSensorRepository;
import com.yosua.homie.dao.UserRepository;
import com.yosua.homie.entity.constant.ApiPath;
import com.yosua.homie.entity.constant.enums.ResponseCode;
import com.yosua.homie.entity.dao.Hub;
import com.yosua.homie.entity.dao.RainSensor;
import com.yosua.homie.entity.dao.RainSensorBuilder;
import com.yosua.homie.entity.dao.User;
import com.yosua.homie.libraries.exception.BusinessLogicException;
import com.yosua.homie.rest.web.model.request.RainSensorRequest;
import com.yosua.homie.rest.web.model.response.FlaskBaseResponse;
import com.yosua.homie.rest.web.model.response.RainSensorResponse;
import com.yosua.homie.rest.web.model.response.RainSensorResponseBuilder;
import com.yosua.homie.service.api.RainSensorService;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RainSensorServiceImpl implements RainSensorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RainSensorServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    RainSensorRepository rainSensorRepository;

    @Override
    public RainSensor addRainSensor(RainSensorRequest rainSensorRequest){
        Validate.notNull(rainSensorRequest,"Rain sensor Request to be added is required");
        RainSensor newRainSensor;
        User user = userRepository.findUserByHubsURL(rainSensorRequest.getHubURL());
        if(Objects.isNull(user))
        {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "Hub does not exist!");
        }
        RainSensor existingRainSensorWithSameName = rainSensorRepository.findRainSensorByNameAndHubURL(rainSensorRequest.getName(), rainSensorRequest.getHubURL());
        if(!Objects.isNull(existingRainSensorWithSameName)) {
            throw new BusinessLogicException(ResponseCode.DUPLICATE_DATA.getCode(),
                    "Rain sensor with the same name already exist in your hub!");
        }
        newRainSensor = new RainSensorBuilder()
                .withHubURL(rainSensorRequest.getHubURL())
                .withName(rainSensorRequest.getName())
                .withStatus(rainSensorRequest.getStatus())
                .build();
        try{
            return rainSensorRepository.save(newRainSensor);
        } catch (Exception e) {
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }
    }

    @Override
    public RainSensorResponse toRainSensorResponse(RainSensor rainSensor){
        Validate.notNull(rainSensor,"Rain Sensor is required");
        return new RainSensorResponseBuilder()
                .withId(rainSensor.getId())
                .withHubURL(rainSensor.getHubURL())
                .withName(rainSensor.getName())
                .withStatus(rainSensor.getStatus())
                .build();
    }

    @Override
    public List<RainSensor> getAllUsersRainSensor(String userID){
        Validate.notNull(userID,"UserID is required");
        User user = userRepository.findUserById(userID);
        if(Objects.isNull(user))
        {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "User does not exist!");
        }
        List<RainSensor> RainSensors = new ArrayList<>();
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
                RainSensors.addAll(rainSensorRepository.findRainSensorsByHubURL(URLs));
            }
        }
        return RainSensors;
    }

    @Override
    public List<RainSensorResponse> toRainSensorResponse(List<RainSensor> rainSensorList){
        Validate.notNull(rainSensorList, "Rain Sensor List is required");
        List<RainSensorResponse> rainSensorResponses= new ArrayList<>();
        for(RainSensor RainSensors: rainSensorList){
            rainSensorResponses.add(new RainSensorResponseBuilder()
                    .withId(RainSensors.getId())
                    .withHubURL(RainSensors.getHubURL())
                    .withName(RainSensors.getName())
                    .withStatus(RainSensors.getStatus())
                    .build());
        }
        return rainSensorResponses;
    }

    @Override
    public FlaskBaseResponse checkRain(String deviceID){
        Validate.notNull(deviceID, "Device ID is required");
        RainSensor rainSensor = rainSensorRepository.findRainSensorById(deviceID);
        if(Objects.isNull(rainSensor)) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "Rain sensor does not exist!");
        }
        final String url = ApiPath.HTTP + rainSensor.getHubURL() + ApiPath.FLASK_CHECK_RAIN + deviceID + "/";
        LOGGER.info(url);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, FlaskBaseResponse.class);
    }
}
