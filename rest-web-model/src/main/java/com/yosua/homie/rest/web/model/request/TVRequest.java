package com.yosua.homie.rest.web.model.request;

import com.yosua.homie.entity.constant.enums.DeviceStatus;
import com.yosua.homie.entity.constant.fields.TVFields;
import com.yosua.homie.entity.dao.BaseMongo;
import org.springframework.data.mongodb.core.mapping.Field;

public class TVRequest {
    private String name;
    private String hubURL;
    private DeviceStatus status;
    private Integer channelNumber;
    private Integer volume;
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
        return "TVRequest{" +
                "name='" + name + '\'' +
                ", hubURL='" + hubURL + '\'' +
                ", status=" + status +
                ", channelNumber=" + channelNumber +
                ", volume=" + volume +
                ", isMuted=" + isMuted +
                '}';
    }
}
