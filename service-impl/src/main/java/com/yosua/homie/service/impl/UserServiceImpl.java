package com.yosua.homie.service.impl;

import com.yosua.homie.dao.UserRepository;
import com.yosua.homie.entity.constant.enums.ResponseCode;
import com.yosua.homie.entity.dao.Hub;
import com.yosua.homie.entity.dao.User;
import com.yosua.homie.entity.dao.UserBuilder;
import com.yosua.homie.libraries.exception.BusinessLogicException;
import com.yosua.homie.rest.web.model.request.UserRequest;
import com.yosua.homie.rest.web.model.response.UserResponse;
import com.yosua.homie.rest.web.model.response.UserResponseBuilder;
import com.yosua.homie.service.api.UserService;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public User editNotificationToken(String userID, String notificationToken) {
        Validate.notNull(userID,"User id to be updated is required");
        Validate.notNull(notificationToken,"Notification token to be updated is required");
        User user = userRepository.findUserById(userID);
        user.setNotificationToken(notificationToken);
        try{
            return userRepository.save(user);
        } catch (Exception e) {
            LOGGER.warn("Failed to update User");
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    "Failed to update User");
        }
    }
    @Override
    public String getNotificationToken(String userID) {
        Validate.notNull(userID,"User id to be updated is required");
        User user = userRepository.findUserById(userID);
        return user.getNotificationToken();
    }
}
