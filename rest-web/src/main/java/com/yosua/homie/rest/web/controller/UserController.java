package com.yosua.homie.rest.web.controller;

import com.yosua.homie.entity.constant.ApiPath;
import com.yosua.homie.entity.constant.enums.ResponseCode;
import com.yosua.homie.entity.dao.User;
import com.yosua.homie.entity.dao.UserBuilder;
import com.yosua.homie.libraries.exception.BusinessLogicException;
import com.yosua.homie.libraries.utility.BaseResponseHelper;
import com.yosua.homie.libraries.utility.PasswordHelper;
import com.yosua.homie.rest.web.model.request.MandatoryRequest;
import com.yosua.homie.rest.web.model.request.UserRequest;
import com.yosua.homie.rest.web.model.response.BaseResponse;
import com.yosua.homie.rest.web.model.response.UserResponse;
import com.yosua.homie.rest.web.model.response.UserResponseBuilder;
import com.yosua.homie.service.api.AuthService;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(ApiPath.BASE_PATH)
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthService authService;

    @PostMapping(ApiPath.ADD_USER)
    public BaseResponse<UserResponse> addUser(
            @ApiIgnore @Valid MandatoryRequest mandatoryRequest,
            @RequestBody UserRequest userRequest) {

        User user = authService.register(toUser(userRequest));
        LOGGER.info(BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, toUserResponse(user)).toString());
        return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, toUserResponse(user));
    }

    @PostMapping(ApiPath.SIGN_IN)
    public BaseResponse<UserResponse> signIn(@RequestParam String email, @RequestParam String password) {

        User user = authService.findOne(email);
        if (user == null)
        {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    ResponseCode.DATA_NOT_EXIST.getMessage());
        }
        if(PasswordHelper.matchPassword(password, user.getPassword())) {

            String token = authService.createToken(user.getId());

            return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, toUserResponse(user, token));
        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_PASSWORD.getCode(),
                    ResponseCode.INVALID_PASSWORD.getMessage());
        }
    }

    @PostMapping("test")
    public String testToken(
            @ApiIgnore @Valid MandatoryRequest mandatoryRequest,
            @RequestBody String test){
        if(authService.isTokenValid(mandatoryRequest.getAccessToken())) {
            return authService.getUserIdFromToken(mandatoryRequest.getAccessToken());
        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
        }
    }

    private User toUser(UserRequest userRequest) {
        return new UserBuilder()
                .withPassword(userRequest.getPassword())
                .withEmail(userRequest.getEmail())
                .withName(userRequest.getName())
                .withPhoneNumber(userRequest.getPhoneNumber())
                .build();
    }

    private UserResponse toUserResponse(User user) {
        Validate.notNull(user, "user is Required");
        return new UserResponseBuilder()
                .withPassword(user.getPassword())
                .withEmail(user.getEmail())
                .withName(user.getName())
                .withPhoneNumber(user.getPhoneNumber())
                .build();
    }

    private UserResponse toUserResponse(User user,String token) {
        Validate.notNull(user, "user is Required");
        return new UserResponseBuilder()
                .withPassword(user.getPassword())
                .withEmail(user.getEmail())
                .withName(user.getName())
                .withPhoneNumber(user.getPhoneNumber())
                .withToken(token)
                .build();
    }

    @ModelAttribute
    public MandatoryRequest getMandatoryParameter(HttpServletRequest request) {
        return (MandatoryRequest) request.getAttribute("mandatory");
    }
}
