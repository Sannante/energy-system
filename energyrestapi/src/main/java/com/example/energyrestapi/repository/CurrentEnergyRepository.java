package com.example.energyrestapi.repository;

import com.example.energyrestapi.entity.CurrentEnergyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrentEnergyRepository extends JpaRepository<CurrentEnergyEntity, String> {
    CurrentEnergyEntity findTopByOrderByHourDesc();
}
