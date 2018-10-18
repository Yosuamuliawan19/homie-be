package com.yosua.homie.rest.web.model.response;

import com.yosua.homie.entity.constant.enums.DeviceStatus;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class FlaskTVResponse extends FlaskBaseResponse{
    private String name;
    private String hubURL;
    private DeviceStatus status;
    private Integer channelNumber;
    private Integer volume;
    private Boolean isMuted;
    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FlaskTVResponse{" +
                "name='" + name + '\'' +
                ", hubURL='" + hubURL + '\'' +
                ", status=" + status +
                ", channelNumber=" + channelNumber +
                ", volume=" + volume +
                ", isMuted=" + isMuted +
                ", id='" + id + '\'' +
                '}' + super.toString();
    }
}
