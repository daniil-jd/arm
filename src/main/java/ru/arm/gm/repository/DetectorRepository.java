package ru.arm.gm.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.arm.gm.domain.Detector;
import ru.arm.gm.domain.DetectorData;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class DetectorRepository {
    private final NamedParameterJdbcTemplate template;

    public DetectorRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public List<Detector> getAll() {
        List<Detector> detectors = template.query(
                "SELECT * FROM detector",
                new BeanPropertyRowMapper<>(Detector.class));
        List<DetectorData> datas = template.query(
                "SELECT * FROM detector_data",
                new BeanPropertyRowMapper<>(DetectorData.class));

        for(Detector detector : detectors) {
            for (DetectorData data : datas) {
                if (data.getDetectorId().equals(detector.getId())) {
                    detector.getDetectorData().add(data);
                }
            }
        }

        return detectors;
    }

    public Optional<Detector> getBySerialAndIp(String serial, String ip) {
        List<Detector> results = template.query("SELECT * FROM detector WHERE SERIAL_NUMBER=:serial and IP=:ip",
                Map.of(
                        "serial", serial,
                        "ip", ip
                ), new BeanPropertyRowMapper<>(Detector.class));
        if (results.size() == 1) {
            return Optional.of(results.get(0));
        }
        return Optional.empty();
    }

    public Integer saveDetector(Detector detector) {
        Optional<Detector> det = getBySerialAndIp(detector.getSerialNumber(), detector.getIp());
        Integer id;
        if (det.isEmpty()) {
            template.update("INSERT INTO detector (IP, ADDRESS, SERIAL_NUMBER, START_DATE, WARRANTY_PERIOD) VALUES (:ip, :address, :serial, :start, :warranty)",
                    Map.of(
                            "ip", detector.getIp(),
                            "address", detector.getAddress(),
                            "serial", detector.getSerialNumber(),
                            "start", detector.getStartDate(),
                            "warranty", detector.getWarrantyPeriod()
                    ));
            id = getBySerialAndIp(detector.getSerialNumber(), detector.getIp()).get().getId();
        } else {
            return det.get().getId();
        }
        return id;
    }

    public Optional<DetectorData> getDataByDetectorIdAndDate(Integer detId, LocalDate date) {
        List<DetectorData> results = template.query("SELECT * FROM detector_data WHERE DETECTOR_ID=:detId and DATE=:date",
                Map.of(
                        "detId", detId,
                        "date", date
                ), new BeanPropertyRowMapper<>(DetectorData.class));
        if (results.size() == 1) {
            return Optional.of(results.get(0));
        }
        return Optional.empty();
    }

    public Integer saveData(DetectorData data) {
        Optional<DetectorData> dat = getDataByDetectorIdAndDate(data.getDetectorId(), data.getDate());
        Integer id;
        if (dat.isEmpty()) {
            id = template.update("INSERT INTO detector_data (DETECTOR_ID, WORK_TIME, ALL_WORK_TIME, EMERGENCY_POWER_TIME, DETECTED_COUNT, POSITIVE_DETECTED_COUNT, FREQUENCY, DATE, ERRORS) VALUES (:detId, :wTime, :aTime, :eTime, :dCount, :pCount, :freq, :date, :errors)",
                    Map.of(
                            "detId", data.getDetectorId(),
                            "wTime", data.getWorkTime(),
                            "aTime", data.getAllWorkTime(),
                            "eTime", data.getEmergencyPowerTime(),
                            "dCount", data.getDetectedCount(),
                            "pCount", data.getPositiveDetectedCount(),
                            "freq", data.getFrequency(),
                            "date", data.getDate(),
                            "errors", data.getErrors()
                    )
            );
        } else {
            return dat.get().getId();
        }
        return id;
    }
}
