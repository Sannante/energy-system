package com.example.energyuser;

import java.time.LocalDateTime;

public record UserMessage(
        String type,
        String association,
        double kwh,
        LocalDateTime datetime
) {}
