package com.yosua.homie.rest.web.controller;

import com.yosua.homie.entity.constant.ApiPath;
import com.yosua.homie.entity.constant.enums.ResponseCode;
import com.yosua.homie.entity.dao.*;
import com.yosua.homie.libraries.exception.BusinessLogicException;
import com.yosua.homie.libraries.utility.BaseResponseHelper;
import com.yosua.homie.rest.web.model.request.MandatoryRequest;
import com.yosua.homie.rest.web.model.response.*;
import com.yosua.homie.service.api.*;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.Validate;
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

    @Autowired
    private SmokeSensorService smokeSensorService;

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
    @ApiOperation(value = "Get ac by id")
    @GetMapping(ApiPath.GET_AC_BY_DEVICE_ID)
    public BaseResponse<ACResponse> getACbyDeviceID(@ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest, @RequestParam String deviceID){
        if (authService.isTokenValid(mandatoryRequest.getAccessToken())){
            String userID = authService.getUserIdFromToken(mandatoryRequest.getAccessToken());
            AC ac = acService.getACFromDeviceID(deviceID);
            LOGGER.info(BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, acService.toACResponse(ac)).toString());
            return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, acService.toACResponse(ac));
        }else {
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
    @ApiOperation(value = "Get tv by id")
    @GetMapping(ApiPath.GET_TV_BY_DEVICE_ID)
    public BaseResponse<TVResponse> getTVbyDeviceID(@ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest, @RequestParam String deviceID){
        if (authService.isTokenValid(mandatoryRequest.getAccessToken())){
            String userID = authService.getUserIdFromToken(mandatoryRequest.getAccessToken());
            TV tv = tvService.getTVFromDeviceID(deviceID);
            LOGGER.info(BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, tvService.toTVResponse(tv)).toString());
            return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, tvService.toTVResponse(tv));
        }else {
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
//
    @ApiOperation(value = "Volume up TV")
    @GetMapping(ApiPath.FLASK_VOLUME_UP_TV)
    public FlaskBaseResponse volumeUpTV(@ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest, @RequestParam String deviceID){
        if (authService.isTokenValid(mandatoryRequest.getAccessToken())) {
            LOGGER.info("Volume up TV Token: " +  mandatoryRequest.getAccessToken());
            return tvService.volumeUpTV(deviceID);
        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
        }
    }
    @ApiOperation(value = "Volume down AC")
    @GetMapping(ApiPath.FLASK_VOLUME_DOWN_TV)
    public FlaskBaseResponse volumeDownTV(@ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest, @RequestParam String deviceID){
        if (authService.isTokenValid(mandatoryRequest.getAccessToken())) {
            LOGGER.info("Volume down TV Token: " +  mandatoryRequest.getAccessToken());
            return tvService.volumeDownTV(deviceID);
        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
        }
    }
    @ApiOperation(value = "Program up TV")
    @GetMapping(ApiPath.FLASK_PROGRAM_UP_TV)
    public FlaskBaseResponse programUpTV(@ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest, @RequestParam String deviceID){
        if (authService.isTokenValid(mandatoryRequest.getAccessToken())) {
            LOGGER.info("Program up TV Token: " +  mandatoryRequest.getAccessToken());
            return tvService.programUpTV(deviceID);
        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
        }
    }
    @ApiOperation(value = "Program down AC")
    @GetMapping(ApiPath.FLASK_PROGRAM_DOWN_TV)
    public FlaskBaseResponse programDownTV(@ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest, @RequestParam String deviceID){
        if (authService.isTokenValid(mandatoryRequest.getAccessToken())) {
            LOGGER.info("Program down TV Token: " +  mandatoryRequest.getAccessToken());
            return tvService.programDownTV(deviceID);
        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
        }
    }


    // Lamp --------------
    @ApiOperation(value = "Get All Users' Lamp")
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
    @ApiOperation(value = "Get lamp by id")
    @GetMapping(ApiPath.GET_LAMP_BY_DEVICE_ID)
    public BaseResponse<LampResponse> getLampByDeviceID(@ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest, @RequestParam String deviceID){
        if (authService.isTokenValid(mandatoryRequest.getAccessToken())){
            String userID = authService.getUserIdFromToken(mandatoryRequest.getAccessToken());
            Lamp lamp = lampService.getLampFromDeviceID(deviceID);
            LOGGER.info(BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, lampService.toLampResponse(lamp)).toString());
            return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, lampService.toLampResponse(lamp));
        }else {
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
    @ApiOperation(value = "Get All Users' Rain Sensor")
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

    @ApiOperation(value = "Check for Rain ")
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
    @ApiOperation(value = "Get All Users' Flame Sensor")
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
    @ApiOperation(value = "Get All Users' Gas Sensor")
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
    // Smoke Sensor --------------
    @ApiOperation(value = "Get All Users' Smoke Sensor")
    @GetMapping(ApiPath.GET_ALL_USERS_SMOKE_SENSOR)
    public BaseResponse<List<SmokeSensorResponse>> getAllUsersSmokeSensor (@ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest){
        if(authService.isTokenValid(mandatoryRequest.getAccessToken())) {
            String userID = authService.getUserIdFromToken(mandatoryRequest.getAccessToken());
            List<SmokeSensor> SmokeSensorList = smokeSensorService.getAllUsersSmokeSensor(userID);
            LOGGER.info(BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, smokeSensorService.toSmokeSensorResponse(SmokeSensorList)).toString());
            return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, smokeSensorService.toSmokeSensorResponse(SmokeSensorList));
        }
        else{
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
        }
    }

    @ApiOperation(value = "Check for Smoke")
    @GetMapping(ApiPath.CHECK_FOR_SMOKE)
    public FlaskBaseResponse checkForSmoke(@ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest, @RequestParam String devieID){
        if(authService.isTokenValid(mandatoryRequest.getAccessToken())){
            LOGGER.info("Check for smoke token: " + mandatoryRequest.getAccessToken());
            return smokeSensorService.checkSmoke(devieID);
        }
        else {
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
        }
    }

}
