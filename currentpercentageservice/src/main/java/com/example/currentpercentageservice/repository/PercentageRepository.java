package com.example.currentpercentageservice.repository;

import com.example.currentpercentageservice.entity.PercentageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PercentageRepository extends JpaRepository<PercentageEntity, Long> {
    Optional<PercentageEntity> findByHour(LocalDateTime hour);
}
