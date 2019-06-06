package ru.arm.gm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartSeriesDTO {
    private String name;
    private List<Integer> data = new ArrayList<>();

    public ChartSeriesDTO(String name) {
        this.name = name;
    }
}
