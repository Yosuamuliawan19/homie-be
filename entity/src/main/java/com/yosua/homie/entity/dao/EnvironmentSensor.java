package com.yosua.homie.entity.dao;

import com.yosua.homie.entity.constant.fields.EnvironmentSensorFields;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@GeneratePojoBuilder
public class EnvironmentSensor extends BaseMongo {

    @Field(value = EnvironmentSensorFields.HUB_URL)
    private String hubURL;

    @Field(value = EnvironmentSensorFields.TEMPERATURE)
    private Double temperature;

    @Field(value = EnvironmentSensorFields.HUMIDITY)
    private Double humidity;

    @Field(value = EnvironmentSensorFields.SERVER_TIME)
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
        return "EnvironmentSensor{" +
                "hubURL='" + hubURL + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", serverTime=" + serverTime +
                '}' + super.toString();
    }
}
