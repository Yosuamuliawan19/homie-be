package com.yosua.homie.entity.dao;

import com.yosua.homie.entity.constant.CollectionName;
import com.yosua.homie.entity.constant.enums.DeviceStatus;
import com.yosua.homie.entity.constant.fields.ACFields;
import com.yosua.homie.entity.constant.fields.TVFields;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@GeneratePojoBuilder
@Document(collection = CollectionName.TV)
public class TV extends BaseMongo {
    @Field(value = TVFields.NAME)
    private String name;

    @Field(value = TVFields.HUB_URL)
    private String hubURL;

    @Field(value = TVFields.STATUS)
    private DeviceStatus status;

    @Field(value = TVFields.CHANNEL_NUMBER)
    private Integer channelNumber;

    @Field(value = TVFields.VOLUME)
    private Integer volume;

    @Field(value = TVFields.IS_MUTED)
    private Boolean isMuted;

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

    public Integer getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(Integer channelNumber) {
        this.channelNumber = channelNumber;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Boolean getMuted() {
        return isMuted;
    }

    public void setMuted(Boolean muted) {
        isMuted = muted;
    }

    @Override
    public String toString() {
        return "TV{" +
                "name='" + name + '\'' +
                ", hubURL='" + hubURL + '\'' +
                ", status=" + status +
                ", channelNumber=" + channelNumber +
                ", volume=" + volume +
                ", isMuted=" + isMuted +
                '}'+ super.toString();
    }
}
