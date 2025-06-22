package com.example.energyproducer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.time.LocalDateTime;
import java.util.Random;

public class MessageSender {

    private static final String QUEUE_NAME = "energy.producer";
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private final Random random = new Random();

    public void send() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            LocalDateTime now = LocalDateTime.now();

            // Simuliere Wetterdaten
            boolean isRaining = random.nextDouble() < 0.3; // 30% Regenwahrscheinlichkeit
            double windSpeed = 1 + 20 * random.nextDouble(); // Wind in m/s (1–21)

            // Berechne Wetterfaktor mit neuen Parametern
            double weatherFactor = WeatherSimulator.getWeatherFactor(now, isRaining, windSpeed);

            double baseKwh = 0.02 + (0.06 - 0.02) * random.nextDouble();
            double fluctuation = 0.8 + 0.4 * random.nextDouble();

            double kwh = baseKwh * weatherFactor * fluctuation;
            kwh = Math.round(kwh * 1000.0) / 1000.0;
            if (kwh < 0.005) kwh = 0.005;

            ProducerMessage message = new ProducerMessage(
                    "PRODUCER",
                    "COMMUNITY",
                    kwh,
                    now
            );

            String json = objectMapper.writeValueAsString(message);
            channel.basicPublish("", QUEUE_NAME, null, json.getBytes());
            System.out.println("Gesendet: " + json + " | Regen: " + isRaining + " | Wind: " + windSpeed);
        }
    }

}
