package com.servocode.skating.core.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Trick implements Serializable{
    private String name;
    private String shortName;
}
