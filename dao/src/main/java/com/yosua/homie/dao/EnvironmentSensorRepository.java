package com.yosua.homie.dao;

import com.yosua.homie.entity.dao.EnvironmentSensor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public interface EnvironmentSensorRepository extends MongoRepository<EnvironmentSensor, String> {
    EnvironmentSensor findEnvironmentSensorByHubURLAndServerTime(String hubURL, Date serverTime);
    List<EnvironmentSensor> findEnvironmentSensorSByHubURL(String hubURL);
    EnvironmentSensor findEnvironmentSensorById(String id);

    //lte: Less Than or Equal to || gte: Greater Than or Equal to
    @Query(value = "{'serverTime':{ $lte: ?0, $gte: ?1}}")
    List<EnvironmentSensor> findEnvironmentSensorByServerTimeBetween(Instant endDate, Instant startDate);
}
