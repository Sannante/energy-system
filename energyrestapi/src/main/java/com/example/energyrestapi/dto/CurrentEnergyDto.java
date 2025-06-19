package com.example.energyrestapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CurrentEnergyDto(
        @JsonProperty("hour") String hour,
        double communityDepleted,
        double gridPortion
) {}
