package com.yosua.homie.entity.dao;

import com.yosua.homie.entity.constant.CollectionName;
import com.yosua.homie.entity.constant.enums.DeviceStatus;
import com.yosua.homie.entity.constant.fields.ACFields;
import com.yosua.homie.entity.constant.fields.LampFields;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@GeneratePojoBuilder
@Document(collection = CollectionName.LAMP)
public class Lamp extends BaseMongo {
    @Field(value = LampFields.NAME)
    private String name;

    @Field(value = LampFields.HUB_URL)
    private String hubURL;

    @Field(value = LampFields.STATUS)
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
        return "Lamp{" +
                "name='" + name + '\'' +
                ", hubURL='" + hubURL + '\'' +
                ", status=" + status +
                '}' + super.toString();
    }
}

