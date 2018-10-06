package com.yosua.homie.service.impl;

import com.yosua.homie.entity.dao.User;
import com.yosua.homie.entity.dao.UserBuilder;
import com.yosua.homie.rest.web.model.request.UserRequest;
import com.yosua.homie.rest.web.model.response.UserResponse;
import com.yosua.homie.rest.web.model.response.UserResponseBuilder;
import com.yosua.homie.service.api.UserService;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public User toUser(UserRequest userRequest) {
        return new UserBuilder()
                .withPassword(userRequest.getPassword())
                .withEmail(userRequest.getEmail())
                .withName(userRequest.getName())
                .withPhoneNumber(userRequest.getPhoneNumber())
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
                .withToken(token)
                .build();
    }
}
