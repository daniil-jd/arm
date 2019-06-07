package ru.arm.gm.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CombinationChartDTO {
    private List<String> data;
    private List<ChartSeriesDTO> series;

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
