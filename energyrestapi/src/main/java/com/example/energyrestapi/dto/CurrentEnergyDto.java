package com.example.energyrestapi.dto;

public record CurrentEnergyDto(String hour, double communityDepleted, double gridPortion) {}
