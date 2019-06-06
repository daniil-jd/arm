package ru.arm.gm.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
    private ChartSeriesDTO series1;
    private ChartSeriesDTO series2;

    @Override
    public String toString() {
        String result = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.writeValueAsString(this);
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        return ("{\"title\":\"Обнаружения и проходы через детектор\",\"series1\":{\"name\":\"Обнаружения\",\"data\":[302,322,432,510,365,558,576]},\"series2\":{\"name\":\"Проходы\",\"data\":[654,614,854,921,883,1019,812]},\"yaxisText\":\"Количество обнаружений/проходов через металлодетектор\",\"xaxisCategories\":[\"12.12\",\"13.12\",\"14.12\",\"15.12\",\"16.12\",\"17.12\",\"18.12\"]}");

    }
}
