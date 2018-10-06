package com.yosua.homie.entity.dao;

import net.karneim.pojobuilder.GeneratePojoBuilder;


@GeneratePojoBuilder
public class Hub {
    private String ipAddress;
    private String hubPhysicalAddress;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getHubPhysicalAddress() {
        return hubPhysicalAddress;
    }

    public void setHubPhysicalAddress(String hubPhysicalAddress) {
        this.hubPhysicalAddress = hubPhysicalAddress;
    }

    @Override
    public String toString() {
        return "Hub{" +
                "ipAddress='" + ipAddress + '\'' +
                ", hubPhysicalAddress='" + hubPhysicalAddress + '\'' +
                '}';
    }
}
