package com.yosua.homie.rest.web.controller;

import com.yosua.homie.entity.constant.ApiPath;
import com.yosua.homie.entity.constant.enums.ResponseCode;
import com.yosua.homie.entity.dao.User;
import com.yosua.homie.entity.dao.UserBuilder;
import com.yosua.homie.libraries.exception.BusinessLogicException;
import com.yosua.homie.libraries.utility.BaseResponseHelper;
import com.yosua.homie.libraries.utility.PasswordHelper;
import com.yosua.homie.rest.web.model.request.HubsRequest;
import com.yosua.homie.rest.web.model.request.MandatoryRequest;
import com.yosua.homie.rest.web.model.request.UserRequest;
import com.yosua.homie.rest.web.model.response.BaseResponse;
import com.yosua.homie.rest.web.model.response.UserResponse;
import com.yosua.homie.rest.web.model.response.UserResponseBuilder;
import com.yosua.homie.service.api.AuthService;
import com.yosua.homie.service.api.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @Autowired
    private UserService userService;

    @PostMapping(ApiPath.SIGN_IN)
    public BaseResponse<UserResponse> signIn(@RequestParam String email, @RequestParam String password) {

        User user = authService.findOne(email.toLowerCase());
        if (user == null)
        {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    ResponseCode.DATA_NOT_EXIST.getMessage());
        }
        if(PasswordHelper.matchPassword(password, user.getPassword())) {

            String token = authService.createToken(user.getId());
            return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, userService.toUserResponse(user, token));
        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_PASSWORD.getCode(),
                    ResponseCode.INVALID_PASSWORD.getMessage());
        }
    }

    @ApiOperation(value = "Edit Hub's Physical Address")
    @PostMapping(ApiPath.EDIT_HUBS)
    public BaseResponse<UserResponse> editHubs(
            @ApiIgnore @Valid MandatoryRequest mandatoryRequest,
            @RequestParam String IPaddressToBeUpdated, @RequestParam String updatedPhysicalAddress){
        if(authService.isTokenValid(mandatoryRequest.getAccessToken())) {

             String userID= authService.getUserIdFromToken(mandatoryRequest.getAccessToken());
             User updatedUser =  userService.editHubs(userID,IPaddressToBeUpdated,updatedPhysicalAddress);

             return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, userService.toUserResponse(updatedUser, mandatoryRequest.getAccessToken()));
        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
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

    @ModelAttribute
    public MandatoryRequest getMandatoryParameter(HttpServletRequest request) {
        return (MandatoryRequest) request.getAttribute("mandatory");
    }


}
