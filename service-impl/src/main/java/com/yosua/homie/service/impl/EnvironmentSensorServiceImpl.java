package com.yosua.homie.service.impl;

import com.yosua.homie.dao.ACRepository;
import com.yosua.homie.dao.EnvironmentSensorRepository;
import com.yosua.homie.dao.UserRepository;
import com.yosua.homie.entity.constant.enums.ResponseCode;
import com.yosua.homie.entity.dao.EnvironmentSensor;
import com.yosua.homie.entity.dao.EnvironmentSensorBuilder;
import com.yosua.homie.entity.dao.User;
import com.yosua.homie.libraries.exception.BusinessLogicException;
import com.yosua.homie.rest.web.model.request.EnvironmentSensorRequest;
import com.yosua.homie.rest.web.model.response.ACResponseBuilder;
import com.yosua.homie.rest.web.model.response.EnvironmentSensorResponse;
import com.yosua.homie.rest.web.model.response.EnvironmentSensorResponseBuilder;
import com.yosua.homie.rest.web.model.response.FlaskBaseResponse;
import com.yosua.homie.service.api.EnvironmentSensorService;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EnvironmentSensorServiceImpl implements EnvironmentSensorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ACServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    EnvironmentSensorRepository environmentSensorRepository;

    @Override
    public EnvironmentSensor addEnvironmentSensor(EnvironmentSensorRequest environmentSensorRequest){
        Validate.notNull(environmentSensorRequest,"EnvironmentSensor Request to be added is required");
        EnvironmentSensor newEnvironmentSensor;
        User user = userRepository.findUserByHubsURL(environmentSensorRequest.getHubURL());
        if(Objects.isNull(user))
        {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "Hub does not exist!");
        }
        EnvironmentSensor existingEnvironmentSensorWithSameURL = environmentSensorRepository.findEnvironmentSensorByHubURL(environmentSensorRequest.getHubURL());
        if(!Objects.isNull(existingEnvironmentSensorWithSameURL)) {
            throw new BusinessLogicException(ResponseCode.DUPLICATE_DATA.getCode(),
                    "EnvironmentSensor with the same url already exist in your hub!");
        }
        newEnvironmentSensor = new EnvironmentSensorBuilder()
                .withHubURL(environmentSensorRequest.getHubURL())
                .withTemperature(environmentSensorRequest.getTemperature())
                .withHumidity(environmentSensorRequest.getHumidity())
                .build();
        try{
            return environmentSensorRepository.save(newEnvironmentSensor);
        } catch (Exception e) {
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }
    }
    @Override
    public EnvironmentSensorResponse toEnvironmentSensorResponse(EnvironmentSensor environmentSensor){
        Validate.notNull(environmentSensor,"Environment Sensor is required");
        return new EnvironmentSensorResponseBuilder()
                .withHubURL(environmentSensor.getHubURL())
                .withHumidity(environmentSensor.getHumidity())
                .withTemperature(environmentSensor.getTemperature())
                .build();
    }

    @Override
    public List<EnvironmentSensorResponse> toEnvironmentSensorResponse(List<EnvironmentSensor> environmentSensorList){
        Validate.notNull(environmentSensorList, "EnvironmentSensorList List is required");
        List<EnvironmentSensorResponse> environmentSensorResponses= new ArrayList<>();
        for(EnvironmentSensor EnvironmentSensors: environmentSensorList){
            environmentSensorResponses.add(new EnvironmentSensorResponseBuilder()
                    .withHubURL(EnvironmentSensors.getHubURL())
                    .withHumidity(EnvironmentSensors.getHumidity())
                    .withTemperature(EnvironmentSensors.getTemperature())
                    .build());
        }
        return environmentSensorResponses;
    }

}
