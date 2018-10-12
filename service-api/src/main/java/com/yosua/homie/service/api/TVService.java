package com.yosua.homie.service.api;



import com.yosua.homie.entity.dao.TV;
import com.yosua.homie.rest.web.model.request.TVRequest;
import com.yosua.homie.rest.web.model.response.FlaskBaseResponse;
import com.yosua.homie.rest.web.model.response.TVResponse;

import java.util.List;

public interface TVService {
    public TV addTV(TVRequest tvRequest);
    public List<TV> getAllUsersTV(String userID);
    public TVResponse toTVResponse(TV tv);
    public List<TVResponse> toTVResponse(List<TV> TVList);
    public FlaskBaseResponse turnOnTV(String deviceID);
    public FlaskBaseResponse turnOffTV(String deviceID);
    public FlaskBaseResponse volumeUpTV(String deviceID);
    public FlaskBaseResponse volumeDownTV(String deviceID);
    public FlaskBaseResponse programUpTV(String deviceID);
    public FlaskBaseResponse programDownTV(String deviceID);
}
