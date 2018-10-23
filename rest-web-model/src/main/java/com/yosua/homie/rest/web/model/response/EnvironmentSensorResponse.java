package com.yosua.homie.rest.web.model.response;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.io.Serializable;
import java.util.Date;

@GeneratePojoBuilder
public class EnvironmentSensorResponse implements Serializable {
    private String hubURL;
    private Double temperature;
    private Double humidity;
    private String serverTime;

    public String getHubURL() {
        return hubURL;
    }

    public void setHubURL(String hubURL) {
        this.hubURL = hubURL;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    @Override
    public String toString() {
        return "EnvironmentSensorResponse{" +
                "hubURL='" + hubURL + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", serverTime=" + serverTime +
                '}';
    }
}
