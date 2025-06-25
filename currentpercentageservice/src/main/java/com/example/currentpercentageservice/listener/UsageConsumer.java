package com.example.currentpercentageservice.listener;

import com.example.currentpercentageservice.model.AggregatedUsageMessage;
import com.example.currentpercentageservice.service.PercentageCalculator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UsageConsumer {

    private final ObjectMapper objectMapper;
    private final PercentageCalculator calculator;

    public UsageConsumer(ObjectMapper objectMapper, PercentageCalculator calculator) {
        this.objectMapper = objectMapper;
        this.calculator = calculator;
    }

    @RabbitListener(queues = "aggregated.usage")
    public void receiveMessage(String message) {
        try {
            System.out.println("🔔 Nachricht empfangen: " + message);
            AggregatedUsageMessage usage = objectMapper.readValue(message, AggregatedUsageMessage.class);
            calculator.calculateAndSave(usage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
