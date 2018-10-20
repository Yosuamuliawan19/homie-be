package com.yosua.homie.service.api;

import com.yosua.homie.entity.constant.enums.EnvironmentDataType;
import com.yosua.homie.entity.dao.EnvironmentSensor;
import com.yosua.homie.rest.web.model.request.EnvironmentSensorRequest;
import com.yosua.homie.rest.web.model.response.EnvironmentSensorResponse;
import com.yosua.homie.rest.web.model.response.FlaskBaseResponse;
import java.util.Date;
import java.util.List;

public interface EnvironmentSensorService {
    EnvironmentSensor addEnvironmentSensor(EnvironmentSensorRequest environmentSensorRequest);
    EnvironmentSensorResponse toEnvironmentSensorResponse(EnvironmentSensor environmentSensor);
    List<EnvironmentSensorResponse> toEnvironmentSensorResponse(List<EnvironmentSensor> environmentSensorList);
    Double getAverageData(Date startTime, Date endTime, EnvironmentDataType environmentDataType);
    List<Double> getTemperatureDataFromLastWeek();
    List<Double> getHumidityDataFromLastWeek();
}
