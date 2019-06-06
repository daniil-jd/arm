package ru.arm.gm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarrantyDTO {
    private boolean warrantyOn;
    private String message;
    private ColorStatus color;

    public WarrantyDTO(boolean warrantyOn) {
        this.warrantyOn = warrantyOn;
    }
}
