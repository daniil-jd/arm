package ru.arm.gm.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
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

    @Override
    public String toString() {
        String result = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.writeValueAsString(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
