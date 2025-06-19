package com.example.currentpercentageservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class AggregatedUsageMessage {

    @JsonProperty("community_produced")
    private double totalProduced;

    @JsonProperty("community_used")
    private double totalUsed;

    @JsonProperty("grid_used")
    private double gridUsed;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime hour;

    public double getTotalProduced() {
        return totalProduced;
    }

    public void setTotalProduced(double totalProduced) {
        this.totalProduced = totalProduced;
    }

    public double getTotalUsed() {
        return totalUsed;
    }

    public void setTotalUsed(double totalUsed) {
        this.totalUsed = totalUsed;
    }

    public double getGridUsed() {
        return gridUsed;
    }

    public void setGridUsed(double gridUsed) {
        this.gridUsed = gridUsed;
    }

    public LocalDateTime getHour() {
        return hour;
    }

    public void setHour(LocalDateTime hour) {
        this.hour = hour;
    }
}
