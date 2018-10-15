package com.yosua.homie.rest.web.controller;

import com.yosua.homie.entity.constant.ApiPath;
import com.yosua.homie.entity.constant.enums.ResponseCode;
import com.yosua.homie.entity.dao.*;
import com.yosua.homie.libraries.exception.BusinessLogicException;
import com.yosua.homie.libraries.utility.BaseResponseHelper;
import com.yosua.homie.libraries.utility.PasswordHelper;
import com.yosua.homie.rest.web.model.request.MandatoryRequest;
import com.yosua.homie.rest.web.model.response.*;
import com.yosua.homie.service.api.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(ApiPath.BASE_PATH)

public class DeviceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

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

    // AC --------------
    @ApiOperation(value = "Get All User's AC")
    @GetMapping(ApiPath.GET_ALL_USERS_AC)
    public BaseResponse<List<ACResponse>> getAllUsersAC(@ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest) {
        if (authService.isTokenValid(mandatoryRequest.getAccessToken())) {
            String userID = authService.getUserIdFromToken(mandatoryRequest.getAccessToken());
            List<AC> ACList = acService.getAllUsersAC(userID);
            LOGGER.info(BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, acService.toACResponse(ACList)).toString());
            return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, acService.toACResponse(ACList));
        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
        }
    }

    @ApiOperation(value = "Turn on AC")
    @GetMapping(ApiPath.TURN_ON_AC)
    public FlaskBaseResponse turnOnAC(@ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest, @RequestParam String deviceID){
        if (authService.isTokenValid(mandatoryRequest.getAccessToken())) {
            LOGGER.info("Turn on AC Token: " +  mandatoryRequest.getAccessToken());
            return acService.turnOnAC(deviceID);
        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
        }
    }

    @ApiOperation(value = "Turn off AC")
    @GetMapping(ApiPath.TURN_OFF_AC)
    public FlaskBaseResponse turnOffAC(@ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest, @RequestParam String deviceID){
        if (authService.isTokenValid(mandatoryRequest.getAccessToken())) {
            return acService.turnOffAC(deviceID);
        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
        }
    }

    // TV --------------
    @ApiOperation(value = "Get All User's TV")
    @GetMapping(ApiPath.GET_ALL_USERS_TV)
    public BaseResponse<List<TVResponse>> getAllUsersTV(@ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest) {
        if (authService.isTokenValid(mandatoryRequest.getAccessToken())) {
            String userID = authService.getUserIdFromToken(mandatoryRequest.getAccessToken());
            List<TV> TVList = tvService.getAllUsersTV(userID);
            LOGGER.info(BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, tvService.toTVResponse(TVList)).toString());
            return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, tvService.toTVResponse(TVList));
        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
        }
    }

    @ApiOperation(value = "Turn on TV")
    @GetMapping(ApiPath.TURN_ON_TV)
    public FlaskBaseResponse turnOnTV(@ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest, @RequestParam String deviceID){
        if (authService.isTokenValid(mandatoryRequest.getAccessToken())) {
            LOGGER.info("Turn on TV Token: " +  mandatoryRequest.getAccessToken());
            return tvService.turnOnTV(deviceID);
        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
        }
    }

    @ApiOperation(value = "Turn off AC")
    @GetMapping(ApiPath.TURN_OFF_TV)
    public FlaskBaseResponse turnOffTV(@ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest, @RequestParam String deviceID){
        if (authService.isTokenValid(mandatoryRequest.getAccessToken())) {
            return tvService.turnOffTV(deviceID);
        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
        }
    }

    // Lamp --------------
    @ApiOperation(value = "Get All User's Lamp")
    @GetMapping(ApiPath.GET_ALL_USERS_LAMP)
    public BaseResponse<List<LampResponse>> getAllUsersLamp(@ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest) {
        if (authService.isTokenValid(mandatoryRequest.getAccessToken())) {
            String userID = authService.getUserIdFromToken(mandatoryRequest.getAccessToken());
            List<Lamp> LampList = lampService.getAllUsersLamp(userID);
            LOGGER.info(BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, lampService.toLampResponse(LampList)).toString());
            return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, lampService.toLampResponse(LampList));
        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
        }
    }

    @ApiOperation(value = "Turn on Lamp")
    @GetMapping(ApiPath.TURN_ON_LAMP)
    public FlaskBaseResponse turnOnLamp(@ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest, @RequestParam String deviceID){
        if (authService.isTokenValid(mandatoryRequest.getAccessToken())) {
            LOGGER.info("Turn on Lamp Token: " +  mandatoryRequest.getAccessToken());
            return lampService.turnOnLamp(deviceID);
        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
        }
    }

    @ApiOperation(value = "Turn off lamp")
    @GetMapping(ApiPath.TURN_OFF_LAMP)
    public FlaskBaseResponse turnOffLamp(@ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest, @RequestParam String deviceID){
        if (authService.isTokenValid(mandatoryRequest.getAccessToken())) {
            return lampService.turnOffLamp(deviceID);
        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
        }
    }

    // Rain sensor --------------
    @ApiOperation(value = "Get All User's rain sensor")
    @GetMapping(ApiPath.GET_ALL_USERS_RAIN_SENSOR)
    public BaseResponse<List<RainSensorResponse>> getAllUsersRainSensor(@ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest) {
        if (authService.isTokenValid(mandatoryRequest.getAccessToken())) {
            String userID = authService.getUserIdFromToken(mandatoryRequest.getAccessToken());
            List<RainSensor> RainSensorList = rainSensorService.getAllUsersRainSensor(userID);
            LOGGER.info(BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, rainSensorService.toRainSensorResponse(RainSensorList)).toString());
            return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, rainSensorService.toRainSensorResponse(RainSensorList));
        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
        }
    }

    @ApiOperation(value = "Check for rain ")
    @GetMapping(ApiPath.CHECK_FOR_RAIN)
    public FlaskBaseResponse checkForRain(@ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest, @RequestParam String deviceID){
        if (authService.isTokenValid(mandatoryRequest.getAccessToken())) {
            LOGGER.info("Check for rain Token: " +  mandatoryRequest.getAccessToken());
            return rainSensorService.checkRain(deviceID);
        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
        }
    }

    // Flame sensor --------------
    @ApiOperation(value = "Get All User's flame sensor")
    @GetMapping(ApiPath.GET_ALL_USERS_FLAME_SENSOR)
    public BaseResponse<List<FlameSensorResponse>> getAllUsersFlameSensor(@ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest) {
        if (authService.isTokenValid(mandatoryRequest.getAccessToken())) {
            String userID = authService.getUserIdFromToken(mandatoryRequest.getAccessToken());
            List<FlameSensor> FlameSensorList = flameSensorService.getAllUsersFlameSensor(userID);
            LOGGER.info(BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, flameSensorService.toFlameSensorResponse(FlameSensorList)).toString());
            return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, flameSensorService.toFlameSensorResponse(FlameSensorList));
        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
        }
    }

    @ApiOperation(value = "Check for Flame")
    @GetMapping(ApiPath.CHECK_FOR_FLAME)
    public FlaskBaseResponse checkForFlame(@ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest, @RequestParam String deviceID){
        if (authService.isTokenValid(mandatoryRequest.getAccessToken())) {
            LOGGER.info("Check for flame Token: " +  mandatoryRequest.getAccessToken());
            return flameSensorService.checkFlame(deviceID);
        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
        }
    }

    // Gas Sensor --------------
    @ApiOperation(value = "Get all users' gas sensor")
    @GetMapping(ApiPath.GET_ALL_USERS_GAS_SENSOR)
    public BaseResponse<List<GasSensorResponse>> getAllUsersGasSensor (@ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest){
        if(authService.isTokenValid(mandatoryRequest.getAccessToken())){
            String userID = authService.getUserIdFromToken(mandatoryRequest.getAccessToken());
            List<GasSensor> GasSensorList = gasSensorService.getAllUsersGasSensor(userID);
            LOGGER.info(BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, gasSensorService.toGasSensorResponse(GasSensorList)).toString());
            return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, gasSensorService.toGasSensorResponse(GasSensorList));
        }
        else{
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
        }
    }

    @ApiOperation(value = "Check for Gas")
    @GetMapping(ApiPath.CHECK_FOR_GAS)
    public FlaskBaseResponse checkForGas(@ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest, @RequestParam String devieID){
        if(authService.isTokenValid(mandatoryRequest.getAccessToken())){
            LOGGER.info("Check for gas token: " + mandatoryRequest.getAccessToken());
            return gasSensorService.checkGas(devieID);
        }
        else {
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
        }
    }
}
