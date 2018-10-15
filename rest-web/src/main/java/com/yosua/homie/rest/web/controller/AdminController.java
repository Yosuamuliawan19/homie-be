package com.yosua.homie.rest.web.controller;

import com.yosua.homie.entity.constant.ApiPath;
import com.yosua.homie.entity.constant.enums.ResponseCode;
import com.yosua.homie.entity.dao.AC;
import com.yosua.homie.entity.dao.TV;
import com.yosua.homie.entity.dao.User;
import com.yosua.homie.entity.dao.UserVerification;
import com.yosua.homie.libraries.utility.BaseResponseHelper;
import com.yosua.homie.rest.web.model.request.ACRequest;
import com.yosua.homie.rest.web.model.request.HubsRequest;
import com.yosua.homie.rest.web.model.request.TVRequest;
import com.yosua.homie.rest.web.model.request.UserRequest;
import com.yosua.homie.rest.web.model.response.ACResponse;
import com.yosua.homie.rest.web.model.response.BaseResponse;
import com.yosua.homie.rest.web.model.response.TVResponse;
import com.yosua.homie.rest.web.model.response.UserResponse;
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
    public BaseResponse<TVResponse> addAC(@RequestBody TVRequest TVRequest)
    {
        TV newTV = tvService.addTV(TVRequest);
        LOGGER.info(BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, tvService.toTVResponse(newTV)).toString());
        return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, tvService.toTVResponse(newTV));
    }
}
