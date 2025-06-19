package com.example.energyrestapi;

import com.example.energyrestapi.dto.CurrentEnergyDto;
import com.example.energyrestapi.dto.HistoricalEnergyDto;
import com.example.energyrestapi.dto.TotalEnergyDto;
import com.example.energyrestapi.entity.CurrentEnergyEntity;
import com.example.energyrestapi.repository.CurrentEnergyRepository;
import com.example.energyrestapi.repository.UsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/energy")
public class EnergyController {

    @Autowired
    private CurrentEnergyRepository currentRepo;

    @Autowired
    private UsageRepository usageRepository;

    @GetMapping("/current")
    public CurrentEnergyDto getCurrent() {
        CurrentEnergyEntity entity = currentRepo.findTopByOrderByHourDesc();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String formattedHour = entity.getHour().format(formatter);

        return new CurrentEnergyDto(
                formattedHour,
                entity.getCommunityDepleted(),
                entity.getGridPortion()
        );
    }


    @GetMapping("/historical")
    public List<HistoricalEnergyDto> getHistorical(@RequestParam String start, @RequestParam String end) {
        // Debug-Ausgabe (hilft dir zu sehen was ankommt)
        System.out.println("Start: " + start);
        System.out.println("End: " + end);

        // Robust gegen Sekunden/Nano-Abweichungen
        LocalDateTime startTime = LocalDateTime.parse(start).withSecond(0).withNano(0);
        LocalDateTime endTime = LocalDateTime.parse(end).withSecond(0).withNano(0);

        // Debug: Alle Zeiten aus DB ausgeben
        usageRepository.findByHourBetweenOrderByHourAsc(startTime, endTime)
                .forEach(e -> System.out.println("DB-Hour: " + e.getHour()));

        return usageRepository.findByHourBetweenOrderByHourAsc(startTime, endTime)
                .stream()
                .map(entity -> new HistoricalEnergyDto(
                        entity.getHour().toString(),
                        entity.getCommunityProduced(),
                        entity.getCommunityUsed(),
                        entity.getGridUsed()
                ))
                .toList();

    }
    @GetMapping("/total")
    public TotalEnergyDto getTotal(@RequestParam String start, @RequestParam String end) {
        LocalDateTime startTime = LocalDateTime.parse(start).withSecond(0).withNano(0);
        LocalDateTime endTime = LocalDateTime.parse(end).withSecond(0).withNano(0);

        Object[] result = usageRepository.sumAllBetweenHours(startTime, endTime);

        double produced = result[0] != null ? ((Number) result[0]).doubleValue() : 0.0;
        double used     = result[1] != null ? ((Number) result[1]).doubleValue() : 0.0;
        double grid     = result[2] != null ? ((Number) result[2]).doubleValue() : 0.0;

        // Runden auf 3 Nachkommastellen
        produced = Math.round(produced * 1000.0) / 1000.0;
        used     = Math.round(used     * 1000.0) / 1000.0;
        grid     = Math.round(grid     * 1000.0) / 1000.0;

        return new TotalEnergyDto(produced, used, grid);
    }



}
