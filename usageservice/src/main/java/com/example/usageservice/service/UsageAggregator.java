package com.example.usageservice.service;

import com.example.usageservice.dto.EnergyMessageDto;
import com.example.usageservice.entity.UsageEntry;
import com.example.usageservice.repository.UsageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UsageAggregator {

    private final UsageRepository repository;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UsageAggregator(UsageRepository repository, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void process(EnergyMessageDto dto) {
        LocalDateTime hour = dto.datetime().withMinute(0).withSecond(0).withNano(0);

        UsageEntry entry = repository.findByHour(hour)
                .orElse(new UsageEntry(hour));

        if ("PRODUCER".equalsIgnoreCase(dto.type())) {
            entry.setProduced(entry.getProduced() + dto.kwh());
        } else if ("USER".equalsIgnoreCase(dto.type())) {
            double available = entry.getProduced() - entry.getUsed();
            if (dto.kwh() <= available) {
                entry.setUsed(entry.getUsed() + dto.kwh());
            } else {
                entry.setUsed(entry.getProduced());
                double gridNeed = dto.kwh() - available;
                entry.setGrid(entry.getGrid() + gridNeed);
            }
        }
        System.out.println("Saving entry: " + entry.getHour() + " | produced=" + entry.getProduced() + " | used=" + entry.getUsed() + " | grid=" + entry.getGrid());

        repository.save(entry);
        sendAggregatedMessage(entry);
    }

    private void sendAggregatedMessage(UsageEntry entry) {
        try {
            Map<String, Object> msg = Map.of(
                    "hour", entry.getHour().toString(),
                    "community_produced", entry.getProduced(),
                    "community_used", entry.getUsed(),
                    "grid_used", entry.getGrid()
            );
            String json = objectMapper.writeValueAsString(msg);
            rabbitTemplate.convertAndSend("aggregated.usage", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
