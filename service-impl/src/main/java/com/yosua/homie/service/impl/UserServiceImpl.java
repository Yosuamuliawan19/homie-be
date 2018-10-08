package com.yosua.homie.service.impl;

import com.yosua.homie.dao.UserRepository;
import com.yosua.homie.entity.constant.enums.ResponseCode;
import com.yosua.homie.entity.dao.Hub;
import com.yosua.homie.entity.dao.User;
import com.yosua.homie.entity.dao.UserBuilder;
import com.yosua.homie.libraries.exception.BusinessLogicException;
import com.yosua.homie.libraries.utility.BaseResponseHelper;
import com.yosua.homie.libraries.utility.PasswordHelper;
import com.yosua.homie.rest.web.model.request.HubsRequest;
import com.yosua.homie.rest.web.model.request.MandatoryRequest;
import com.yosua.homie.rest.web.model.request.UserRequest;
import com.yosua.homie.rest.web.model.response.UserResponse;
import com.yosua.homie.rest.web.model.response.UserResponseBuilder;
import com.yosua.homie.service.api.UserService;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public User toUser(UserRequest userRequest) {
        return new UserBuilder()
                .withPassword(userRequest.getPassword())
                .withEmail(userRequest.getEmail())
                .withName(userRequest.getName())
                .withPhoneNumber(userRequest.getPhoneNumber())
                .withHubs(userRequest.getHubs())
                .build();
    }

    @Override
    public UserResponse toUserResponse(User user) {
        Validate.notNull(user, "user is Required");
        return new UserResponseBuilder()
                .withPassword(user.getPassword())
                .withEmail(user.getEmail())
                .withName(user.getName())
                .withPhoneNumber(user.getPhoneNumber())
                .withHubs(user.getHubs())
                .build();
    }

    @Override
    public UserResponse toUserResponse(User user,String token) {
        Validate.notNull(user, "user is Required");
        return new UserResponseBuilder()
                .withPassword(user.getPassword())
                .withEmail(user.getEmail())
                .withName(user.getName())
                .withPhoneNumber(user.getPhoneNumber())
                .withHubs(user.getHubs())
                .withToken(token)
                .build();
    }

    @Override
    public User editHubs(String userID, String IPAddress, String updatedPhysicalAddress) {
        Validate.notNull(IPAddress,"IP Address to be updated is required");
        Validate.notNull(updatedPhysicalAddress,"Physical Address to be updated is required");
        User user = userRepository.findUserById(userID);
        List<Hub> userHubs = user.getHubs();
        Boolean isIPFound = false;
        for(Hub hubs: userHubs){
            if(hubs.getIpAddress().equals(IPAddress))
            {
                hubs.setHubPhysicalAddress(updatedPhysicalAddress);
                isIPFound = true;
            }
        }
        if(!isIPFound) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    ResponseCode.DATA_NOT_EXIST.getMessage());
        }
        try{
            return userRepository.save(user);
        } catch (Exception e) {
            LOGGER.warn("Failed to update User");
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    "Failed to update User");
        }
    }

    @Override
    public User changePassword(String userID, String oldPassword, String newPassword){
        Validate.notNull(userID,"userID to be updated is required");
        Validate.notNull(oldPassword,"Old Password is required");
        Validate.notNull(newPassword, "New Password is required");

        User user = userRepository.findUserById(userID);

        if(PasswordHelper.matchPassword(oldPassword, user.getPassword())) {
            if(PasswordHelper.isPasswordValid(newPassword)){
                user.setPassword(PasswordHelper.encryptPassword(newPassword));
                try{
                    return userRepository.save(user);
                } catch (Exception e) {
                    throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                            ResponseCode.SYSTEM_ERROR.getMessage());
                }
            }
            else
                throw new BusinessLogicException(ResponseCode.INVALID_PASSWORD.getCode(), ResponseCode.INVALID_PASSWORD.getMessage());
        } else {
            throw new BusinessLogicException(ResponseCode.PASSWORDS_DOES_NOT_MATCH.getCode(),
                    ResponseCode.PASSWORDS_DOES_NOT_MATCH.getMessage());
        }
    }
}
