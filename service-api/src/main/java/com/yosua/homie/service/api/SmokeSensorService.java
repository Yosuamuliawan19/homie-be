package com.yosua.homie.service.api;

import com.yosua.homie.entity.dao.SmokeSensor;
import com.yosua.homie.rest.web.model.request.SmokeSensorRequest;
import com.yosua.homie.rest.web.model.response.SmokeSensorResponse;
import com.yosua.homie.rest.web.model.response.FlaskBaseResponse;

import java.util.List;

public interface SmokeSensorService {
    SmokeSensor addSmokeSensor(SmokeSensorRequest smokeSensorRequest);
    public SmokeSensorResponse toSmokeSensorResponse(SmokeSensor smokeSensor);
    public List<SmokeSensor> getAllUsersSmokeSensor(String userID);
    public  List<SmokeSensorResponse> toSmokeSensorResponse (List<SmokeSensor> smokeSensorList);
    public  FlaskBaseResponse checkSmoke(String deviceID);
}
