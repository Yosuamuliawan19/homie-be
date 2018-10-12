package com.yosua.homie.service.api;

import com.yosua.homie.entity.dao.AC;
import com.yosua.homie.entity.dao.RainSensor;
import com.yosua.homie.rest.web.model.request.ACRequest;
import com.yosua.homie.rest.web.model.request.RainSensorRequest;
import com.yosua.homie.rest.web.model.response.ACResponse;
import com.yosua.homie.rest.web.model.response.FlaskBaseResponse;
import com.yosua.homie.rest.web.model.response.RainSensorResponse;

import java.util.List;

public interface RainSensorService {
    RainSensor addRainSensor(RainSensorRequest rainSensorRequest);
    RainSensorResponse toRainSensorResponse(RainSensor rainSensor);
    List<RainSensor> getAllUsersRainSensor(String userID);
    List<RainSensorResponse> toRainSensorResponse(List<RainSensor> rainSensorList);
    FlaskBaseResponse checkRain(String deviceID);
}
