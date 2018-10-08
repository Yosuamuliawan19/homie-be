package com.yosua.homie.rest.web.model.response;

import com.yosua.homie.entity.dao.Hub;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.List;

@GeneratePojoBuilder
public class UserResponse implements Serializable {

    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private List<Hub> hubs;
    private String token;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Hub> getHubs() {
        return hubs;
    }

    public void setHubs(List<Hub> hubs) {
        this.hubs = hubs;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", hubs=" + hubs +
                ", token='" + token + '\'' +
                '}';
    }
}
