package com.example.energyuser;

import java.time.LocalDateTime;

public class UsageSimulator {

    public static double getUsageFactor(LocalDateTime time) {
        int hour = time.getHour();

        if (hour >= 7 && hour <= 9) return 1.5;     // Morgens viel Verbrauch
        if (hour >= 18 && hour <= 22) return 1.8;   // Abends Spitzenzeit
        if (hour >= 0 && hour <= 5) return 0.4;     // Nachts wenig Verbrauch
        return 1.0;                                 // Restlicher Tag
    }
}
