package com.yosua.homie.service.api;



import com.yosua.homie.entity.dao.TV;
import com.yosua.homie.rest.web.model.request.TVRequest;
import com.yosua.homie.rest.web.model.response.FlaskBaseResponse;
import com.yosua.homie.rest.web.model.response.FlaskTVResponse;
import com.yosua.homie.rest.web.model.response.TVResponse;

import java.util.Date;
import java.util.List;

public interface TVService {
    TV addTV(TVRequest tvRequest);
    List<TV> getAllUsersTV(String userID);
    TV getTVFromDeviceID(String id);
    TVResponse toTVResponse(TV tv);
    List<TVResponse> toTVResponse(List<TV> TVList);
    FlaskTVResponse turnOnTV(String deviceID);
    FlaskTVResponse turnOffTV(String deviceID);
    FlaskTVResponse volumeUpTV(String deviceID);
    FlaskTVResponse volumeDownTV(String deviceID);
    FlaskTVResponse programUpTV(String deviceID);
    FlaskTVResponse programDownTV(String deviceID);
    FlaskTVResponse muteTV(String deviceID);
    void scheduledTurnOffTV(String deviceID);
    void scheduledTurnOnTV(String deviceID);
    void setTimerTV(String deviceID, Date start, Date end);
}
