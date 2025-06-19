package com.example.energyrestapi.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usage_table")
public class UsageEntity {

    @Id
    private LocalDateTime hour;

    @Column(name = "grid_used")
    private double gridUsed;

    @Column(name = "community_produced")
    private double communityProduced;

    @Column(name = "community_used")
    private double communityUsed;

    public double getGridUsed() {
        return gridUsed;
    }
    public double getCommunityProduced() {
        return communityProduced;
    }
    public double getCommunityUsed() {
        return communityUsed;
    }

    public LocalDateTime getHour() {
        return hour;
    }
}
