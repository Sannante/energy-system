package com.example.energyuser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.time.LocalDateTime;
import java.util.Random;

public class MessageSender {

    private static final String QUEUE_NAME = "energy.user";
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final Random random = new Random();

    public void send() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection(); //verbindung herstellen
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null); // gibts die queue mit dem namen schon

            double base = 0.02 + (0.05 - 0.02) * random.nextDouble(); // Basisverbrauch 0.02–0.05
            double factor = UsageSimulator.getUsageFactor(LocalDateTime.now()); // z.B. tagsüber höher
            double fluctuation = 0.8 + 0.4 * random.nextDouble(); // 0.8–1.2

            double kwh = base * factor * fluctuation;
            kwh = Math.round(kwh * 1000.0) / 1000.0;
            if (kwh < 0.005) kwh = 0.005; // Mindestwert, um sinnvolle Werte zu garantieren

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
