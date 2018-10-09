package com.yosua.homie.entity.constant.enums;

public enum DeviceStatus {

    ON("ON","ON"),
    OFF("OFF","OFF");

    private String code;
    private String message;

    DeviceStatus(String code, String message) {
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
