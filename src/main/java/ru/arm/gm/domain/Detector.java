package ru.arm.gm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.Date;

/**
 * Класс-сущность "Детектор".
 */
//@Entity
//@Table(name = "DETECTOR")
@XmlRootElement(name = "detector")
@XmlAccessorType(XmlAccessType.NONE)
public class Detector {
    /**
     * id.
     */
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlAttribute
    private Integer id;

    /**
     * ip адрес.
     */
//    @Column(name = "IP")
    @XmlElement
    private String ip;

    /**
     * Адрес местоположения.
     */
//    @Column(name = "ADDRESS")
    @XmlElement
    private String address;

    /**
     * Серийный номер.
     */
//    @Column(name = "SERIAL_NUMBER")
    @XmlElement
    private String serial;

    /**
     * Дата ввода в эксплуатацию.
     */
//    @Column(name = "START_DATE")
    @XmlElement
    private LocalDate startDate;

    /**
     * Период гарантии.
     */
//    @Column(name = "WARRANTY_PERIOD")
    @XmlElement
    private String warrantyPeriod;

    /**
     * Время непрерывной работы.
     */
//    @Column(name = "WORK_TIME")
    @XmlElement
    private String workTime;

    /**
     * Общее время работы.
     */
//    @Column(name = "ALL_WORK_TIME")
    @XmlElement
    private String allWorkTime;

    /**
     * Время работы на аварийном питании.
     */
//    @Column(name = "EMERGENCY_POWER_TIME")
    @XmlElement
    private String emergencyPowerTime;

    /**
     * Количество проходов.
     */
//    @Column(name = "DETECTED_COUNT")
    @XmlElement
    private long detectedCount;

    /**
     * Количество срабатываний.
     */
//    @Column(name = "POSITIVE_DETECTED_COUNT")
    @XmlElement
    private long positiveDetectedCount;

    /**
     * Рабочая частота.
     */
//    @Column(name = "FREQUENCY")
    @XmlElement
    private double frequency;

    /**
     * Дата обращения.
     */
//    @Column(name = "DATE")
    @XmlElement
    private LocalDate date;

    /**
     * Обшибки в процессе работы.
     */
//    @Column(name = "ERRORS")
    @XmlElement
    private String errors;

    public Detector() {
    }

    public Detector(String ip, String address,
                    String serial, LocalDate startDate,
                    String warrantyPeriod, String workTime,
                    String allWorkTime, String emergencyPowerTime,
                    long detectedCount, long positiveDetectedCount,
                    double frequency, LocalDate date, String errors) {
        this.ip = ip;
        this.address = address;
        this.serial = serial;
        this.startDate = startDate;
        this.warrantyPeriod = warrantyPeriod;
        this.workTime = workTime;
        this.allWorkTime = allWorkTime;
        this.emergencyPowerTime = emergencyPowerTime;
        this.detectedCount = detectedCount;
        this.positiveDetectedCount = positiveDetectedCount;
        this.frequency = frequency;
        this.date = date;
        this.errors = errors;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getAllWorkTime() {
        return allWorkTime;
    }

    public void setAllWorkTime(String allWorkTime) {
        this.allWorkTime = allWorkTime;
    }

    public String getEmergencyPowerTime() {
        return emergencyPowerTime;
    }

    public void setEmergencyPowerTime(String emergencyPowerTime) {
        this.emergencyPowerTime = emergencyPowerTime;
    }

    public long getDetectedCount() {
        return detectedCount;
    }

    public void setDetectedCount(long detectedCount) {
        this.detectedCount = detectedCount;
    }

    public long getPositiveDetectedCount() {
        return positiveDetectedCount;
    }

    public void setPositiveDetectedCount(long positiveDetectedCount) {
        this.positiveDetectedCount = positiveDetectedCount;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }
}
