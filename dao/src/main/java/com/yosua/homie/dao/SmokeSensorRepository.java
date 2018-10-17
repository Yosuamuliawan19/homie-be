package com.yosua.homie.dao;

import com.yosua.homie.entity.dao.SmokeSensor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SmokeSensorRepository extends MongoRepository<SmokeSensor, String>{
    SmokeSensor findSmokeSensorByNameAndHubURL(String name, String URL);
    List<SmokeSensor> findSmokeSensorsByHubURL(String URL);
    SmokeSensor findSmokeSensorById(String id);
}
