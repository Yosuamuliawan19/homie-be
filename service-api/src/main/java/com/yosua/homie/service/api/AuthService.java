package com.yosua.homie.service.api;

import com.yosua.homie.entity.JWTokenClaim;
import com.yosua.homie.entity.dao.User;
import com.yosua.homie.entity.dao.UserVerification;

public interface AuthService {

  String createToken(String userId);

  JWTokenClaim getTokenInformation(String token);

  Boolean isTokenValid(String token);

  User register(User user);

  User findOne(String email);

  String getUserIdFromToken(String token);

  void generateCode(User user) ;

  Boolean verifyCode(String code, User user);

  UserVerification addUserVerification(User user);

  User changePassword(String userID, String oldPassword, String newPassword);
}

