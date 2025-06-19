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
        // Verbindung zu RabbitMQ aufbauen
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // Queue deklarieren
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // Erzeuge KWh-Wert basierend auf Wetterfaktor
            LocalDateTime now = LocalDateTime.now();
            double baseKwh = 0.02 + (0.06 - 0.02) * random.nextDouble(); // realistischer: 20–60 Wh
            double weatherFactor = WeatherSimulator.getWeatherFactor(now);
            double fluctuation = 0.8 + 0.4 * random.nextDouble(); // 0.8 – 1.2

            double kwh = baseKwh * weatherFactor * fluctuation;
            kwh = Math.round(kwh * 1000.0) / 1000.0; // runden auf 3 Nachkommastellen
            if (kwh < 0.005) kwh = 0.005; // Mindestwert, falls alles sehr niedrig


            // Nachricht zusammenbauen
            ProducerMessage message = new ProducerMessage(
                    "PRODUCER",
                    "COMMUNITY",
                    kwh,
                    now
            );

            // JSON senden
            String json = objectMapper.writeValueAsString(message);
            channel.basicPublish("", QUEUE_NAME, null, json.getBytes());
            System.out.println("Gesendet: " + json);
        }
    }
}
