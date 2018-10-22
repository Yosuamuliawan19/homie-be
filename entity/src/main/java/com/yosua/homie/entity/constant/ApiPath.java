package com.yosua.homie.entity.constant;

public interface ApiPath {

  String BASE_PATH = "/homie";
  String USER_CONTROLLER = "/user";
  String ADMIN_CONTROLLER = "/admin";
  String DEVICE_CONTROLLER = "/device";
  String ENVIRONMENT_CONTROLLER = "/environment/data";

  String AC = "/AC";
  String TV = "/TV";
  String LAMP = "Lamp";
  String ENVIRONMENT = "Environment";

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
  String FLASK_CHECK_GAS = "/gas";
  String FLASK_TURN_ON_LAMP = "/lamp/turn-on";
  String FLASK_TURN_OFF_LAMP = "/lamp/turn-off";

  String FLASK_CHECK_RAIN = "/rain/";
  //Admin
  String ADD_HUBS = ADMIN_CONTROLLER + "/add-hubs";
  String ADD_USER = ADMIN_CONTROLLER+ "/add-user";
  String ADD_AC = ADMIN_CONTROLLER + "/add-ac";
  String ADD_TV = ADMIN_CONTROLLER + "/add-tv";
  String ADD_LAMP = ADMIN_CONTROLLER + "/add-lamp";
  String ADD_RAIN_SENSOR = ADMIN_CONTROLLER + "/add-rain-sensor";
  String ADD_FLAME_SENSOR = ADMIN_CONTROLLER + "/add-flame-sensor";
  String ADD_GAS_SENSOR = ADMIN_CONTROLLER  + "/add-gas-sensor";
  String ADD_SMOKE_SENSOR = ADMIN_CONTROLLER  + "/add-smoke-sensor";
  //User
  String SIGN_IN = USER_CONTROLLER + "/sign-in";
  String VERIFY_CREDENTIALS = USER_CONTROLLER + "/verify-credentials";
  String EDIT_HUBS = USER_CONTROLLER + "/edit-hubs";
  String CHANGE_PASSWORD = USER_CONTROLLER + "/change-password";
  String EDIT_NOTIFICATION_TOKEN = USER_CONTROLLER + "/edit-notification-token";
  //Device
  String GET_ALL_USERS_AC = DEVICE_CONTROLLER + AC + "/get-all-users-ac";
  String SET_TEMPERATURE = DEVICE_CONTROLLER + AC + "/set-temperature";
  String TURN_ON_AC = DEVICE_CONTROLLER + AC + "/turn-on-ac";
  String TURN_OFF_AC = DEVICE_CONTROLLER +AC + "/turn-off-ac";
  String GET_AC_BY_DEVICE_ID =  DEVICE_CONTROLLER + AC + "/get-ac-by-device-id";
  String SET_TIMER_AC = DEVICE_CONTROLLER + AC + "/set-timer-ac";

  String GET_ALL_USERS_TV = DEVICE_CONTROLLER + TV + "/get-all-users-tv";
  String TURN_ON_TV = DEVICE_CONTROLLER + TV + "/turn-on-tv";
  String TURN_OFF_TV = DEVICE_CONTROLLER + TV + "/turn-off-tv";
  String GET_TV_BY_DEVICE_ID =  DEVICE_CONTROLLER + TV + "/get-tv-by-device-id";
  String SET_TIMER_TV = DEVICE_CONTROLLER + TV + "/set-timer-tv";
  String TURN_UP_VOLUME_TV = DEVICE_CONTROLLER + TV + "/volume-up";
  String TURN_DOWN_VOLUME_TV = DEVICE_CONTROLLER + TV + "/volume-down";

  String GET_ALL_USERS_LAMP = DEVICE_CONTROLLER + LAMP + "/get-all-users-lamp";
  String TURN_ON_LAMP = DEVICE_CONTROLLER + LAMP + "/turn-on-lamp";
  String TURN_OFF_LAMP = DEVICE_CONTROLLER + LAMP + "/turn-off-lamp";
  String GET_LAMP_BY_DEVICE_ID =  DEVICE_CONTROLLER + LAMP + "/get-lamp-by-device-id";
  String SET_TIMER_LAMP =  DEVICE_CONTROLLER + LAMP + "/set-timer-lamp";

  String GET_ALL_USERS_RAIN_SENSOR = DEVICE_CONTROLLER + ENVIRONMENT + "/get-all-users-rain-sensor";
  String CHECK_FOR_RAIN = DEVICE_CONTROLLER + ENVIRONMENT + "/check-for-rain";

  String GET_ALL_USERS_FLAME_SENSOR = DEVICE_CONTROLLER + ENVIRONMENT + "/get-all-users-flame-sensor";
  String CHECK_FOR_FLAME = DEVICE_CONTROLLER + ENVIRONMENT + "/check-for-flame";

  String GET_ALL_USERS_GAS_SENSOR = DEVICE_CONTROLLER + ENVIRONMENT + "/get-all-users-gas-sensor";
  String CHECK_FOR_GAS = DEVICE_CONTROLLER + ENVIRONMENT + "/check-for-gas";

  String GET_ALL_USERS_SMOKE_SENSOR = DEVICE_CONTROLLER + ENVIRONMENT + "/get-all-users-smoke-sensor";
  String CHECK_FOR_SMOKE = DEVICE_CONTROLLER + ENVIRONMENT + "/check-for-smoke";

  //Environment Control
  String GET_TEMPERATURE_DATA = ENVIRONMENT_CONTROLLER + "/get-temperature-data";
  String GET_HUMIDITY_DATA = ENVIRONMENT_CONTROLLER + "/get-humidity-data";

  //Notification
  String NOTIFY_IN_CASE_OF_GAS = "/gas-notification";
  String NOTIFY_IN_CASE_OF_FLAME = "/flame-notification";
  String NOTIFY_IN_CASE_OF_RAIN = "/rain-notification";
}