package com.yosua.homie.service.api;

import com.yosua.homie.entity.dao.EnvironmentSensor;
import com.yosua.homie.rest.web.model.request.EnvironmentSensorRequest;
import com.yosua.homie.rest.web.model.response.EnvironmentSensorResponse;
import com.yosua.homie.rest.web.model.response.FlaskBaseResponse;

import java.util.List;

public interface EnvironmentSensorService {
    EnvironmentSensor addEnvironmentSensor(EnvironmentSensorRequest environmentSensorRequest);
    EnvironmentSensorResponse toEnvironmentSensorResponse(EnvironmentSensor environmentSensor);
    List<EnvironmentSensorResponse> toEnvironmentSensorResponse(List<EnvironmentSensor> environmentSensorList);
}
