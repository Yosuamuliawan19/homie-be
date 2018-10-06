package com.yosua.homie.entity.constant;

public interface ApiPath {

  String BASE_PATH = "/homie";
  String USER_CONTROLLER = "/user";
  String ADMIN_CONTROLLER = "/admin";

  String ID = "/{id}";
  String ADD_USER = BASE_PATH + ADMIN_CONTROLLER+ "/add-user";
  String SIGN_IN = BASE_PATH + USER_CONTROLLER + "/sign-in";
  String ADD_HUBS = BASE_PATH + ADMIN_CONTROLLER + "/add_hubs";
}
