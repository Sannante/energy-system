package com.example.usageservice.service;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.example.usageservice.dto.EnergyMessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class RabbitMqListener {

    private final UsageAggregator aggregator;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private static final Logger log = LoggerFactory.getLogger(RabbitMqListener.class);

    public RabbitMqListener(UsageAggregator aggregator) {
        this.aggregator = aggregator;
    }

    @RabbitListener(queues = "energy.producer")
    public void handleProducer(String json) throws Exception {
        log.info("Producer-Nachricht empfangen: {}", json);
        EnergyMessageDto dto = objectMapper.readValue(json, EnergyMessageDto.class);
        aggregator.process(dto);
    }

    @RabbitListener(queues = "energy.user")
    public void handleUser(String json) throws Exception {
        log.info("User-Nachricht empfangen: {}", json);
        EnergyMessageDto dto = objectMapper.readValue(json, EnergyMessageDto.class);
        aggregator.process(dto);
    }
}
