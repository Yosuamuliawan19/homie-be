package com.yosua.homie.entity.dao;

import com.yosua.homie.entity.constant.CollectionName;
import com.yosua.homie.entity.constant.enums.DeviceStatus;
import com.yosua.homie.entity.constant.fields.RainSensorFields;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@GeneratePojoBuilder
@Document(collection = CollectionName.RAIN_SENSOR)
public class RainSensor extends BaseMongo {
    @Field(value = RainSensorFields.NAME)
    private String name;

    @Field(value = RainSensorFields.HUB_URL)
    private String hubURL;

    @Field(value = RainSensorFields.STATUS)
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
        return "RainSensor{" +
                "name='" + name + '\'' +
                ", hubURL='" + hubURL + '\'' +
                ", status=" + status +
                '}'+ super.toString();
    }
}
