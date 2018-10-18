package com.yosua.homie.rest.web.model.response;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.io.Serializable;

@GeneratePojoBuilder
public class FlaskBaseResponse implements Serializable {

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "FlaskBaseResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}' + super.toString();
    }
}
