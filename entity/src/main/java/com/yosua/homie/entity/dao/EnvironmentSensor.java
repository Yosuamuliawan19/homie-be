package com.yosua.homie.entity.dao;

import com.yosua.homie.entity.constant.enums.DeviceStatus;
import com.yosua.homie.entity.constant.fields.EnvironmentSensorFields;
import com.yosua.homie.entity.constant.fields.LampFields;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Field;

@GeneratePojoBuilder
public class EnvironmentSensor extends BaseMongo {

    @Field(value = EnvironmentSensorFields.HUB_URL)
    private String hubURL;

    @Field(value = EnvironmentSensorFields.TEMPERATURE)
    private Double temperature;

    @Field(value = EnvironmentSensorFields.HUMIDITY)
    private Double humidity;


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

    @Override
    public String toString() {
        return "EnvironmentSensor{" +
                "hubURL='" + hubURL + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                '}';
    }
}
