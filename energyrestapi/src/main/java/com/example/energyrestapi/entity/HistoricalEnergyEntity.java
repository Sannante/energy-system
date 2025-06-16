package com.example.energyrestapi.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usage_table")
public class HistoricalEnergyEntity {

    @Id
    @Column(name = "hour")
    private LocalDateTime hour;

    @Column(name = "community_produced")
    private double produced;

    @Column(name = "community_used")
    private double used;

    @Column(name = "grid_used")
    private double grid;

    // Getter + Setter
    public LocalDateTime getHour() { return hour; }
    public void setHour(LocalDateTime hour) { this.hour = hour; }

    public double getProduced() { return produced; }
    public void setProduced(double produced) { this.produced = produced; }

    public double getUsed() { return used; }
    public void setUsed(double used) { this.used = used; }

    public double getGrid() { return grid; }
    public void setGrid(double grid) { this.grid = grid; }
}
