package com.servocode.skating.testutils;

import android.support.annotation.NonNull;

import com.servocode.skating.tricks.Tricks;
import com.servocode.skating.common.SkatingApplication;

public class TestApplication extends SkatingApplication {
    private Tricks tricks;

    @NonNull
    @Override
    protected Tricks getTricks() {
        if (tricks == null)
            return super.getTricks();
        return tricks;
    }

    public void setTricks(Tricks tricks) {
        this.tricks = tricks;
    }

}
