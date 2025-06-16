package com.example.energyproducer;

import java.time.LocalDateTime;

public class WeatherSimulator {

    // Gibt einen Wetterfaktor zurück basierend auf Uhrzeit und Wochentag
    public static double getWeatherFactor(LocalDateTime time) {
        int hour = time.getHour();
        int dayOfWeek = time.getDayOfWeek().getValue(); // 1=Montag, 7=Sonntag

        // Nachts wenig Sonne
        if (hour < 6 || hour > 20) {
            return 0.1;
        }

        // Frühmorgens und Abend (weniger Sonne)
        if (hour < 9 || hour > 17) {
            return 0.4;
        }

        // Wochenende: manchmal mehr Sonne
        if (dayOfWeek == 6 || dayOfWeek == 7) {
            return 1.2;
        }

        // Standard: sonniger Tag
        return 1.0;
    }
}
