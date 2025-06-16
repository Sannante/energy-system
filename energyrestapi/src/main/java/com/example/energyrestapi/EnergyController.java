package com.example.energyrestapi;

import com.example.energyrestapi.dto.CurrentEnergyDto;
import com.example.energyrestapi.dto.HistoricalEnergyDto;
import com.example.energyrestapi.entity.CurrentEnergyEntity;
import com.example.energyrestapi.entity.HistoricalEnergyEntity;
import com.example.energyrestapi.repository.CurrentEnergyRepository;
import com.example.energyrestapi.repository.HistoricalEnergyRepository;
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
    private HistoricalEnergyRepository historicalRepo;

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
        LocalDateTime startTime = LocalDateTime.parse(start);
        LocalDateTime endTime = LocalDateTime.parse(end);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:MM");

        return historicalRepo.findByHourBetweenOrderByHour(startTime, endTime)
                .stream()
                .map(entity -> new HistoricalEnergyDto(
                        entity.getHour().format(formatter),
                        entity.getProduced(),
                        entity.getUsed(),
                        entity.getGrid()
                ))
                .toList();
    }
}
