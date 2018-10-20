package com.yosua.homie.rest.web.controller;

import com.yosua.homie.entity.constant.ApiPath;
import com.yosua.homie.entity.constant.enums.ResponseCode;
import com.yosua.homie.libraries.utility.BaseResponseHelper;
import com.yosua.homie.rest.web.model.request.MandatoryRequest;
import com.yosua.homie.rest.web.model.response.BaseResponse;
import com.yosua.homie.service.api.EnvironmentSensorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(ApiPath.BASE_PATH)
public class EnvironmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentController.class);

    @Autowired
    EnvironmentSensorService environmentSensorService;

    @GetMapping(ApiPath.GET_TEMPERATURE_DATA)
    public BaseResponse<List<Double>> getTemperatureData(
            @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest)  {
        List<Double> num =  environmentSensorService.getTemperatureData();
        return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, num);
    }

    @GetMapping(ApiPath.GET_HUMIDITY_DATA)
    public BaseResponse<List<Double>> getHumidityData(@ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest){
        List<Double> num = environmentSensorService.getHumidityData();
        return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, num);
    }

}
