package com.yosua.homie.rest.web.controller;

import com.yosua.homie.entity.constant.ApiPath;
import com.yosua.homie.entity.constant.enums.ResponseCode;
import com.yosua.homie.entity.dao.AC;
import com.yosua.homie.entity.dao.User;
import com.yosua.homie.libraries.exception.BusinessLogicException;
import com.yosua.homie.libraries.utility.BaseResponseHelper;
import com.yosua.homie.libraries.utility.PasswordHelper;
import com.yosua.homie.rest.web.model.request.MandatoryRequest;
import com.yosua.homie.rest.web.model.response.ACResponse;
import com.yosua.homie.rest.web.model.response.BaseResponse;
import com.yosua.homie.rest.web.model.response.FlaskBaseResponse;
import com.yosua.homie.rest.web.model.response.UserResponse;
import com.yosua.homie.service.api.ACService;
import com.yosua.homie.service.api.AuthService;
import com.yosua.homie.service.api.HubService;
import com.yosua.homie.service.api.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(ApiPath.BASE_PATH)
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;
  
    @Autowired
    private HubService hubService;

    @Autowired
    private ACService acService;


    @PostMapping(ApiPath.VERIFY_CREDENTIALS)
    public BaseResponse<UserResponse> verifyCredentials(@RequestParam String email, @RequestParam String password) {

        User user = authService.findOne(email.toLowerCase());
        if (user == null) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    ResponseCode.DATA_NOT_EXIST.getMessage());
        }
        if (PasswordHelper.matchPassword(password, user.getPassword())) {


            authService.generateCode(user);
            return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, userService.toUserResponse(user));
        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_PASSWORD.getCode(),
                    ResponseCode.INVALID_PASSWORD.getMessage());
        }
    }
    @PostMapping(ApiPath.SIGN_IN)
    public BaseResponse<UserResponse> signIn(@RequestParam String email, @RequestParam String password, @RequestParam String code ) {

        User user = authService.findOne(email.toLowerCase());
        if (user == null)
        {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    ResponseCode.DATA_NOT_EXIST.getMessage());
        }
        if(PasswordHelper.matchPassword(password, user.getPassword())) {

            Boolean success = authService.verifyCode(code, user);
            if (success){
                return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                        null, userService.toUserResponse(user));
            }else{
                throw new BusinessLogicException(ResponseCode.INVALID_VERIFICATION_CODE.getCode(),
                        ResponseCode.INVALID_VERIFICATION_CODE.getMessage());
            }

        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_PASSWORD.getCode(),
                    ResponseCode.INVALID_PASSWORD.getMessage());
        }
    }

    @ApiOperation(value = "Edit Hub's Physical Address")
    @PostMapping(ApiPath.EDIT_HUBS)
    public BaseResponse<UserResponse> editHubs(
            @ApiIgnore @Valid MandatoryRequest mandatoryRequest,
            @RequestParam String URLToBeUpdated, @RequestParam String updatedPhysicalAddress) {
        if (authService.isTokenValid(mandatoryRequest.getAccessToken())) {

            String userID = authService.getUserIdFromToken(mandatoryRequest.getAccessToken());
            User updatedUser = hubService.editHubs(userID, URLToBeUpdated, updatedPhysicalAddress);

            return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, userService.toUserResponse(updatedUser, mandatoryRequest.getAccessToken()));
        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
        }
    }

    @ApiOperation(value = "Change User's Password")
    @PostMapping(ApiPath.CHANGE_PASSWORD)
    public BaseResponse<UserResponse> changePassword(
            @ApiIgnore @Valid MandatoryRequest mandatoryRequest, @RequestParam String oldPassword, @RequestParam String newPassword) {
        if (authService.isTokenValid(mandatoryRequest.getAccessToken())) {

            String userID = authService.getUserIdFromToken(mandatoryRequest.getAccessToken());
            User updatedUser = authService.changePassword(userID, oldPassword, newPassword);

            return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, userService.toUserResponse(updatedUser, mandatoryRequest.getAccessToken()));
        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
        }

    }

    @ApiOperation(value = "Get All User's AC")
    @GetMapping(ApiPath.GET_ALL_USERS_AC)
    public BaseResponse<List<ACResponse>> getAllUsersAC(@ApiIgnore @Valid MandatoryRequest mandatoryRequest) {
        if (authService.isTokenValid(mandatoryRequest.getAccessToken())) {
            String userID = authService.getUserIdFromToken(mandatoryRequest.getAccessToken());
            List<AC> ACList = acService.getAllUsersAC(userID);
            return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    null, acService.toACResponse(ACList));
        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
        }
    }

    @ApiOperation(value = "Turn on AC")
    @GetMapping(ApiPath.TURN_ON_AC)
    public FlaskBaseResponse turnOnAC(@ApiIgnore @Valid MandatoryRequest mandatoryRequest, @RequestParam String deviceID){
        if (authService.isTokenValid(mandatoryRequest.getAccessToken())) {
            return acService.turnOnAC(deviceID);
        } else {
            throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                    ResponseCode.INVALID_TOKEN.getMessage());
        }
    }

    @ApiOperation(value = "Turn off AC")
    @GetMapping(ApiPath.TURN_OFF_AC)
    public FlaskBaseResponse turnOffAC(@ApiIgnore @Valid MandatoryRequest mandatoryRequest, @RequestParam String deviceID){
        if (authService.isTokenValid(mandatoryRequest.getAccessToken())) {
            return acService.turnOffAC(deviceID);
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
