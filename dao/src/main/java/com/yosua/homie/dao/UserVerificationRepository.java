package com.yosua.homie.dao;

import com.yosua.homie.entity.dao.User;
import com.yosua.homie.entity.dao.UserVerification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserVerificationRepository extends MongoRepository<UserVerification, String> {
    UserVerification findUserVerificationByUserID(String id);
    UserVerification findUserVerificationByCode(String code);
}
