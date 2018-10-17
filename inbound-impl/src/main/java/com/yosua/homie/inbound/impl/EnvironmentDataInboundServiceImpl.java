package com.yosua.homie.inbound.impl;

import com.yosua.homie.entity.constant.KafkaContainerFactoryName;
import com.yosua.homie.inbound.api.EnvironmentDataInboundService;
import com.yosua.homie.service.impl.ACServiceImpl;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EnvironmentDataInboundServiceImpl implements EnvironmentDataInboundService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentDataInboundServiceImpl.class);

    @Override
    @KafkaListener(id = "com.yosua.homie.environmentdata",topics = "${homie.kafka.topic.environment-data}", containerFactory = KafkaContainerFactoryName.ENVIRONMENT_DATA)
    public void listen(ConsumerRecord<String, String> record) throws IOException {
        LOGGER.info("receive data request {}", record);
        LOGGER.info("HELLO");
    }
}

