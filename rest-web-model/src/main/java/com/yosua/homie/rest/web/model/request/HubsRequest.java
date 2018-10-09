package com.yosua.homie.rest.web.model.request;

import java.util.List;

public class HubsRequest {
    private List<String> URL;
    private List<String> hubPhysicalAddress;

    public List<String> getURL() {
        return URL;
    }

    public void setURL(List<String> URL) {
        this.URL = URL;
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
                "URL=" + URL +
                ", hubPhysicalAddress=" + hubPhysicalAddress +
                '}';
    }
}
