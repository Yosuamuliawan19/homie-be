package com.yosua.homie.service.api;

import com.yosua.homie.entity.dao.GasSensor;
import com.yosua.homie.rest.web.model.request.GasSensorRequest;
import com.yosua.homie.rest.web.model.response.GasSensorResponse;
import com.yosua.homie.rest.web.model.response.FlaskBaseResponse;

import java.util.List;

public interface GasSensorService {
    GasSensor addGasSensor(GasSensorRequest gasSensorRequest);
    GasSensor toGasSensorResponse(GasSensor gasSensor);
    List<GasSensor> getAllUsersGasSensor(String userID);
    List<GasSensorResponse> toGasSensorResponse(List<GasSensor> gasSensorList);
    FlaskBaseResponse checkGas(String deviceID);
}
