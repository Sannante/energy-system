package com.example.energyproducer;

import java.time.LocalDateTime;

public class WeatherSimulator {

    // Gibt einen Wetterfaktor zurück basierend auf Uhrzeit und Wochentag
    public static double getWeatherFactor(LocalDateTime time, boolean isRaining, double windSpeed) {
        int hour = time.getHour();
        int dayOfWeek = time.getDayOfWeek().getValue(); // 1=Montag, 7=Sonntag

        double factor = 1.0;

        // 🌙 Nacht – kaum Sonne
        if (hour < 6 || hour > 20) {
            factor *= 0.1;
        } else if (hour < 9 || hour > 17) {
            factor *= 0.4; // Sonnenauf-/untergang
        }

        // 🏖️ Wochenende: mehr Sonne
        if (dayOfWeek == 6 || dayOfWeek == 7) {
            factor *= 1.1;
        }

        // 🌧️ Regen reduziert die Effizienz stark
        if (isRaining) {
            factor *= 0.5;
        }

        // 💨 Wind beeinflusst leicht (kann positiv sein bei Windenergie)
        if (windSpeed > 15) {
            factor *= 1.05; // starker Wind
        } else if (windSpeed < 3) {
            factor *= 0.95; // windstill
        }

        return factor;
    }
}
