package com.example.energyuser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.time.LocalDateTime;
import java.util.Random;

public class MessageSender {

    private static final String QUEUE_NAME = "energy-queue";
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final Random random = new Random();

    public void send() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            double base = 0.001 + (0.004 - 0.001) * random.nextDouble();
            double factor = UsageSimulator.getUsageFactor(LocalDateTime.now());
            double kwh = Math.round(base * factor * 1000.0) / 1000.0;

            UserMessage message = new UserMessage(
                    "USER",
                    "COMMUNITY",
                    kwh,
                    LocalDateTime.now()
            );

            String json = objectMapper.writeValueAsString(message);
            channel.basicPublish("", QUEUE_NAME, null, json.getBytes());
            System.out.println("Gesendet: " + json);
        }
    }
}
