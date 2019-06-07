package ru.arm.gm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.arm.gm.domain.Detector;
import ru.arm.gm.domain.Period;
import ru.arm.gm.dto.CalculateDTO;
import ru.arm.gm.dto.ChartDTO;
import ru.arm.gm.service.CalculatorService;
import ru.arm.gm.service.DetectorService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/calculate")
public class CalculatorController {

    @Autowired
    private DetectorService detectorService;

    @Autowired
    private CalculatorService calculatorService;


    @GetMapping
    public String calculateAllParams(Model model) {
        var detectors = detectorService.getAll();
        var calcDTOs = new ArrayList<CalculateDTO>();
        var chartDTOs = new ArrayList<ChartDTO>();
        for (Detector detector : detectors) {
            calcDTOs.add(calculatorService.calculateAllParameters(
                    detector, Period.WEEK));
//            chartDTOs.add(calculatorService.buildChartFromDetector(
//                    detector, Period.WEEK));
        }

        ChartDTO chart = calculatorService.buildChartFromDetector(detectors.get(0), Period.WEEK);
        ChartDTO chart2 = calculatorService.buildChartFromDetector(detectors.get(1), Period.WEEK);
        var list = calculatorService.prepareCombinationChartData(detectors, Period.WEEK);

        model.addAttribute("calcs", calcDTOs);
        model.addAttribute("chart_1", chart);
        model.addAttribute("chart_2", chart2);
        model.addAttribute("combination_chart", list);
        return "calculate";
    }
}
