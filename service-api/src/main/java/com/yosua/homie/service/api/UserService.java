package com.yosua.homie.service.api;

import com.yosua.homie.entity.dao.User;
import com.yosua.homie.rest.web.model.request.UserRequest;
import com.yosua.homie.rest.web.model.response.UserResponse;

public interface UserService {
    User toUser(UserRequest userRequest);
    UserResponse toUserResponse(User user,String token);
    UserResponse toUserResponse(User user);
    User editNotificationToken(String userID, String notificationToken);
    String getNotificationToken(String userID);
}
