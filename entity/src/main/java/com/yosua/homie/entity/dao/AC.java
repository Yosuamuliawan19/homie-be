package com.yosua.homie.entity.dao;

import com.yosua.homie.entity.constant.CollectionName;
import com.yosua.homie.entity.constant.enums.DeviceStatus;
import com.yosua.homie.entity.constant.fields.ACFields;
import com.yosua.homie.entity.constant.fields.UserFields;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@GeneratePojoBuilder
@Document(collection = CollectionName.AC)
public class AC extends BaseMongo {
    @Field(value = ACFields.NAME)
    private String name;

    @Field(value = ACFields.HUB_URL)
    private String hubURL;

    @Field(value = ACFields.STATUS)
    private DeviceStatus status;

    @Field(value = ACFields.TEMPERATURE)
    private Double temperature;

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

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "AC{" +
                "name='" + name + '\'' +
                ", hubURL='" + hubURL + '\'' +
                ", status=" + status +
                ", temperature=" + temperature +
                '}' + super.toString();
    }
}
