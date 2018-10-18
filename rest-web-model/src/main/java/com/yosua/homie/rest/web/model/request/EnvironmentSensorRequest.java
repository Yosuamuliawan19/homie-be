package com.yosua.homie.rest.web.model.request;

import java.util.Date;

public class EnvironmentSensorRequest {
    private String hubURL;
    private Double temperature;
    private Double humidity;
    private Date serverTime;

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

    public Date getServerTime() {
        return serverTime;
    }

    public void setServerTime(Date serverTime) {
        this.serverTime = serverTime;
    }

    @Override
    public String toString() {
        return "EnvironmentSensorRequest{" +
                "hubURL='" + hubURL + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", serverTime=" + serverTime +
                '}';
    }
}
