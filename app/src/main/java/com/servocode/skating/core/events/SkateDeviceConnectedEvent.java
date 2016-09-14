package com.servocode.skating.core.events;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SkateDeviceConnectedEvent extends SkateEvent {

    private Object data;

    public SkateDeviceConnectedEvent() {
        super("Skate device connected!");
    }

    public SkateDeviceConnectedEvent(Object data) {
        this();
        this.data = data;
    }
}
