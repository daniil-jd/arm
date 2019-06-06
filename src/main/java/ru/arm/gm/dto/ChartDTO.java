package ru.arm.gm.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ResponseBody
public class ChartDTO {
    private String title;
    private String yAxisText;
    private List<String> xAxisCategories;
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
