package com.example.usageservice.repository;

import com.example.usageservice.entity.UsageEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UsageRepository extends JpaRepository<UsageEntry, LocalDateTime> {
    Optional<UsageEntry> findByHour(LocalDateTime hour);
}
