package com.yosua.homie.entity.dao;

import net.karneim.pojobuilder.GeneratePojoBuilder;


@GeneratePojoBuilder
public class Hub {
    private String URL;
    private String hubPhysicalAddress;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
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
                "URL='" + URL + '\'' +
                ", hubPhysicalAddress='" + hubPhysicalAddress + '\'' +
                '}';
    }
}
