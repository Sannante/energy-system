package com.example.energyrestapi.repository;

import com.example.energyrestapi.entity.CurrentEnergyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface CurrentEnergyRepository extends JpaRepository<CurrentEnergyEntity, LocalDateTime> {
    CurrentEnergyEntity findTopByOrderByHourDesc();
}
