package com.servocode.skating.core.events;

import com.servocode.skating.core.model.Trick;

import lombok.Data;

@Data
public class DisplayedTrickChangedEvent extends SkateEvent{
    private Trick trick;

    public DisplayedTrickChangedEvent(Trick trick) {
        super(trick.getName());
        this.trick = trick;
    }
}
