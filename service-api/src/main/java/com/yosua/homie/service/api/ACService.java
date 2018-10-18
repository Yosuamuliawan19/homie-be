package com.yosua.homie.service.api;

import com.yosua.homie.entity.dao.AC;
import com.yosua.homie.rest.web.model.request.ACRequest;
import com.yosua.homie.rest.web.model.response.ACResponse;
import com.yosua.homie.rest.web.model.response.FlaskBaseResponse;

import java.util.List;

public interface ACService {
    AC addAC(ACRequest acRequest);
    ACResponse toACResponse(AC ac);
    List<AC> getAllUsersAC(String userID);
    List<ACResponse> toACResponse(List<AC> ACList);
    FlaskBaseResponse turnOnAC(String deviceID);
    FlaskBaseResponse turnOffAC(String deviceID);
//    FlaskBaseResponse temperatureUp(String deviceID);
//    FlaskBaseResponse temperatureDown(String deviceID);
    AC getACFromDeviceID(String deviceID);
}
