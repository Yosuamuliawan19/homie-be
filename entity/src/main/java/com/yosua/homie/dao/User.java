package com.yosua.homie.dao;

import com.yosua.homie.constant.CollectionName;
import com.yosua.homie.constant.fields.UserFields;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Arrays;

@GeneratePojoBuilder
@Document(collection = CollectionName.USER)
public class User {

    @Field(value = UserFields.NAME)
    private String name;

    @Field(value = UserFields.EMAIL)
    private String email;

    @Field(value = UserFields.PASSWORD)
    private String password;

    @Field(value = UserFields.PHONE_NUMBER)
    private String phoneNumber;

    @Field(value = UserFields.HUBS_ID)
    private String[] hubsID;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String[] getHubsID() { return hubsID; }

    public void setHubsID(String[] hubsID) { this.hubsID = hubsID; }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", hubsID=" + Arrays.toString(hubsID) +
                '}';
    }
}
