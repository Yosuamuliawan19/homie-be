package com.yosua.homie.rest.web.model.request;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@GeneratePojoBuilder
public class MandatoryRequest implements Serializable {

  private String channelId;

  private String accessToken;

  public String getChannelId() {
    return channelId;
  }

  public void setChannelId(String channelId) {
    this.channelId = channelId;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  @Override
  public String toString() {
    return "MandatoryRequest{" +
        "channelId='" + channelId + '\'' +
        ", accessToken='" + accessToken + '\'' +
        '}';
  }
}
