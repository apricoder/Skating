package com.servocode.skating.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(suppressConstructorProperties = true)
public class SkatePosition {
    private float frontBackTint;
    private float leftRightTint;
    private float magicValue;
}
