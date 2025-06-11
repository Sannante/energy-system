package com.example.energyrestapi;
import com.example.energyrestapi.dto.CurrentEnergyDto;
import com.example.energyrestapi.dto.HistoricalEnergyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/energy")
public class EnergyController {

    @Autowired
    private JdbcTemplate jdbc;

    @GetMapping("/current")
    public CurrentEnergyDto getCurrent() {
        return jdbc.queryForObject(
                "SELECT * FROM percentage_table ORDER BY hour DESC LIMIT 1",
                (rs, rowNum) -> new CurrentEnergyDto(
                        rs.getTimestamp("hour").toLocalDateTime().toString(),
                        rs.getDouble("community_depleted"),
                        rs.getDouble("grid_portion")
                )
        );
    }

    @GetMapping("/historical")
    public List<HistoricalEnergyDto> getHistorical(
            @RequestParam String start,
            @RequestParam String end) {

        return jdbc.query(
                "SELECT * FROM usage_table WHERE hour BETWEEN ? AND ? ORDER BY hour",
                new Object[]{start, end},
                (rs, rowNum) -> new HistoricalEnergyDto(
                        rs.getTimestamp("hour").toLocalDateTime().toString(),
                        rs.getDouble("community_produced"),
                        rs.getDouble("community_used"),
                        rs.getDouble("grid_used")
                )
        );
    }
}
