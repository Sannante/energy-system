package com.example.energyrestapi.repository;

import com.example.energyrestapi.entity.UsageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsageRepository extends JpaRepository<UsageEntity, LocalDateTime> {
    List<UsageEntity> findByHourBetweenOrderByHourAsc(LocalDateTime start, LocalDateTime end);
    @Query("SELECT SUM(u.communityProduced), SUM(u.communityUsed), SUM(u.gridUsed) " +
            "FROM UsageEntity u WHERE u.hour BETWEEN :start AND :end")
    Object[] sumAllBetweenHours(@Param("start") LocalDateTime start,
                                @Param("end") LocalDateTime end);


}
