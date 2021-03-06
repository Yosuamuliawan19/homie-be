package com.yosua.homie.rest.web.model.response;

import com.yosua.homie.entity.constant.enums.DeviceStatus;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class FlaskACResponse extends FlaskBaseResponse {
    private String id;
    private String name;
    private String hubURL;
    private DeviceStatus status;
    private Double temperature;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
        return "FlaskACResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", hubURL='" + hubURL + '\'' +
                ", power=" + status +
                ", temperature=" + temperature +
                '}' + super.toString();
    }
}
