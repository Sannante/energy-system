package com.example.energyrestapi.dto;

public class TotalEnergyDto {
    private double totalProduced;
    private double totalUsed;
    private double totalGrid;

    public TotalEnergyDto(double totalProduced, double totalUsed, double totalGrid) {
        this.totalProduced = totalProduced;
        this.totalUsed = totalUsed;
        this.totalGrid = totalGrid;
    }

    public double getTotalProduced() {
        return totalProduced;
    }

    public double getTotalUsed() {
        return totalUsed;
    }

    public double getTotalGrid() {
        return totalGrid;
    }
}
