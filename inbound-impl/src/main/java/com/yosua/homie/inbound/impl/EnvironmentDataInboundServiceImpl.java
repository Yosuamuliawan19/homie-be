package com.yosua.homie.inbound.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yosua.homie.entity.constant.enums.ResponseCode;
import com.yosua.homie.entity.dao.EnvironmentSensor;
import com.yosua.homie.inbound.api.EnvironmentDataInboundService;
import com.yosua.homie.libraries.exception.BusinessLogicException;
import com.yosua.homie.rest.web.model.request.EnvironmentSensorRequest;
import com.yosua.homie.service.api.EnvironmentSensorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class EnvironmentDataInboundServiceImpl implements EnvironmentDataInboundService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentDataInboundServiceImpl.class);

    @Autowired
    EnvironmentSensorService environmentSensorService;

    @Override
    @KafkaListener(topics = "${homie.kafka.topic.environmentdata}", groupId = "${homie.kafka.environmentdata.consumerGroupId}")
    public void listen(ConsumerRecord<String ,String> record) throws IOException {
        LOGGER.info("receive data request {}", record);
        ObjectMapper mapper = new ObjectMapper();
        String recordValue;
        EnvironmentSensorRequest environmentSensorRequest;
        try{
            recordValue = record.value().replace("\\","");
            LOGGER.info(recordValue);
            environmentSensorRequest = mapper.readValue(recordValue, EnvironmentSensorRequest.class);
            LOGGER.info(environmentSensorRequest.toString());
        } catch (Exception e) {
            throw new BusinessLogicException(ResponseCode.INVALID_JSON_FORMAT.getCode(),
                    ResponseCode.INVALID_JSON_FORMAT.getMessage());
        }
        EnvironmentSensor environmentSensor = environmentSensorService.addEnvironmentSensor(environmentSensorRequest);
        LOGGER.info(environmentSensor.toString());
    }
}

