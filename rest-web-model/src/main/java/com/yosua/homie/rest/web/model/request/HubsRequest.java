package com.yosua.homie.rest.web.model.request;

import java.util.Arrays;

public class HubsRequest {
    private String ipAddress[];
    private String hubPhysicalAddress[];
    private String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String[] getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String[] ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String[] getHubPhysicalAddress() {
        return hubPhysicalAddress;
    }

    public void setHubPhysicalAddress(String[] hubPhysicalAddress) {
        this.hubPhysicalAddress = hubPhysicalAddress;
    }

    @Override
    public String toString() {
        return "HubsRequest{" +
                "ipAddress=" + Arrays.toString(ipAddress) +
                ", hubPhysicalAddress=" + Arrays.toString(hubPhysicalAddress) +
                ", userID='" + userID + '\'' +
                '}';
    }
}
