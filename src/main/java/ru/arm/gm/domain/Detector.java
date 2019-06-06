package ru.arm.gm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.arm.gm.dto.DetectorDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс-сущность "Детектор".
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "detector")
@XmlAccessorType(XmlAccessType.FIELD)
public class Detector {
    /**
     * id.
     */
    @XmlAttribute
    private Integer id;

    /**
     * ip адрес.
     */
    @XmlElement
    private String ip;

    /**
     * Адрес местоположения.
     */
    @XmlElement
    private String address;

    /**
     * Серийный номер.
     */
    @XmlElement
    private String serialNumber;

    /**
     * Дата ввода в эксплуатацию.
     */
    @XmlElement
    private LocalDate startDate;

    /**
     * Период гарантии.
     */
    @XmlElement
    private String warrantyPeriod;

    private List<DetectorData> detectorData = new ArrayList<>();

    public Detector(DetectorDTO detectorDTO) {
        this.ip = detectorDTO.getIp();
        this.address = detectorDTO.getAddress();
        this.serialNumber = detectorDTO.getSerial();
        this.startDate = LocalDate.parse(detectorDTO.getStartDate());
        this.warrantyPeriod = detectorDTO.getWarrantyPeriod().getValue();

        this.detectorData.add(new DetectorData(
                null,
                null,
                Double.parseDouble(detectorDTO.getWorkTime().getValue()),
                Double.parseDouble(detectorDTO.getAllWorkTime().getValue()),
                Double.parseDouble(detectorDTO.getEmergencyPowerTime().getValue()),
                detectorDTO.getDetectedCount(),
                detectorDTO.getPositiveDetectedCount(),
                Double.parseDouble(detectorDTO.getFrequency().getValue()),
                LocalDate.parse(detectorDTO.getDate()),
                detectorDTO.getErrors()
        ));
    }
}
