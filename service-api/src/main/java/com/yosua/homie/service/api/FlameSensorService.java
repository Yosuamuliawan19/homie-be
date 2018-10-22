package com.yosua.homie.service.api;

import com.yosua.homie.entity.dao.FlameSensor;
import com.yosua.homie.rest.web.model.request.FlameSensorRequest;
import com.yosua.homie.rest.web.model.response.FlameSensorResponse;
import com.yosua.homie.rest.web.model.response.FlaskBaseResponse;

import java.util.List;

public interface FlameSensorService {
    FlameSensor addFlameSensor(FlameSensorRequest flameSensorRequest);
    FlameSensorResponse toFlameSensorResponse(FlameSensor flameSensor);
    List<FlameSensor> getAllUsersFlameSensor(String userID);
    List<FlameSensorResponse> toFlameSensorResponse(List<FlameSensor> flameSensorList);
    FlaskBaseResponse checkFlame(String deviceID);
    String notifyForFlame(String userID);
}
