package com.yosua.homie.service.api;



import com.yosua.homie.entity.dao.TV;
import com.yosua.homie.rest.web.model.request.TVRequest;
import com.yosua.homie.rest.web.model.response.FlaskBaseResponse;
import com.yosua.homie.rest.web.model.response.TVResponse;

import java.util.List;

public interface TVService {
    TV addTV(TVRequest tvRequest);
    List<TV> getAllUsersTV(String userID);
    TV getTVFromDeviceID(String id);
    TVResponse toTVResponse(TV tv);
    List<TVResponse> toTVResponse(List<TV> TVList);
    FlaskBaseResponse turnOnTV(String deviceID);
    FlaskBaseResponse turnOffTV(String deviceID);
    FlaskBaseResponse volumeUpTV(String deviceID);
    FlaskBaseResponse volumeDownTV(String deviceID);
    FlaskBaseResponse programUpTV(String deviceID);
    FlaskBaseResponse programDownTV(String deviceID);
    FlaskBaseResponse muteTV(String deviceID);

}
