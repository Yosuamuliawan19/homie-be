package com.yosua.homie.service.impl;

import com.yosua.homie.dao.EnvironmentSensorRepository;
import com.yosua.homie.dao.UserRepository;
import com.yosua.homie.entity.constant.ApiPath;
import com.yosua.homie.entity.constant.enums.EnvironmentDataType;
import com.yosua.homie.entity.constant.enums.ResponseCode;
import com.yosua.homie.entity.dao.EnvironmentSensor;
import com.yosua.homie.entity.dao.EnvironmentSensorBuilder;
import com.yosua.homie.entity.dao.User;
import com.yosua.homie.libraries.exception.BusinessLogicException;
import com.yosua.homie.rest.web.model.request.EnvironmentSensorRequest;
import com.yosua.homie.rest.web.model.response.EnvironmentSensorResponse;
import com.yosua.homie.rest.web.model.response.EnvironmentSensorResponseBuilder;
import com.yosua.homie.service.api.EnvironmentSensorService;
import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EnvironmentSensorServiceImpl implements EnvironmentSensorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentSensorServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    EnvironmentSensorRepository environmentSensorRepository;

    @Override
    public EnvironmentSensor addEnvironmentSensor(EnvironmentSensorRequest environmentSensorRequest){
        Validate.notNull(environmentSensorRequest,"EnvironmentSensor Request to be added is required");
        EnvironmentSensor newEnvironmentSensor;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        Date serverTime;
        try {
           serverTime  = format.parse(environmentSensorRequest.getServerTime());
           LOGGER.info(serverTime.toString());
        } catch (ParseException e) {
            throw new BusinessLogicException(ResponseCode.INVALID_DATE_FORMAT.getCode(),
                    ResponseCode.INVALID_DATE_FORMAT.getMessage());
        }
        User user = userRepository.findUserByHubsURL(environmentSensorRequest.getHubURL());
        if(Objects.isNull(user))
        {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "Hub does not exist!");
        }
        EnvironmentSensor existingEnvironmentSensorWithSameURLAndServerTime = environmentSensorRepository.findEnvironmentSensorByHubURLAndServerTime
                (environmentSensorRequest.getHubURL(), serverTime);
        if(!Objects.isNull(existingEnvironmentSensorWithSameURLAndServerTime)) {
            throw new BusinessLogicException(ResponseCode.DUPLICATE_DATA.getCode(),
                    "Environment Record already exist!");
        }
        newEnvironmentSensor = new EnvironmentSensorBuilder()
                .withHubURL(environmentSensorRequest.getHubURL())
                .withTemperature(environmentSensorRequest.getTemperature())
                .withHumidity(environmentSensorRequest.getHumidity())
                .withServerTime(serverTime)
                .build();
        try{
            return environmentSensorRepository.save(newEnvironmentSensor);
        } catch (Exception e) {
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }
    }
//    @Override
//    public EnvironmentSensorResponse toEnvironmentSensorResponse(EnvironmentSensor environmentSensor){
//        Validate.notNull(environmentSensor,"Environment Sensor is required");
//        return new EnvironmentSensorResponseBuilder()
//                .withHubURL(environmentSensor.getHubURL())
//                .withHumidity(environmentSensor.getHumidity())
//                .withTemperature(environmentSensor.getTemperature())
//                .withServerTime(environmentSensor.getServerTime())
//                .build();
//    }
//
//    @Override
//    public List<EnvironmentSensorResponse> toEnvironmentSensorResponse(List<EnvironmentSensor> environmentSensorList){
//        Validate.notNull(environmentSensorList, "EnvironmentSensorList List is required");
//        List<EnvironmentSensorResponse> environmentSensorResponses= new ArrayList<>();
//        for(EnvironmentSensor EnvironmentSensors: environmentSensorList){
//            environmentSensorResponses.add(new EnvironmentSensorResponseBuilder()
//                    .withHubURL(EnvironmentSensors.getHubURL())
//                    .withHumidity(EnvironmentSensors.getHumidity())
//                    .withTemperature(EnvironmentSensors.getTemperature())
//                    .withServerTime(EnvironmentSensors.getServerTime())
//                    .build());
//        }
//        return environmentSensorResponses;
//    }

    @Override
    public List<Double> getTemperatureDataFromLastWeek(){
        Date currentTime = new DateTime().toDate();
        Date startTime;
        Date endTime;
        ArrayList<Double> averageTemperaturesPerDay = new ArrayList<>();
        for(int i=0;i<7;i++){
            endTime = new DateTime(currentTime).minusDays(7-i-1).toDate();
            startTime = new DateTime(currentTime).minusDays(7-i).toDate();
            LOGGER.info(startTime + "    " + endTime);
            averageTemperaturesPerDay.add(getAverageData(endTime,startTime, EnvironmentDataType.TEMPERATURE));
        }
        LOGGER.info(averageTemperaturesPerDay.toString());
        return averageTemperaturesPerDay;
    }

    @Override
    public List<Double> getHumidityDataFromLastWeek(){
        Date currentTime = new DateTime().toDate();
        Date startTime;
        Date endTime;
        ArrayList<Double> averageHumidityPerDay = new ArrayList<>();
        for(int i=0;i<7;i++) {
            endTime = new DateTime(currentTime).minusDays(7 - i - 1).toDate();
            startTime = new DateTime(currentTime).minusDays(7 - i).toDate();
            LOGGER.info(startTime + "    " + endTime);
            averageHumidityPerDay.add(getAverageData(endTime, startTime, EnvironmentDataType.HUMIDITY));
        }
        LOGGER.info(averageHumidityPerDay.toString());
        return averageHumidityPerDay;
    }

    @Override
    public Double getAverageData(Date endTime, Date startTime, EnvironmentDataType environmentDataType){
        Double sum = 0.0;
        double average = 0.0;
        List<EnvironmentSensor> environmentData = environmentSensorRepository.findEnvironmentSensorByServerTimeBetween
                (endTime.toInstant(),startTime.toInstant());
        if(!Objects.isNull(environmentData) && !environmentData.isEmpty()){
            if(environmentDataType.equals(EnvironmentDataType.TEMPERATURE)){
                for(EnvironmentSensor data: environmentData){
                    LOGGER.info(data.toString());
                        sum += data.getTemperature();
                }
            }
            else{
                for(EnvironmentSensor data: environmentData) {
                    LOGGER.info(data.toString());
                    sum += data.getHumidity();
                }
            }
            average = sum/environmentData.size();
        }
        LOGGER.info("AVERAGE: " + average);
        return average;
    }

    @Override
    public List<Double> getCurrentEnvironmentData(String hubURL){
        EnvironmentSensorResponse environmentSensorResponse;
        List<Double> data = new ArrayList<>();
        final String url = ApiPath.HTTP + hubURL + ApiPath.FLASK_GET_CURRENT_ENVIRONMENT_DATA;
        LOGGER.info(url);
        RestTemplate restTemplate = new RestTemplate();
        try {
            environmentSensorResponse = restTemplate.getForObject(url, EnvironmentSensorResponse.class);
        }catch (Exception e){
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }
        data.add(environmentSensorResponse.getTemperature());
        data.add(environmentSensorResponse.getHumidity());
        return data;
    }
}
