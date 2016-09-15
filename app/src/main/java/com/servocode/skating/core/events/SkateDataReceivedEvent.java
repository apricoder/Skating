package com.servocode.skating.core.events;

import com.servocode.skating.core.model.SkatePosition;

import lombok.Data;

@Data
public class SkateDataReceivedEvent extends SkateEvent {
    private SkatePosition skatePosition;
    public SkateDataReceivedEvent(SkatePosition skatePosition) {
        super("Received data from skateboard!");
        this.skatePosition = skatePosition;
    }
}
