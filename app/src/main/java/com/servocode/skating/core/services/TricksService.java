package com.servocode.skating.core.services;

import com.servocode.skating.core.model.Trick;

import java.util.Arrays;
import java.util.List;

public class TricksService {

    public List<Trick> getAllTricks() {
        return Arrays.asList(
                Trick.builder().name("Ollie").shortName("ollie").build(),
                Trick.builder().name("Flip").shortName("flip").build()
        );
    }
}
