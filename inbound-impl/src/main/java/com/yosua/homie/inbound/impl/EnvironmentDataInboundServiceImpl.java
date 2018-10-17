package com.yosua.homie.inbound.impl;

import com.yosua.homie.inbound.api.EnvironmentDataInboundService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class EnvironmentDataInboundServiceImpl implements EnvironmentDataInboundService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentDataInboundServiceImpl.class);

    @Override
    @KafkaListener(topics = "${homie.kafka.topic.environmentdata}", groupId = "${homie.kafka.environmentdata.consumerGroupId}")
    public void listen(ConsumerRecord<String, String> record) throws IOException {
        LOGGER.info("receive data request {}", record);
    }

}

