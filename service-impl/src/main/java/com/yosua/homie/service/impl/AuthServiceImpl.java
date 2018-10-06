package com.yosua.homie.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yosua.homie.dao.UserRepository;
import com.yosua.homie.entity.JWTokenClaim;
import com.yosua.homie.entity.JWTokenClaimBuilder;
import com.yosua.homie.entity.constant.enums.ResponseCode;
import com.yosua.homie.entity.dao.User;
import com.yosua.homie.entity.dao.UserBuilder;
import com.yosua.homie.libraries.exception.BusinessLogicException;
import com.yosua.homie.libraries.utility.PasswordHelper;
import com.yosua.homie.service.api.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

  @Autowired
  private UserRepository userRepository;

  @Value("${homie.auth.secret}")
  private String TOKEN_SECRET;

  @Override
  public String createToken(String userId) {
    try {
      Date currentDate = new Date();
      Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
      return JWT.create()
          .withClaim("userId", userId)
          .withClaim("createdAt", currentDate)
          .withExpiresAt(new Date(currentDate.getTime() + 3600000))
          .sign(algorithm);
    } catch (Exception exception) {
      LOGGER.error(exception.getMessage());
      throw new BusinessLogicException(ResponseCode.RUNTIME_ERROR.getCode(),
          ResponseCode.RUNTIME_ERROR.getMessage());
    }
  }

  @Override
  public JWTokenClaim getTokenInformation(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
      JWTVerifier verifier = JWT.require(algorithm)
          .build();
      DecodedJWT jwt = verifier.verify(token);
      return new JWTokenClaimBuilder()
          .withUserId(jwt.getClaim("userId").asString())
          .build();

    } catch (Exception exception) {
      LOGGER.error(exception.getMessage());
      throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
          ResponseCode.INVALID_TOKEN.getMessage());
    }
  }

  @Override
  public Boolean isTokenValid(String token) {
    JWTokenClaim jwTokenClaim = this.getTokenInformation(token);
    return jwTokenClaim != null;
  }

  @Override
  public String getUserIdFromToken(String token)
  {
    JWTokenClaim jwTokenClaim = this.getTokenInformation(token);
    return jwTokenClaim.getUserId();
  }

  @Override
  public User register(User user) {
    User newUser;
    if(PasswordHelper.isPasswordValid(user.getPassword())) {
         newUser = new UserBuilder()
                .withPassword(PasswordHelper.encryptPassword(user.getPassword()))
                .withName(user.getName())
                .withEmail(user.getEmail())
                .withPhoneNumber(user.getPhoneNumber())
                .withHubsID(user.getHubsID())
                .build();
          try{
            return userRepository.save(newUser);
          } catch (Exception e) {
            throw new BusinessLogicException(ResponseCode.DUPLICATE_DATA.getCode(),
                    ResponseCode.DUPLICATE_DATA.getMessage());
          }
    }
    else
      throw new BusinessLogicException(ResponseCode.INVALID_PASSWORD.getCode(), ResponseCode.INVALID_PASSWORD.getMessage());
  }

  @Override
  public User findOne(String email) {

    User user = userRepository.findUserByEmail(email);

    if (user == null) {
      throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
              ResponseCode.DATA_NOT_EXIST.getMessage());
    }
    return user;
  }
}