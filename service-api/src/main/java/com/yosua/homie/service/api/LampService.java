package com.yosua.homie.service.api;

import com.yosua.homie.entity.dao.Lamp;
import com.yosua.homie.rest.web.model.request.LampRequest;
import com.yosua.homie.rest.web.model.response.FlaskBaseResponse;
import com.yosua.homie.rest.web.model.response.LampResponse;

import java.util.List;

public interface LampService {
    Lamp addLamp(LampRequest lampRequest);
    LampResponse toLampResponse(Lamp lamp);
    List<Lamp> getAllUsersLamp(String userID);
    Lamp getLampFromDeviceID(String deviceID) ;
    List<LampResponse> toLampResponse(List<Lamp> lampList);
    FlaskBaseResponse turnOnLamp(String deviceID);
    FlaskBaseResponse turnOffLamp(String deviceID);
}
