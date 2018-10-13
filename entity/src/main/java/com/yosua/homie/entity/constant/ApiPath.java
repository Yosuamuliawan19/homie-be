package com.yosua.homie.entity.constant;

public interface ApiPath {

  String BASE_PATH = "/homie";
  String USER_CONTROLLER = "/user";
  String ADMIN_CONTROLLER = "/admin";

  String ID = "/{id}";

  String HTTP = "http://";
  //flask
  String FLASK_TURN_ON_AC = "/AC/turn-on/";
  String FLASK_TURN_OFF_AC = "/AC/turn-off/";
  String FLASK_SET_TEMPERATURE_AC = "/AC/set-temp/";


  String FLASK_TURN_ON_TV = "/TV/turn-on/";
  String FLASK_TURN_OFF_TV = "/TV/turn-off/";
  String FLASK_VOLUME_UP_TV = "/TV/volume-up/";
  String FLASK_VOLUME_DOWN_TV = "/TV/volume-down/";
  String FLASK_PROGRAM_UP_TV = "/TV/program-up/";
  String FLASK_PROGRAM_DOWN_TV = "/TV/program-down/";
  String FLASK_MUTE_TV = "/TV/mute/";



  String FLASK_TURN_ON_LAMP = "/lamp/turn-on/";
  String FLASK_TURN_OFF_LAMP = "/lamp/turn-off/";


  String FLASK_CHECK_RAIN = "/rain/";

  //Admin
  String ADD_HUBS = BASE_PATH + ADMIN_CONTROLLER + "/add-hubs";
  String ADD_USER = BASE_PATH + ADMIN_CONTROLLER+ "/add-user";
  String ADD_AC = BASE_PATH + ADMIN_CONTROLLER + "/add-ac";
  //User
  String SIGN_IN = BASE_PATH + USER_CONTROLLER + "/sign-in";
  String VERIFY_CREDENTIALS = BASE_PATH + USER_CONTROLLER + "/verify-credentials";
  String EDIT_HUBS = BASE_PATH + USER_CONTROLLER + "/edit-hubs";
  String CHANGE_PASSWORD = BASE_PATH + USER_CONTROLLER + "/change-password";
  String GET_ALL_USERS_AC = BASE_PATH + USER_CONTROLLER + "/get-all-users-ac";
  String TURN_ON_AC = BASE_PATH + USER_CONTROLLER + "/turn-on-ac";
  String TURN_OFF_AC = BASE_PATH + USER_CONTROLLER + "/turn-off-ac";
}