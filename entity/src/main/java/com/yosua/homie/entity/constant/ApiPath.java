package com.yosua.homie.entity.constant;

public interface ApiPath {

  String BASE_PATH = "/homie";
  String USER_CONTROLLER = "/user";
  String ADMIN_CONTROLLER = "/admin";

  String ID = "/{id}";

  //Admin
  String ADD_HUBS = BASE_PATH + ADMIN_CONTROLLER + "/add-hubs";
  String ADD_USER = BASE_PATH + ADMIN_CONTROLLER+ "/add-user";

  //User
  String SIGN_IN = BASE_PATH + USER_CONTROLLER + "/sign-in";
  String VERIFY_CREDENTIALS = BASE_PATH + USER_CONTROLLER + "/verify-credentials";
  String EDIT_HUBS = BASE_PATH + USER_CONTROLLER + "/edit-hubs";
  String CHANGE_PASSWORD = BASE_PATH + USER_CONTROLLER + "/change-password";
}