package com.yosua.homie.entity.constant;

public interface ApiPath {

  String BASE_PATH = "/homie";
  String USER_CONTROLLER = "/user";
  String ADMIN_CONTROLLER = "/admin";

  String ID = "/{id}";

  String HTTP = "http://";
  //flask
  String FLASK_TURN_ON_AC = "/AC/turn-on/";

  //Admin
  String ADD_HUBS = BASE_PATH + ADMIN_CONTROLLER + "/add-hubs";
  String ADD_USER = BASE_PATH + ADMIN_CONTROLLER+ "/add-user";
  String ADD_AC = BASE_PATH + ADMIN_CONTROLLER + "/add-ac";
  //User
  String SIGN_IN = BASE_PATH + USER_CONTROLLER + "/sign-in";
  String EDIT_HUBS = BASE_PATH + USER_CONTROLLER + "/edit-hubs";
  String CHANGE_PASSWORD = BASE_PATH + USER_CONTROLLER + "/change-password";
  String GET_ALL_USERS_AC = BASE_PATH + USER_CONTROLLER + "/get-all-users-ac";
  String TURN_ON_AC = BASE_PATH + USER_CONTROLLER + "/turn-on-ac";
}