package com.yosua.homie.service.api;

import com.yosua.homie.entity.dao.SmokeSensor;
import com.yosua.homie.rest.web.model.request.SmokeSensorRequest;
import com.yosua.homie.rest.web.model.response.SmokeSensorResponse;
import com.yosua.homie.rest.web.model.response.FlaskBaseResponse;

import java.util.List;

public interface SmokeSensorService {
    SmokeSensor addSmokeSensor(SmokeSensorRequest smokeSensorRequest);
    SmokeSensorResponse toSmokeSensorResponse(SmokeSensor smokeSensor);
    List<SmokeSensor> getAllUsersSmokeSensor(String userID);
    List<SmokeSensorResponse> toSmokeSensorResponse (List<SmokeSensor> smokeSensorList);
    FlaskBaseResponse checkSmoke(String deviceID);
}
