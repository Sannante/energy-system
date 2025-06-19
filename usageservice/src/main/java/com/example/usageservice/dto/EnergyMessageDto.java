package com.example.usageservice.dto;

import java.time.LocalDateTime;

public record EnergyMessageDto(
        String type,          // "PRODUCER" oder "USER"
        String association,   // z.b. "COMMUNITY"
        double kwh,
        LocalDateTime datetime
) {}
