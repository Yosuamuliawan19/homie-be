package com.yosua.homie.service.impl;

import com.yosua.homie.dao.SmokeSensorRepository;
import com.yosua.homie.dao.UserRepository;
import com.yosua.homie.entity.constant.ApiPath;
import com.yosua.homie.entity.constant.enums.ResponseCode;
import com.yosua.homie.entity.dao.Hub;
import com.yosua.homie.entity.dao.SmokeSensor;
import com.yosua.homie.entity.dao.SmokeSensorBuilder;
import com.yosua.homie.entity.dao.User;
import com.yosua.homie.libraries.exception.BusinessLogicException;
import com.yosua.homie.rest.web.model.request.SmokeSensorRequest;
import com.yosua.homie.rest.web.model.response.FlaskBaseResponse;
import com.yosua.homie.rest.web.model.response.SmokeSensorResponse;
import com.yosua.homie.rest.web.model.response.SmokeSensorResponseBuilder;
import com.yosua.homie.service.api.SmokeSensorService;
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
public class SmokeSensorServiceImpl implements SmokeSensorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmokeSensorServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    SmokeSensorRepository smokeSensorRepository;

    @Override
    public SmokeSensor addSmokeSensor(SmokeSensorRequest smokeSensorRequest)
    {
        Validate.notNull(smokeSensorRequest,"Smoke sensor Request to be added is required");
        SmokeSensor newSmokeSensor;
        User user = userRepository.findUserByHubsURL(smokeSensorRequest.getHubURL());
        if(Objects.isNull(user))
        {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "Hub does not exist!");
        }
        SmokeSensor existingSmokeSensorWithSameName = smokeSensorRepository.findSmokeSensorByNameAndHubURL(smokeSensorRequest.getName(), smokeSensorRequest.getHubURL());
        if(!Objects.isNull(existingSmokeSensorWithSameName)){
            throw new BusinessLogicException(ResponseCode.DUPLICATE_DATA.getCode(),
                    "Smoke sensor with the same name already exist in your hub!");
        }
        newSmokeSensor = new SmokeSensorBuilder()
                .withHubURL(smokeSensorRequest.getHubURL())
                .withName(smokeSensorRequest.getName())
                .withStatus(smokeSensorRequest.getStatus())
                .build();
        smokeSensorRepository.save(newSmokeSensor);
        try{
            return smokeSensorRepository.save(newSmokeSensor);
        } catch (Exception e){
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }
    }

    @Override
    public SmokeSensorResponse toSmokeSensorResponse(SmokeSensor smokeSensor){
        Validate.notNull(smokeSensor,"Smoke Sensor is required");
        return new SmokeSensorResponseBuilder()
                .withHubURL(smokeSensor.getHubURL())
                .withName(smokeSensor.getName())
                .withStatus(smokeSensor.getStatus())
                .build();
    }

    @Override
    public List<SmokeSensor> getAllUsersSmokeSensor(String userID){
        Validate.notNull(userID, "User ID is required");
        User user = userRepository.findUserById(userID);
        if(Objects.isNull(user))
        {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(), "User does not exist");
        }
        List<SmokeSensor> SmokeSensors = new ArrayList<>();
        List<Hub> userHubs = user.getHubs();
        List<String> hubsURL = new ArrayList<>();
        if(!Objects.isNull(userHubs) && !userHubs.isEmpty()){
            for(Hub hubs: userHubs)
            {
                hubsURL.add(hubs.getURL());
            }
        }
        if(!hubsURL.isEmpty()) {
            for (String URLs : hubsURL) {
                SmokeSensors.addAll(smokeSensorRepository.findSmokeSensorsByHubURL(URLs));
            }
        }
        return SmokeSensors;
    }

    @Override
    public List<SmokeSensorResponse> toSmokeSensorResponse(List<SmokeSensor> smokeSensorList){
        Validate.notNull(smokeSensorList, "Gas Sensor list is required");
        List<SmokeSensorResponse> smokeSensorResponses = new ArrayList<>();
        for (SmokeSensor SmokeSensors: smokeSensorList)
        {
            smokeSensorResponses.add(new SmokeSensorResponseBuilder()
                    .withHubURL(SmokeSensors.getHubURL())
                    .withName(SmokeSensors.getName())
                    .withStatus(SmokeSensors.getStatus())
                    .build());
        }
        return smokeSensorResponses;
    }


    @Override
    public FlaskBaseResponse checkSmoke(String deviceID)
    {
        Validate.notNull(deviceID, "Device ID is required");
        SmokeSensor smokeSensor = smokeSensorRepository.findSmokeSensorById(deviceID);
        if(Objects.isNull(smokeSensor))
        {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "Smoke sensor does not exist");
        }
        final String url = ApiPath.HTTP + smokeSensor.getHubURL() + ApiPath.FLASK_CHECK_GAS  + "/"+ deviceID + "/";
        LOGGER.info(url);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, FlaskBaseResponse.class);

    }
}
