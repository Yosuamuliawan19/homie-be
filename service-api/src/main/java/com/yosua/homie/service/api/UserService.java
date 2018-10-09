package com.yosua.homie.service.api;

import com.yosua.homie.entity.dao.User;
import com.yosua.homie.rest.web.model.request.HubsRequest;
import com.yosua.homie.rest.web.model.request.MandatoryRequest;
import com.yosua.homie.rest.web.model.request.UserRequest;
import com.yosua.homie.rest.web.model.response.UserResponse;

public interface UserService {
    User toUser(UserRequest userRequest);
    UserResponse toUserResponse(User user,String token);
    UserResponse toUserResponse(User user);

}
