package com.yosua.homie.dao;

import com.yosua.homie.entity.dao.EnvironmentSensor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EnvironmentSensorRepository extends MongoRepository<EnvironmentSensor, String> {
    EnvironmentSensor findEnvironmentSensorByHubURL(String hubURL);
    List<EnvironmentSensor> findEnvironmentSensorSByHubURL(String hubURL);
    EnvironmentSensor findEnvironmentSensorById(String id);
}
