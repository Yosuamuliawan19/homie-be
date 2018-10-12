package com.yosua.homie.dao;

import com.yosua.homie.entity.dao.AC;
import com.yosua.homie.entity.dao.Lamp;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LampRepository extends MongoRepository<Lamp, String> {
    Lamp findLampByNameAndHubURL(String name, String hubURL);
    List<Lamp> findLampsByHubURL(String hubURL);
    Lamp findLampById(String id);
}
