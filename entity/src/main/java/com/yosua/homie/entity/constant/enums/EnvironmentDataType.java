package com.yosua.homie.entity.constant.enums;

public enum EnvironmentDataType {
    TEMPERATURE("TEMPERATURE","TEMPERATURE"),
    HUMIDITY("HUMIDITY","HUMIDITY");

    private String code;
    private String message;

    EnvironmentDataType(String code, String message) {
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
