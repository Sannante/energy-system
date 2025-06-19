package com.example.energyfxgui;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TotalEnergy {
    private double totalProduced;
    private double totalUsed;
    private double totalGrid;

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

    public double getTotalGrid() {
        return totalGrid;
    }

    public void setTotalGrid(double totalGrid) {
        this.totalGrid = totalGrid;
    }
}
