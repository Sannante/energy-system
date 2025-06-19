package com.example.energyproducer;

import java.time.LocalDateTime;

public record ProducerMessage(
        String type,
        String association,
        double kwh,
        LocalDateTime datetime
) {}
