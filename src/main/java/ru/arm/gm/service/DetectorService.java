package ru.arm.gm.service;

import org.springframework.stereotype.Service;
import ru.arm.gm.domain.Detector;
import ru.arm.gm.dto.DetectorDTO;
import ru.arm.gm.repository.DetectorRepository;

import java.util.List;

@Service
public class DetectorService {
    private final DetectorRepository repository;

    public DetectorService(DetectorRepository repository) {
        this.repository = repository;
    }

    public List<Detector> getAll() {
        return repository.getAll();
    }

    public List<Detector> saveDetectors(List<Detector> detectorsToSave) {
        detectorsToSave.forEach(detector -> {
            Integer id = repository.saveDetector(detector);
            detector.getDetectorData().forEach(data -> {
                data.setDetectorId(id);
                repository.saveData(data);
            });
        });
        return getAll();
    }
}
