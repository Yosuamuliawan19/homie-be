package com.yosua.homie.service.api;

import com.yosua.homie.entity.dao.AC;
import com.yosua.homie.rest.web.model.request.ACRequest;
import com.yosua.homie.rest.web.model.response.ACResponse;
import com.yosua.homie.rest.web.model.response.FlaskACResponse;
import com.yosua.homie.rest.web.model.response.FlaskBaseResponse;

import java.util.List;
import java.util.Date;

public interface ACService {
    AC addAC(ACRequest acRequest);
    ACResponse toACResponse(AC ac);
    List<AC> getAllUsersAC(String userID);
    List<ACResponse> toACResponse(List<AC> ACList);
    FlaskBaseResponse turnOnAC(String deviceID);
    FlaskBaseResponse turnOffAC(String deviceID);
    FlaskACResponse setTemperature(String deviceID, Double temperature);
    AC getACFromDeviceID(String deviceID);
    void scheduledTurnOffAC(String deviceID);
    void scheduledTurnOnAC(String deviceID);
    void setTimerAC(String deviceID, Date start, Date end);
}
