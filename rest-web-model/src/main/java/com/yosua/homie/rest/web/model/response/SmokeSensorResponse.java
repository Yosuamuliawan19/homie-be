package com.yosua.homie.rest.web.model.response;

import com.yosua.homie.entity.constant.enums.DeviceStatus;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.io.Serializable;

@GeneratePojoBuilder
public class SmokeSensorResponse implements Serializable{
    private String name;
    private String hubURL;
    private DeviceStatus status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHubURL() {
        return hubURL;
    }

    public void setHubURL(String hubURL) {
        this.hubURL = hubURL;
    }

    public DeviceStatus getStatus() {
        return status;
    }

    public void setStatus(DeviceStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "GasSensorResponse{" +
                "name='" + name + '\'' +
                ", hubURL='" + hubURL + '\'' +
                ", status=" + status +
                '}';
    }
}
