package com.example.energyrestapi;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/energy")
public class EnergyController {

    @GetMapping("/current")
    public Map<String, Object> getCurrent() {
        return Map.of(
                "hour", "2025-04-10T14:00:00",
                "community_depleted", 100.0,
                "grid_portion", 5.63
        );
    }

    @GetMapping("/historical")
    public List<Map<String, Object>> getHistorical(
            @RequestParam String start,
            @RequestParam String end) {
        return List.of(
                Map.of("hour", "2025-04-10T13:00:00", "produced", 15.015, "used", 14.033, "grid", 2.049),
                Map.of("hour", "2025-04-10T14:00:00", "produced", 18.05, "used", 18.05, "grid", 1.076)
        );
    }
}
