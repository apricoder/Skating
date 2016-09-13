package com.servocode.skating.tricks;

import java.util.ArrayList;
import java.util.List;

public class HardcodedTricks implements Tricks {
    @Override
    public List<Trick> getAll() {
        ArrayList<Trick> tricks = new ArrayList<>();
        tricks.add(new Trick("Ollie", "ollie"));
        tricks.add(new Trick("Flip", "flip"));
        return tricks;
    }
}
