package com.yosua.homie.rest.web.model.response;

import com.yosua.homie.entity.constant.enums.DeviceStatus;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.io.Serializable;

@GeneratePojoBuilder
public class ACResponse implements Serializable {
    private String name;
    private String hubIP;
    private DeviceStatus status;
    private Double temperature;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHubIP() {
        return hubIP;
    }

    public void setHubIP(String hubIP) {
        this.hubIP = hubIP;
    }

    public DeviceStatus getStatus() {
        return status;
    }

    public void setStatus(DeviceStatus status) {
        this.status = status;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "ACResponse{" +
                "name='" + name + '\'' +
                ", hubIP='" + hubIP + '\'' +
                ", status=" + status +
                ", temperature=" + temperature +
                '}';
    }
}
