package com.yosua.homie.service.api;

import com.yosua.homie.entity.JWTokenClaim;
import com.yosua.homie.entity.dao.User;
import com.yosua.homie.rest.web.model.response.BaseResponse;
import com.yosua.homie.rest.web.model.response.UserResponse;

public interface AuthService {

  String createToken(String userId);

  JWTokenClaim getTokenInformation(String token);

  Boolean isTokenValid(String token);

  User register(User user);

  User findOne(String email);

  String getUserIdFromToken(String token);
}