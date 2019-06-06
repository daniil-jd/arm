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
        for (Detector detector : detectors) {
            calcDTOs.add(calculatorService.calculateAllParameters(
                    detectors.get(0), Period.WEEK));
        }

        List<String> xLabels = Arrays.asList("1.12", "2.12", "3.12", "4.12", "5.12", "6.12" ,"7.12", "8.12");
        List<Integer> totalCount = Arrays.asList(674, 820, 1156, 1024, 1055, 998, 832, 720);
        List<Integer> positiveCount = Arrays.asList(350, 452, 683, 601, 598, 535, 340, 340);

        model.addAttribute("totalCount", totalCount);
        model.addAttribute("positiveCount", positiveCount);
        model.addAttribute("xLabels", xLabels);
        model.addAttribute("calcs", calcDTOs);
        model.addAttribute("chart_1", calculatorService.buildChartFromDetector(detectors.get(0), Period.WEEK));
        return "calculate";
    }

}
