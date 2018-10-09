package com.yosua.homie.dao;

import com.yosua.homie.entity.dao.AC;
import com.yosua.homie.entity.dao.TV;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TVRepository extends MongoRepository<TV, String> {
    TV findTVByNameAndHubURL(String name, String hubURL);
    List<TV> findTVByHubURL(String hubURL);
}
