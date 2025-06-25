package com.example.energyfxgui;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // <<< unbekannte felder ignorieren
public class CurrentEnergy {
    public String hour;
    public double communityDepleted;
    public double gridPortion;

    public double communityPercentage() {
        return 100.0 - gridPortion;
    }

    public double gridPercentage() {
        return gridPortion;
    }
}
