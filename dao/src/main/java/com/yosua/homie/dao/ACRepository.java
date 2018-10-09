package com.yosua.homie.dao;

import com.yosua.homie.entity.dao.AC;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ACRepository extends MongoRepository<AC, String> {
    AC findACByNameAndHubURL(String name, String hubURL);
    List<AC> findACSByHubURL(String hubURL);
    AC findACById(String id);
}
