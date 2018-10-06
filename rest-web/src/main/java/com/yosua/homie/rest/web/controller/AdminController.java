package com.yosua.homie.rest.web.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.yosua.homie.dao.UserRepository;
import com.yosua.homie.entity.constant.ApiPath;
import com.yosua.homie.entity.constant.enums.ResponseCode;
import com.yosua.homie.entity.dao.User;
import com.yosua.homie.libraries.utility.BaseResponseHelper;
import com.yosua.homie.rest.web.model.request.HubsRequest;
import com.yosua.homie.rest.web.model.request.MandatoryRequest;
import com.yosua.homie.rest.web.model.request.UserRequest;
import com.yosua.homie.rest.web.model.response.BaseResponse;
import com.yosua.homie.rest.web.model.response.UserResponse;
import com.yosua.homie.service.api.AdminService;
import com.yosua.homie.service.api.AuthService;
import com.yosua.homie.service.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(ApiPath.BASE_PATH)
public class AdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @PostMapping(ApiPath.ADD_USER)
    public BaseResponse<UserResponse> addUser(@RequestBody UserRequest userRequest) {

        User user = authService.register(userService.toUser(userRequest));
        LOGGER.info(BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, userService.toUserResponse(user)).toString());
        return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, userService.toUserResponse(user));
    }

    @PostMapping(ApiPath.ADD_HUBS)
    public BaseResponse<UserResponse> addHubs(@RequestBody HubsRequest hubsRequest, @RequestParam String userID)
    {
        User user = adminService.addHubs(hubsRequest, userID);
        LOGGER.info(BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, userService.toUserResponse(user)).toString());
        return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, userService.toUserResponse(user));
    }
}
