package com.yosua.homie.entity.constant.enums;

public enum ResponseCode {
  SUCCESS("SUCCESS", "SUCCESS"),
  SYSTEM_ERROR("SYSTEM_ERROR", "Contact our team"),
  DUPLICATE_DATA("DUPLICATED_DATA", "Duplicated data"),
  DATA_NOT_EXIST("DATA_DOES_NOT_EXIST", "No data exist"),
  BIND_ERROR("BIND_ERROR", "Please fill in mandatory parameter"),
  INVALID_PASSWORD("INVALID_PASSWORD", "Invalid Password"),
  RUNTIME_ERROR("RUNTIME_ERROR", "Runtime Error"),
  INVALID_TOKEN("INVALID_TOKEN", "Token is not valid"),
  INVALID_PERMISSION("INVALID_PERMISSION", "Invalid permission"),
  USER_DOES_NOT_EXIST("USER_DOES_NOT_EXIST","Specified user does not exist"),
  PASSWORDS_DOES_NOT_MATCH("PASSWORDS_DOES_NOT_MATCH", "Passwords does not match");

  private String code;
  private String message;

  ResponseCode(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
