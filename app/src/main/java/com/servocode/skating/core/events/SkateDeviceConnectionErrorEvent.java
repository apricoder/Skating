package com.servocode.skating.core.events;

public class SkateDeviceConnectionErrorEvent extends SkateEvent {
    public SkateDeviceConnectionErrorEvent() {
        super("Couldn't connect to skate!");
    }
}
