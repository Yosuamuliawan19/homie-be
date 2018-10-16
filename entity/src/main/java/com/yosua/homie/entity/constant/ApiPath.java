package com.yosua.homie.entity.constant;

public interface ApiPath {

  String BASE_PATH = "/homie";
  String USER_CONTROLLER = "/user";
  String ADMIN_CONTROLLER = "/admin";
  String DEVICE_CONTROLLER = "/device";

  String ID = "/{id}";

  String HTTP = "http://";
  //flask
  String FLASK_TURN_ON_AC = "/AC/turn-on";
  String FLASK_TURN_OFF_AC = "/AC/turn-off";
  String FLASK_SET_TEMPERATURE_AC = "/AC/set-temp";


  String FLASK_TURN_ON_TV = "/TV/turn-on";
  String FLASK_TURN_OFF_TV = "/TV/turn-off";
  String FLASK_VOLUME_UP_TV = "/TV/volume-up";
  String FLASK_VOLUME_DOWN_TV = "/TV/volume-down";
  String FLASK_PROGRAM_UP_TV = "/TV/program-up";
  String FLASK_PROGRAM_DOWN_TV = "/TV/program-down";
  String FLASK_MUTE_TV = "/TV/mute";

  String FLASK_CHECK_FLAME = "/flame";

  String FLASK_TURN_ON_LAMP = "/lamp/turn-on";
  String FLASK_TURN_OFF_LAMP = "/lamp/turn-off";

  String FLASK_CHECK_RAIN = "/rain/";
  //Admin
  String ADD_HUBS = BASE_PATH + ADMIN_CONTROLLER + "/add-hubs";
  String ADD_USER = BASE_PATH + ADMIN_CONTROLLER+ "/add-user";
  String ADD_AC = BASE_PATH + ADMIN_CONTROLLER + "/add-ac";
  String ADD_TV = BASE_PATH + ADMIN_CONTROLLER + "/add-tv";
  String ADD_LAMP = BASE_PATH + ADMIN_CONTROLLER + "/add-lamp";
  String ADD_RAIN_SENSOR = BASE_PATH + ADMIN_CONTROLLER + "/add-rain-sensor";
  String ADD_FLAME_SENSOR = BASE_PATH + ADMIN_CONTROLLER + "/add-flame-sensor";
  //User
  String SIGN_IN = USER_CONTROLLER + "/sign-in";
  String VERIFY_CREDENTIALS = USER_CONTROLLER + "/verify-credentials";
  String EDIT_HUBS = USER_CONTROLLER + "/edit-hubs";
  String CHANGE_PASSWORD = USER_CONTROLLER + "/change-password";

  //Device
  String GET_ALL_USERS_AC = DEVICE_CONTROLLER + "/get-all-users-ac";
  String TURN_ON_AC = DEVICE_CONTROLLER + "/turn-on-ac";
  String TURN_OFF_AC = DEVICE_CONTROLLER + "/turn-off-ac";

  String GET_ALL_USERS_TV = DEVICE_CONTROLLER + "/get-all-users-tv";
  String TURN_ON_TV = DEVICE_CONTROLLER + "/turn-on-tv";
  String TURN_OFF_TV = DEVICE_CONTROLLER + "/turn-off-tv";

  String GET_ALL_USERS_LAMP = DEVICE_CONTROLLER + "/get-all-users-lamp";
  String TURN_ON_LAMP = DEVICE_CONTROLLER + "/turn-on-lamp";
  String TURN_OFF_LAMP = DEVICE_CONTROLLER + "/turn-off-lamp";

  String GET_ALL_USERS_RAIN_SENSOR = DEVICE_CONTROLLER + "/get-all-users-rain-sensor";
  String CHECK_FOR_RAIN = DEVICE_CONTROLLER + "/check-for-rain";

  String GET_ALL_USERS_FLAME_SENSOR = DEVICE_CONTROLLER + "/get-all-users-flame-sensor";
  String CHECK_FOR_FLAME = DEVICE_CONTROLLER + "/check-for-flame";

  String GET_ALL_USERS_GAS_SENSOR = DEVICE_CONTROLLER + "/get-all-users-gas-sensor";
  String CHECK_FOR_GAS = DEVICE_CONTROLLER + "/check-for-gas";
}