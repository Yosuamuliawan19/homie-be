package com.yosua.homie.entity.dao;

import com.yosua.homie.entity.constant.enums.DeviceStatus;
import com.yosua.homie.entity.constant.fields.LampFields;
import org.springframework.data.mongodb.core.mapping.Field;

public class EnvironmentSensor extends BaseMongo {
    @Field(value = LampFields.NAME)
    private String name;

    @Field(value = LampFields.HUB_URL)
    private String hubURL;

    @Field(value = LampFields.STATUS)
    private DeviceStatus status;
}
