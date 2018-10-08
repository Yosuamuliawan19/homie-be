package com.yosua.homie.entity.dao;

import com.yosua.homie.entity.constant.CollectionName;
import com.yosua.homie.entity.constant.fields.UserFields;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@GeneratePojoBuilder
@Document(collection = CollectionName.USER)
public class User extends BaseMongo{

    @Field(value = UserFields.NAME)
    private String name;

    @Field(value = UserFields.EMAIL)
    private String email;

    @Field(value = UserFields.PASSWORD)
    private String password;

    @Field(value = UserFields.PHONE_NUMBER)
    private String phoneNumber;

    @Field(value = UserFields.HUBS)
    private List<Hub> hubs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() { return email; }

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

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", hubs=" + hubs +
                '}';
    }
}
