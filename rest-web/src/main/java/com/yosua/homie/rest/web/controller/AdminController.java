package com.yosua.homie.rest.web.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.yosua.homie.entity.constant.ApiPath;
import com.yosua.homie.entity.constant.enums.ResponseCode;
import com.yosua.homie.entity.dao.AC;
import com.yosua.homie.entity.dao.TV;
import com.yosua.homie.entity.dao.Lamp;
import com.yosua.homie.entity.dao.RainSensor;
import com.yosua.homie.entity.dao.FlameSensor;
import com.yosua.homie.entity.dao.GasSensor;
import com.yosua.homie.entity.dao.User;
import com.yosua.homie.entity.dao.UserVerification;
import com.yosua.homie.libraries.utility.BaseResponseHelper;
import com.yosua.homie.rest.web.model.request.*;
import com.yosua.homie.rest.web.model.response.*;
import com.yosua.homie.service.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPath.BASE_PATH)
public class AdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private HubService hubService;

    @Autowired
    private ACService acService;

    @Autowired
    private TVService tvService;

    @Autowired
    private LampService lampService;

    @Autowired
    private RainSensorService rainSensorService;

    @Autowired
    private FlameSensorService flameSensorService;

    @Autowired
    private GasSensorService gasSensorService;

    @PostMapping(ApiPath.ADD_USER)
    public BaseResponse<UserResponse> addUser(@RequestBody UserRequest userRequest) {

        User user = authService.register(userService.toUser(userRequest));
        UserVerification userVerification = authService.addUserVerification(user);
        LOGGER.info(BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, userService.toUserResponse(user)).toString());
        LOGGER.info(userVerification.toString());
        return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, userService.toUserResponse(user));
    }

    @PostMapping(ApiPath.ADD_HUBS)
    public BaseResponse<UserResponse> addHubs(@RequestBody HubsRequest hubsRequest, @RequestParam String userID)
    {
        User user = hubService.addHubs(hubsRequest, userID);
        LOGGER.info(BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, userService.toUserResponse(user)).toString());
        return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, userService.toUserResponse(user));
    }

    @PostMapping(ApiPath.ADD_AC)
    public BaseResponse<ACResponse> addAC(@RequestBody ACRequest ACRequest)
    {
        AC newAC = acService.addAC(ACRequest);
        LOGGER.info(BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, acService.toACResponse(newAC)).toString());
        return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, acService.toACResponse(newAC));
    }

    @PostMapping(ApiPath.ADD_TV)
    public BaseResponse<TVResponse> addTV(@RequestBody TVRequest TVRequest)
    {
        TV newTV = tvService.addTV(TVRequest);
        LOGGER.info(BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, tvService.toTVResponse(newTV)).toString());
        return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, tvService.toTVResponse(newTV));
    }

    @PostMapping(ApiPath.ADD_LAMP)
    public BaseResponse<LampResponse> addLamp(@RequestBody LampRequest LampRequest)
    {
        Lamp newLamp = lampService.addLamp(LampRequest);
        LOGGER.info(BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, lampService.toLampResponse(newLamp)).toString());
        return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, lampService.toLampResponse(newLamp));
    }

    @PostMapping(ApiPath.ADD_RAIN_SENSOR)
    public BaseResponse<RainSensorResponse> addRainSensor(@RequestBody RainSensorRequest RainSensorRequest)
    {
        RainSensor newRainSensor = rainSensorService.addRainSensor(RainSensorRequest);
        LOGGER.info(BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, rainSensorService.toRainSensorResponse(newRainSensor)).toString());
        return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, rainSensorService.toRainSensorResponse(newRainSensor));
    }

    @PostMapping(ApiPath.ADD_FlAME_SENSOR)
    public BaseResponse<FlameSensorResponse> addFlameSensor(@RequestBody FlameSensorRequest FlameSensorRequest)
    {
        FlameSensor newFlameSensor = flameSensorService.addFlameSensor(FlameSensorRequest);
        LOGGER.info(BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, flameSensorService.toFlameSensorResponse(newFlameSensor)).toString());
        return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, flameSensorService.toFlameSensorResponse(newFlameSensor));
    }

    @PostMapping(ApiPath.ADD_GAS_SENSOR)
    public BaseResponse<GasSensorResponse> addGasSensor(@RequestBody GasSensorRequest GasSensorRequest)
    {
        GasSensor newGasSensor = gasSensorService.addGasSensor(GasSensorRequest);
        LOGGER.info(BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, gasSensorService.toGasSensorResponse(newGasSensor)).toString());
        return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, gasSensorService.toGasSensorResponse(newGasSensor));
    }
}
