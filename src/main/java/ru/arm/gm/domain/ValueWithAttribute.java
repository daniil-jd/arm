package ru.arm.gm.domain;

import javax.persistence.Transient;
import javax.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
public class ValueWithAttribute {

    @Transient
    @XmlAttribute(name = "unit")
    protected String unit;

    @XmlValue
    protected String value;

    public ValueWithAttribute() {
    }

    public ValueWithAttribute(String value,  String unit) {
        this.value = value;
        this.unit = unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
