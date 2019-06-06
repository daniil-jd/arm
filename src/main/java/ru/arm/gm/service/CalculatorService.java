package ru.arm.gm.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.arm.gm.domain.Detector;
import ru.arm.gm.domain.DetectorData;
import ru.arm.gm.domain.Period;
import ru.arm.gm.domain.ValueWithAttribute;
import ru.arm.gm.dto.CalculateDTO;
import ru.arm.gm.dto.ChartDTO;
import ru.arm.gm.dto.ChartSeriesDTO;
import ru.arm.gm.dto.ColorStatus;
import ru.arm.gm.dto.DetectorDTO;
import ru.arm.gm.dto.WarrantyDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalculatorService {

    public CalculateDTO calculateAllParameters(Detector detector, Period period) {
        List<DetectorData> detectorData = detector.getDetectorData();
        WarrantyDTO warrantyDTO = calculateWarrantyPeriod(detector);
        int size = detectorData.size();
        if (size > 0) {
            if (period == Period.DAY) {
                return new CalculateDTO(
                        warrantyDTO,
                        calculateWorkTimeInPeriod(detectorData, 1),
                        calculateEmergencyWorkTimeInPeriod(detectorData, 1),
                        calculatePositiveDetectedCountInPeriod(detectorData, 1),
                        calculateErrorCountInPeriod(detectorData, 1),
                        calculateDetectedCountInPeriod(detectorData, 1),
                        calculatePositiveDetectedCountSpeedInPeriod(detectorData, 1),
                        prepareChartData(detector, 1));
            } else if (period == Period.WEEK) {
                return new CalculateDTO(
                        warrantyDTO,
                        calculateWorkTimeInPeriod(detectorData, 7),
                        calculateEmergencyWorkTimeInPeriod(detectorData, 7),
                        calculatePositiveDetectedCountInPeriod(detectorData, 7),
                        calculateErrorCountInPeriod(detectorData, 7),
                        calculateDetectedCountInPeriod(detectorData, 7),
                        calculatePositiveDetectedCountSpeedInPeriod(detectorData, 7),
                        prepareChartData(detector, 7));
            } else if (period == Period.MONTH) {
                return new CalculateDTO(
                        warrantyDTO,
                        calculateWorkTimeInPeriod(detectorData, 31),
                        calculateEmergencyWorkTimeInPeriod(detectorData, 31),
                        calculatePositiveDetectedCountInPeriod(detectorData, 31),
                        calculateErrorCountInPeriod(detectorData, 31),
                        calculateDetectedCountInPeriod(detectorData, 31),
                        calculatePositiveDetectedCountSpeedInPeriod(detectorData, 31),
                        prepareChartData(detector, 31));
            }
        }
        return new CalculateDTO(
                warrantyDTO,
                -1,
                -1,
                -1,
                -1,
                -1,
                -1,
                prepareChartData(detector, -1)
        );
    }

    public DetectorDTO prepareChartData(Detector detector, int days) {
        if (days > 0) {
            return new DetectorDTO(
                    detector.getIp(),
                    detector.getAddress(),
                    detector.getSerialNumber(),
                    detector.getStartDate().toString(),
                    new ValueWithAttribute(detector.getWarrantyPeriod(), ""),
                    new ValueWithAttribute(String.valueOf(sumWorkTimeInPeriod(detector.getDetectorData(), days)), ""),
                    new ValueWithAttribute(String.valueOf(sumAllWorkTimeInPeriod(detector.getDetectorData(), days)), ""),
                    new ValueWithAttribute(String.valueOf(sumEmergencyTimeInPeriod(detector.getDetectorData(), days)), ""),
                    sumDetectedCountInPeriod(detector.getDetectorData(), days),
                    sumPositiveDetectedCountInPeriod(detector.getDetectorData(), days),
                    new ValueWithAttribute(String.valueOf(detector.getDetectorData().get(0).getFrequency()), ""),
                    sumErrorsInPeriod(detector.getDetectorData(), days),
                    detector.getDetectorData().get(detector.getDetectorData().size() - 1).getDate().toString()
            );
        }
        return new DetectorDTO(
                detector.getIp(),
                detector.getAddress(),
                detector.getSerialNumber(),
                detector.getStartDate().toString(),
                new ValueWithAttribute(detector.getWarrantyPeriod(), ""),
                new ValueWithAttribute(String.valueOf(-1), ""),
                new ValueWithAttribute(String.valueOf(-1), ""),
                new ValueWithAttribute(String.valueOf(-1), ""),
                -1,
                -1,
                new ValueWithAttribute(String.valueOf(-1), ""),
                "-1",
                "-1"
        );
    }

    public ChartDTO buildChartFromDetector(Detector detector, Period period) {
        List<DetectorData> detectorData = detector.getDetectorData();
        List<DetectorData> calculateData = detectorData.stream().collect(Collectors.toList());
        List<String> days = new ArrayList<>();
        String title = "Обнаружения и проходы через детектор";
        String yAxisText = "Количество обнаружений/проходов через металлодетектор";
        List<ChartSeriesDTO> series = new ArrayList<>();
        ChartSeriesDTO positiveDetected = new ChartSeriesDTO("Обнаружения");
        ChartSeriesDTO detected = new ChartSeriesDTO("Проходы");

        int size = detectorData.size();
        int length;
        if (size > 0) {
            if (period == Period.DAY) {
                days.add(detectorData.get(size-1).getDate().format(DateTimeFormatter.ofPattern("dd.MM")));
                positiveDetected.getData().add(detectorData.get(size-1).getPositiveDetectedCount());
                detected.getData().add(detectorData.get(size-1).getDetectedCount());
            } else if (period == Period.WEEK) {
                length = 7;
                if (size < length) {
                    length = size;
                }
                Collections.reverse(calculateData);
                for (int i = 0; i < length; i++) {
                    days.add(calculateData.get(i).getDate().format(DateTimeFormatter.ofPattern("dd.MM")));
                    positiveDetected.getData().add(calculateData.get(i).getPositiveDetectedCount());
                    detected.getData().add(calculateData.get(i).getDetectedCount());
                }
            } else {
                length = 31;
                if (size < length) {
                    length = size;
                }
                Collections.reverse(calculateData);
                for (int i = 0; i < length; i++) {
                    days.add(calculateData.get(i).getDate().format(DateTimeFormatter.ofPattern("dd.MM")));
                    positiveDetected.getData().add(calculateData.get(i).getPositiveDetectedCount());
                    detected.getData().add(calculateData.get(i).getDetectedCount());
                }
            }
        }
        Collections.reverse(days);
        Collections.reverse(positiveDetected.getData());
        Collections.reverse(detected.getData());
        return new ChartDTO(title, yAxisText, days, positiveDetected, detected);
    }

    private double sumWorkTimeInPeriod(List<DetectorData> sourceData, int days) {
        int size = sourceData.size();
        int lastDet = sourceData.size() - 1;
        List<DetectorData> calculateData = sourceData.stream().collect(Collectors.toList());
        double workTime = 0;

        if (days == 1) {
            workTime = sourceData.get(lastDet).getWorkTime();
            return workTime;
        } else {
            Collections.reverse(calculateData);
            if (size >= days) {
                for (int i = 0; i < days; i++) {
                    workTime += calculateData.get(i).getWorkTime();
                }
                return workTime;
            } else {
                for (int i = 0; i < size; i++) {
                    workTime += calculateData.get(i).getWorkTime();
                }
                return workTime;
            }
        }
    }

    private double sumAllWorkTimeInPeriod(List<DetectorData> sourceData, int days) {
        int size = sourceData.size();
        int lastDet = sourceData.size() - 1;
        List<DetectorData> calculateData = sourceData.stream().collect(Collectors.toList());
        double allWorkTime = 0;

        if (days == 1) {
            allWorkTime = sourceData.get(lastDet).getAllWorkTime();
            return allWorkTime;
        } else {
            Collections.reverse(calculateData);
            if (size >= days) {
                for (int i = 0; i < days; i++) {
                    allWorkTime += calculateData.get(i).getAllWorkTime();
                }
                return allWorkTime;
            } else {
                for (int i = 0; i < size; i++) {
                    allWorkTime += calculateData.get(i).getAllWorkTime();
                }
                return allWorkTime;
            }
        }
    }

    private double sumEmergencyTimeInPeriod(List<DetectorData> sourceData, int days) {
        int size = sourceData.size();
        int lastDet = sourceData.size() - 1;
        List<DetectorData> calculateData = sourceData.stream().collect(Collectors.toList());
        double emrgTime = 0;

        if (days == 1) {
            emrgTime = sourceData.get(lastDet).getEmergencyPowerTime();
            return emrgTime;
        } else {
            Collections.reverse(calculateData);
            if (size >= days) {
                for (int i = 0; i < days; i++) {
                    emrgTime += calculateData.get(i).getEmergencyPowerTime();
                }
                return emrgTime;
            } else {
                for (int i = 0; i < size; i++) {
                    emrgTime += calculateData.get(i).getEmergencyPowerTime();
                }
                return emrgTime;
            }
        }
    }

    private int sumDetectedCountInPeriod(List<DetectorData> sourceData, int days) {
        int size = sourceData.size();
        int lastDet = sourceData.size() - 1;
        List<DetectorData> calculateData = sourceData.stream().collect(Collectors.toList());
        int detectedCount = 0;

        if (days == 1) {
            detectedCount = sourceData.get(lastDet).getDetectedCount();
            return detectedCount;
        } else {
            Collections.reverse(calculateData);
            if (size >= days) {
                for (int i = 0; i < days; i++) {
                    detectedCount += calculateData.get(i).getDetectedCount();
                }
                return detectedCount;
            } else {
                for (int i = 0; i < size; i++) {
                    detectedCount += calculateData.get(i).getDetectedCount();
                }
                return detectedCount;
            }
        }
    }

    private int sumPositiveDetectedCountInPeriod(List<DetectorData> sourceData, int days) {
        int size = sourceData.size();
        int lastDet = sourceData.size() - 1;
        List<DetectorData> calculateData = sourceData.stream().collect(Collectors.toList());
        int positiveDetectedCount = 0;

        if (days == 1) {
            positiveDetectedCount = sourceData.get(lastDet).getPositiveDetectedCount();
            return positiveDetectedCount;
        } else {
            Collections.reverse(calculateData);
            if (size >= days) {
                for (int i = 0; i < days; i++) {
                    positiveDetectedCount += calculateData.get(i).getPositiveDetectedCount();
                }
                return positiveDetectedCount;
            } else {
                for (int i = 0; i < size; i++) {
                    positiveDetectedCount += calculateData.get(i).getPositiveDetectedCount();
                }
                return positiveDetectedCount;
            }
        }
    }

    private String sumErrorsInPeriod(List<DetectorData> sourceData, int days) {
        int size = sourceData.size();
        int lastDet = sourceData.size() - 1;
        List<DetectorData> calculateData = sourceData.stream().collect(Collectors.toList());
        String errors = "";

        if (days == 1) {
            errors = sourceData.get(lastDet).getErrors();
            return errors;
        } else {
            Collections.reverse(calculateData);
            if (size < days) {
                days = size;
            }
            for (int i = 0; i < days; i++) {
                if (!StringUtils.isEmpty(calculateData.get(i).getErrors()) && calculateData.get(i).getErrors().length() > 2) {
                    errors += calculateData.get(i).getErrors() + ";";
                }
            }
            return errors;
        }
    }

    /**
     * Вычисляет истек ли гарантийный период.
     * Если период не истек, подсчитывает количесво лет/месяцев до истечения.
     * @param detector данные о детекторе
     * @return объект с информацией о гарантийном периоде
     */
    public WarrantyDTO calculateWarrantyPeriod(Detector detector) {
        LocalDate warranty = detector.getStartDate();
        warranty = warranty.plusYears(Long.parseLong(detector.getWarrantyPeriod()));
        if (detector.getDetectorData().size() > 0) {
            LocalDate lastDate = detector.getDetectorData()
                    .get((detector.getDetectorData()).size() - 1).getDate();
            boolean warrantyOn = warranty.isAfter(lastDate);
            if (warrantyOn) {
                return compareDates(warranty, lastDate);
            } else {
                return new WarrantyDTO(
                        false,
                        "Гарантийный период истек.",
                        ColorStatus.RED
                );
            }
        }
        return new WarrantyDTO(
                false,
                "Детектор не введен в эксплуатацию.",
                ColorStatus.NONE
        );
    }

    /**
     * Сравнивает даты.
     * @param warranty дата конца гарантийного периода
     * @param current текущая дата
     * @return объект с информацией о гарантийном периоде
     */
    private WarrantyDTO compareDates(LocalDate warranty, LocalDate current) {
        String data = "До истечения гарантийного периода: %s %s";
        WarrantyDTO warrantyDTO = new WarrantyDTO(true);
        if (warranty.getYear() - current.getYear() > 0) {
            warrantyDTO.setMessage(String.format(data, warranty.getYear() - current.getYear(), "года(год)."));
            warrantyDTO.setColor(ColorStatus.GREEN);
            return warrantyDTO;
        } else if (warranty.getMonthValue() - warranty.getMonthValue() > 3) {
            warrantyDTO.setMessage(String.format(data, warranty.getMonthValue() - warranty.getMonthValue(), "месяцев."));
            warrantyDTO.setColor(ColorStatus.GREEN);
            return warrantyDTO;
        }

        warrantyDTO.setMessage(String.format(data, "", "менее 3 месяцев."));
        warrantyDTO.setColor(ColorStatus.YELLOW);
        return warrantyDTO;
    }

    @Deprecated
    public double calculateRelativeWorkTimeOld(Detector detector, Period period) {
        List<DetectorData> detectorData = detector.getDetectorData();
        int size = detectorData.size();
        if (size > 0) {
            int lastDet = detectorData.size() - 1;
            double workTime = 0;
            double allWorkTime = 0;
            double result;
            if (period == Period.DAY) {
                workTime = detectorData.get(lastDet).getWorkTime();
                allWorkTime = detectorData.get(lastDet).getAllWorkTime();
                result = workTime/allWorkTime * 100;
                return BigDecimal.valueOf(result)
                        .setScale(3, RoundingMode.HALF_UP)
                        .doubleValue();
            } else if (period == Period.WEEK) {
                Collections.reverse(detectorData);
                if (size >= 7) {
                    for (int i = 0; i < 7; i++) {
                        workTime += detectorData.get(i).getWorkTime();
                        allWorkTime += detectorData.get(i).getAllWorkTime();
                    }
                    result = workTime/allWorkTime * 100;
                    return  BigDecimal.valueOf(result)
                            .setScale(3, RoundingMode.HALF_UP)
                            .doubleValue();
                } else {
                    for (int i = 0; i < size; i++) {
                        workTime += detectorData.get(i).getWorkTime();
                        allWorkTime += detectorData.get(i).getAllWorkTime();
                    }
                    result = workTime/allWorkTime * 100;
                    return BigDecimal.valueOf(result)
                            .setScale(3, RoundingMode.HALF_UP)
                            .doubleValue();
                }
            } else if (period == Period.MONTH) {
                Collections.reverse(detectorData);
                if (size >= 31) {
                    for (int i = 0; i < 31; i++) {
                        workTime += detectorData.get(i).getWorkTime();
                        allWorkTime += detectorData.get(i).getAllWorkTime();
                    }
                    result = workTime/allWorkTime * 100;
                    return  BigDecimal.valueOf(result)
                            .setScale(3, RoundingMode.HALF_UP)
                            .doubleValue();
                } else {
                    for (int i = 0; i < size; i++) {
                        workTime += detectorData.get(i).getWorkTime();
                        allWorkTime += detectorData.get(i).getAllWorkTime();
                    }
                    result = workTime/allWorkTime * 100;
                    return BigDecimal.valueOf(result)
                            .setScale(3, RoundingMode.HALF_UP)
                            .doubleValue();
                }
            }
        }
        return -1;
    }

    /**
     * Подсчитывает время работы в определенный период.
     * @param sourceData список данных детектора по дням
     * @param days количество дней в периоде
     * @return относительное время работы
     */
    private double calculateWorkTimeInPeriod(List<DetectorData> sourceData, int days) {
        int size = sourceData.size();
        int lastDet = sourceData.size() - 1;
        List<DetectorData> calculateData = sourceData.stream().collect(Collectors.toList());
        double workTime = 0;
        double allWorkTime = 0;

        if (days == 1) {
            workTime = sourceData.get(lastDet).getWorkTime();
            allWorkTime = sourceData.get(lastDet).getAllWorkTime();
            return relativeCalculateInPercent(workTime, allWorkTime);
        } else {
            Collections.reverse(calculateData);
            if (size >= days) {
                for (int i = 0; i < days; i++) {
                    workTime += calculateData.get(i).getWorkTime();
                    allWorkTime += calculateData.get(i).getAllWorkTime();
                }
                return relativeCalculateInPercent(workTime, allWorkTime);
            } else {
                for (int i = 0; i < size; i++) {
                    workTime += calculateData.get(i).getWorkTime();
                    allWorkTime += calculateData.get(i).getAllWorkTime();
                }
                return relativeCalculateInPercent(workTime, allWorkTime);
            }
        }
    }

    private double calculateEmergencyWorkTimeInPeriod(List<DetectorData> sourceData, int days) {
        int size = sourceData.size();
        int lastDet = sourceData.size() - 1;
        List<DetectorData> calculateData = sourceData.stream().collect(Collectors.toList());
        double workTime = 0;
        double allWorkTime = 0;

        if (days == 1) {
            workTime = sourceData.get(lastDet).getEmergencyPowerTime();
            allWorkTime = sourceData.get(lastDet).getAllWorkTime();
            return relativeCalculateInPercent(workTime, allWorkTime);
        } else {
            Collections.reverse(calculateData);
            if (size >= days) {
                for (int i = 0; i < days; i++) {
                    workTime += calculateData.get(i).getEmergencyPowerTime();
                    allWorkTime += calculateData.get(i).getAllWorkTime();
                }
                return relativeCalculateInPercent(workTime, allWorkTime);
            } else {
                for (int i = 0; i < size; i++) {
                    workTime += calculateData.get(i).getEmergencyPowerTime();
                    allWorkTime += calculateData.get(i).getAllWorkTime();
                }
                return relativeCalculateInPercent(workTime, allWorkTime);
            }
        }
    }

    private double calculatePositiveDetectedCountInPeriod(List<DetectorData> sourceData, int days) {
        int size = sourceData.size();
        int lastDet = sourceData.size() - 1;
        List<DetectorData> calculateData = sourceData.stream().collect(Collectors.toList());
        double positiveCount = 0;
        double count = 0;

        if (days == 1) {
            positiveCount = sourceData.get(lastDet).getPositiveDetectedCount();
            count = sourceData.get(lastDet).getDetectedCount();
            return relativeCalculateInPercent(positiveCount, count);
        } else {
            Collections.reverse(calculateData);
            if (size >= days) {
                for (int i = 0; i < days; i++) {
                    positiveCount += calculateData.get(i).getPositiveDetectedCount();
                    count += calculateData.get(i).getDetectedCount();
                }
                return relativeCalculateInPercent(positiveCount, count);
            } else {
                for (int i = 0; i < size; i++) {
                    positiveCount += calculateData.get(i).getPositiveDetectedCount();
                    count += calculateData.get(i).getDetectedCount();
                }
                return relativeCalculateInPercent(positiveCount, count);
            }
        }
    }

    private double calculateErrorCountInPeriod(List<DetectorData> sourceData, int days) {
        int size = sourceData.size();
        int lastDet = sourceData.size() - 1;
        List<DetectorData> calculateData = sourceData.stream().collect(Collectors.toList());
        double errorCount = 0;
        double workTime = 0;

        if (days == 1) {
            errorCount = sourceData.get(lastDet).getErrors().split(",").length;
            workTime = sourceData.get(lastDet).getWorkTime();
            return relativeCalculate(errorCount, workTime);
        } else {
            Collections.reverse(calculateData);
            if (size >= days) {
                for (int i = 0; i < days; i++) {
                    errorCount += calculateData.get(i).getErrors().split(",").length;
                    workTime += calculateData.get(i).getWorkTime();
                }
                return relativeCalculate(errorCount, workTime);
            } else {
                for (int i = 0; i < size; i++) {
                    errorCount += calculateData.get(i).getErrors().split(",").length;
                    workTime += calculateData.get(i).getWorkTime();
                }
                return relativeCalculate(errorCount, workTime);
            }
        }
    }

    private double calculateDetectedCountInPeriod(List<DetectorData> sourceData, int days) {
        int size = sourceData.size();
        int lastDet = sourceData.size() - 1;
        List<DetectorData> calculateData = sourceData.stream().collect(Collectors.toList());
        double detectedCount = 0;
        double workTime = 0;

        if (days == 1) {
            detectedCount = sourceData.get(lastDet).getDetectedCount();
            workTime = sourceData.get(lastDet).getWorkTime();
            return relativeCalculate(detectedCount, workTime);
        } else {
            Collections.reverse(calculateData);
            if (size >= days) {
                for (int i = 0; i < days; i++) {
                    detectedCount += calculateData.get(i).getDetectedCount();
                    workTime += calculateData.get(i).getWorkTime();
                }
                return relativeCalculate(detectedCount, workTime);
            } else {
                for (int i = 0; i < size; i++) {
                    detectedCount += calculateData.get(i).getDetectedCount();
                    workTime += calculateData.get(i).getWorkTime();
                }
                return relativeCalculate(detectedCount, workTime);
            }
        }
    }

    private double calculatePositiveDetectedCountSpeedInPeriod(List<DetectorData> sourceData, int days) {
        int size = sourceData.size();
        int lastDet = sourceData.size() - 1;
        List<DetectorData> calculateData = sourceData.stream().collect(Collectors.toList());
        double detectedCount = 0;
        double workTime = 0;

        if (days == 1) {
            detectedCount = sourceData.get(lastDet).getPositiveDetectedCount();
            workTime = sourceData.get(lastDet).getWorkTime();
            return relativeCalculate(detectedCount, workTime);
        } else {
            Collections.reverse(calculateData);
            if (size >= days) {
                for (int i = 0; i < days; i++) {
                    detectedCount += calculateData.get(i).getPositiveDetectedCount();
                    workTime += calculateData.get(i).getWorkTime();
                }
                return relativeCalculate(detectedCount, workTime);
            } else {
                for (int i = 0; i < size; i++) {
                    detectedCount += calculateData.get(i).getPositiveDetectedCount();
                    workTime += calculateData.get(i).getWorkTime();
                }
                return relativeCalculate(detectedCount, workTime);
            }
        }
    }

    private double relativeCalculateInPercent(double x1, double x2) {
        return setScaleToDouble(x1/x2 * 100);
    }

    private double relativeCalculate(double x1, double x2) {
        return setScaleToDouble(x1/x2);
    }

    private double setScaleToDouble(double d) {
        return BigDecimal.valueOf(d)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
    }

}
