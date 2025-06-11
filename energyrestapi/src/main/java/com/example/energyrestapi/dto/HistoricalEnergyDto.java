package com.example.energyrestapi.dto;

public record HistoricalEnergyDto(String hour, double produced, double used, double grid) {}
