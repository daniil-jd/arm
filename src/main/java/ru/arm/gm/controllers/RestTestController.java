package ru.arm.gm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.arm.gm.domain.Detector;
import ru.arm.gm.service.DetectorService;

import java.util.List;

@RestController
@RequestMapping("/api/detectors")
public class RestTestController {

    @Autowired
    private DetectorService service;

    @GetMapping
    public List<Detector> getDetectors() {
        return service.getAll();
    }


}
