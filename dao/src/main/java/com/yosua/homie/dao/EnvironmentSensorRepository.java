package com.yosua.homie.dao;

import com.yosua.homie.entity.dao.EnvironmentSensor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface EnvironmentSensorRepository extends MongoRepository<EnvironmentSensor, String> {
    EnvironmentSensor findEnvironmentSensorByHubURLAndServerTime(String hubURL, Date serverTime);
    List<EnvironmentSensor> findEnvironmentSensorSByHubURL(String hubURL);
    EnvironmentSensor findEnvironmentSensorById(String id);
}
