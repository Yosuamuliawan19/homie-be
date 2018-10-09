package com.yosua.homie.dao;

import com.yosua.homie.entity.dao.AC;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ACRepository extends MongoRepository<AC, String> {
    AC findACByNameAndHubIP(String name, String hubIP);
    List<AC> findACSByHubIP(String hubIP);
}
