package com.yosua.homie.dao;

import com.yosua.homie.entity.dao.GasSensor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GasSensorRepository extends MongoRepository<GasSensor, String>{

    GasSensor findGasSensorByNameAndHubURL(String name, String HubURL);
    List<GasSensor> findGasSensorsByHubURL(String hubURL);
    GasSensor findGasSensorById(String id);
}
