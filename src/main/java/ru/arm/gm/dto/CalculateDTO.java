package ru.arm.gm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculateDTO {
    private WarrantyDTO warranty;
    private double relativeWorkTime;
    private double relativeEmergencyWorkTime;
    private double relativePositiveCount;
    private double relativeErrorCountSpeed;
    private double detectedCountSpeed;
    private double positiveDetectedCountSpeed;
    private DetectorDTO chartData;
}
