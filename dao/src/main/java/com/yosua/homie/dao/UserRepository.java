package com.yosua.homie.dao;

import com.yosua.homie.entity.dao.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findUserByEmail(String email);
    User findUserById(String id);
    User findUserByHubsURL(String URL);

}
