package com.example.currentpercentageservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue aggregatedUsageQueue() {
        return new Queue("aggregated.usage", true); // durable = true
    }
}
