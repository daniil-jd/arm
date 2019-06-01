package ru.arm.gm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

/**
 * Класс-сущность "Детектор".
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//@XmlRootElement(name = "detector")
@XmlAccessorType(XmlAccessType.FIELD)
public class DetectorData {
    /**
     * id.
     */
    @XmlAttribute
    private Integer id;

    @XmlElement
    private Integer detectorId;

    /**
     * Время непрерывной работы.
     */
    @XmlElement
    private String workTime;

    /**
     * Общее время работы.
     */
    @XmlElement
    private String allWorkTime;

    /**
     * Время работы на аварийном питании.
     */
    @XmlElement
    private String emergencyPowerTime;

    /**
     * Количество проходов.
     */
    @XmlElement
    private long detectedCount;

    /**
     * Количество срабатываний.
     */
    @XmlElement
    private long positiveDetectedCount;

    /**
     * Рабочая частота.
     */
    @XmlElement
    private double frequency;

    /**
     * Дата обращения.
     */
    @XmlElement
    private LocalDate date;

    /**
     * Обшибки в процессе работы.
     */
    @XmlElement
    private String errors;
}
