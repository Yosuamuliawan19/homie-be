package com.yosua.homie.rest.web.model.response;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.io.Serializable;

@GeneratePojoBuilder
public class FlaskBaseResponse implements Serializable {

    private String status;
    private String success;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "FlaskBaseResponse{" +
                "status='" + status + '\'' +
                ", success='" + success + '\'' +
                '}';
    }
}
