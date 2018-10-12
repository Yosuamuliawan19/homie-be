package com.yosua.homie.dao;

import com.yosua.homie.entity.dao.AC;
import com.yosua.homie.entity.dao.RainSensor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RainSensorRepository  extends MongoRepository<RainSensor, String> {
    RainSensor findRainSensorByNameAndHubURL(String name, String hubURL);
    List<RainSensor> findRainSensorsByHubURL(String hubURL);
    RainSensor findRainSensorById(String id);
}
