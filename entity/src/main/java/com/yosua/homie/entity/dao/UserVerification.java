package com.yosua.homie.entity.dao;

import com.yosua.homie.entity.constant.CollectionName;
import com.yosua.homie.entity.constant.fields.UserVerificationFields;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@GeneratePojoBuilder
@Document(collection = CollectionName.USER_VERIFICATION)
public class UserVerification extends BaseMongo {
    @Field(value = UserVerificationFields.USER_ID)
    private String userID;

    @Field(value = UserVerificationFields.CODE)
    private String code;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "UserVerification{" +
                "userID='" + userID + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
