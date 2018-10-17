package com.yosua.homie.inbound.api;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.IOException;

public interface EnvironmentDataInboundService {
    void listen(ConsumerRecord<String, String> record)throws IOException;
}
