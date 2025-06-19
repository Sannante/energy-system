package com.example.currentpercentageservice.service;

import com.example.currentpercentageservice.entity.PercentageEntity;
import com.example.currentpercentageservice.model.AggregatedUsageMessage;
import com.example.currentpercentageservice.repository.PercentageRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PercentageCalculator {

    private final PercentageRepository repository;

    public PercentageCalculator(PercentageRepository repository) {
        this.repository = repository;
    }

    public void calculateAndSave(AggregatedUsageMessage msg) {
        System.out.println("🔍 Neue Message erhalten für Stunde: " + msg.getHour());

        double used = msg.getTotalUsed();
        double produced = msg.getTotalProduced();
        double gridUsed = msg.getGridUsed();

        double total = used + gridUsed;

        double communityPortion = total == 0 ? 0 : used / total;
        double gridPortion = total == 0 ? 0 : gridUsed / total;

        double roundedCommunity = Math.round(communityPortion * 10000.0) / 100.0;
        double roundedGrid = Math.round(gridPortion * 10000.0) / 100.0;

        Optional<PercentageEntity> existing = repository.findByHour(msg.getHour());

        if (existing.isPresent()) {
            PercentageEntity entity = existing.get();
            entity.setCommunityDepleted(roundedCommunity);
            entity.setGridPortion(roundedGrid);
            repository.save(entity); // Update
            System.out.printf("🔄 UPDATED: %s | depleted=%.2f %% | grid=%.2f %%\n",
                    msg.getHour(), roundedCommunity, roundedGrid);
        } else {
            PercentageEntity entity = new PercentageEntity();
            entity.setHour(msg.getHour());
            entity.setCommunityDepleted(roundedCommunity);
            entity.setGridPortion(roundedGrid);
            repository.save(entity); // Insert
            System.out.printf("💾 INSERTED: %s | depleted=%.2f %% | grid=%.2f %%\n",
                    msg.getHour(), roundedCommunity, roundedGrid);
        }
    }
}
