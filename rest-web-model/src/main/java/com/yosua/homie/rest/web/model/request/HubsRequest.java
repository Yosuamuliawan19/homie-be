package com.yosua.homie.rest.web.model.request;

import java.util.Arrays;
import java.util.List;

public class HubsRequest {
    private List<String> ipAddress;
    private List<String> hubPhysicalAddress;

    public List<String> getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(List<String> ipAddress) {
        this.ipAddress = ipAddress;
    }

    public List<String> getHubPhysicalAddress() {
        return hubPhysicalAddress;
    }

    public void setHubPhysicalAddress(List<String> hubPhysicalAddress) {
        this.hubPhysicalAddress = hubPhysicalAddress;
    }

    @Override
    public String toString() {
        return "HubsRequest{" +
                "ipAddress=" + ipAddress +
                ", hubPhysicalAddress=" + hubPhysicalAddress +
                '}';
    }
}
