package com.example.energyrestapi.repository;

import com.example.energyrestapi.entity.HistoricalEnergyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface HistoricalEnergyRepository extends JpaRepository<HistoricalEnergyEntity, String> {
    List<HistoricalEnergyEntity> findByHourBetweenOrderByHour(LocalDateTime start, LocalDateTime end);
}
