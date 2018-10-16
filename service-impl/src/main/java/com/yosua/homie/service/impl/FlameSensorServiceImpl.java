package com.yosua.homie.service.impl;

import com.yosua.homie.dao.FlameSensorRepository;
import com.yosua.homie.dao.LampRepository;
import com.yosua.homie.dao.UserRepository;
import com.yosua.homie.entity.constant.ApiPath;
import com.yosua.homie.entity.constant.enums.ResponseCode;
import com.yosua.homie.entity.dao.*;
import com.yosua.homie.libraries.exception.BusinessLogicException;
import com.yosua.homie.rest.web.model.request.FlameSensorRequest;
import com.yosua.homie.rest.web.model.response.*;
import com.yosua.homie.service.api.FlameSensorService;
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
public class FlameSensorServiceImpl implements FlameSensorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlameSensorServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    FlameSensorRepository flameSensorRepository;

    @Override
    public FlameSensor addFlameSensor(FlameSensorRequest flameSensorRequest){
        Validate.notNull(flameSensorRequest,"Flame Sensor Request to be added is required");
        FlameSensor newFlameSensor;
        User user = userRepository.findUserByHubsURL(flameSensorRequest.getHubURL());
        if(Objects.isNull(user))
        {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "Hub does not exist!");
        }
        FlameSensor existingFlameSensorWithSameName = flameSensorRepository.findFlameSensorByNameAndHubURL(flameSensorRequest.getName(), flameSensorRequest.getHubURL());
        if(!Objects.isNull(existingFlameSensorWithSameName)) {
            throw new BusinessLogicException(ResponseCode.DUPLICATE_DATA.getCode(),
                    "Flame Sensor with the same name already exist in your hub!");
        }
        newFlameSensor = new FlameSensorBuilder()
                .withHubURL(flameSensorRequest.getHubURL())
                .withName(flameSensorRequest.getName())
                .withStatus(flameSensorRequest.getStatus())
                .build();
        try{
            return flameSensorRepository.save(newFlameSensor);
        } catch (Exception e) {
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }
    }

    @Override
    public FlameSensorResponse toFlameSensorResponse(FlameSensor flameSensor){
        Validate.notNull(flameSensor,"Flame Sensor is required");
        return new FlameSensorResponseBuilder()
                .withId(flameSensor.getId())
                .withHubURL(flameSensor.getHubURL())
                .withName(flameSensor.getName())
                .withStatus(flameSensor.getStatus())
                .build();
    }
    @Override
    public List<FlameSensor> getAllUsersFlameSensor(String userID){
        Validate.notNull(userID,"UserID is required");
        User user = userRepository.findUserById(userID);
        if(Objects.isNull(user))
        {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "User does not exist!");
        }
        List<FlameSensor> FlameSensors = new ArrayList<>();
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
                FlameSensors.addAll(flameSensorRepository.findFlameSensorsByHubURL(URLs));
            }
        }
        return FlameSensors;
    }
    @Override
    public List<FlameSensorResponse> toFlameSensorResponse(List<FlameSensor> flameSensorList){
        Validate.notNull(flameSensorList, "AC List is required");
        List<FlameSensorResponse> flameSensorResponses= new ArrayList<>();
        for(FlameSensor flameSensor: flameSensorList){
            flameSensorResponses.add(new FlameSensorResponseBuilder()
                    .withHubURL(flameSensor.getHubURL())
                    .withName(flameSensor.getName())
                    .withStatus(flameSensor.getStatus())
                    .build());
        }
        return flameSensorResponses;
    }

    @Override
    public FlaskBaseResponse checkFlame(String deviceID){
        Validate.notNull(deviceID, "Device ID is required");
        FlameSensor flameSensor = flameSensorRepository.findFlameSensorById(deviceID);
        if(Objects.isNull(flameSensor)) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "Flame sensor does not exist!");
        }
        final String url = ApiPath.HTTP + flameSensor.getHubURL() + ApiPath.FLASK_CHECK_FLAME + deviceID + "/";
        LOGGER.info(url);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, FlaskBaseResponse.class);
    }
}
