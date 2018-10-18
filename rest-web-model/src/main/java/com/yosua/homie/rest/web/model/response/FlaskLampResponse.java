package com.yosua.homie.rest.web.model.response;

import com.yosua.homie.entity.constant.enums.DeviceStatus;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class FlaskLampResponse extends  FlaskBaseResponse{
    private String name;
    private String hubURL;
    private DeviceStatus status;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FlaskLampResponse{" +
                "name='" + name + '\'' +
                ", hubURL='" + hubURL + '\'' +
                ", status=" + status +
                ", id='" + id + '\'' +
                '}';
    }
}
