package ru.arm.gm.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.arm.gm.domain.ValueWithAttribute;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class DetectorDTO {
    @XmlElement
    private String ip;

    @XmlElement(name = "address")
    private String address;

    @XmlElement(name = "serialNumber")
    private String serial;

    @XmlElement(name = "start_date")
    private String startDate;

    @XmlElement(name = "warranty_period")
    private ValueWithAttribute warrantyPeriod;

    @XmlElement(name = "work_time")
    private ValueWithAttribute workTime;

    @XmlElement(name = "all_work_time")
    private ValueWithAttribute allWorkTime;

    @XmlElement(name = "emergency_power_time")
    private ValueWithAttribute emergencyPowerTime;

    @XmlElement(name = "detected_count")
    private int detectedCount;

    @XmlElement(name = "positive_detected_count")
    private int positiveDetectedCount;

    @XmlElement(name = "frequency")
    private ValueWithAttribute frequency;

    @XmlElement(name = "errors")
    private String errors;

    @XmlElement(name = "date")
    private String date;

}
