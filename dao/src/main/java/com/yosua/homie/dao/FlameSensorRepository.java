package com.yosua.homie.dao;

import com.yosua.homie.entity.dao.FlameSensor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FlameSensorRepository extends MongoRepository<FlameSensor, String> {
    FlameSensor findFlameSensorByNameAndHubURL(String name, String hubURL);
    List<FlameSensor> findFlameSensorsByHubURL(String hubURL);
    FlameSensor findFlameSensorById(String id);
}
