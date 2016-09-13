package com.servocode.skating.common;

import android.app.Application;
import android.support.annotation.NonNull;

import com.servocode.skating.activities.ShowTricksActivity;
import com.servocode.skating.tricks.Tricks;
import com.servocode.skating.tricks.HardcodedTricks;

public class SkatingApplication extends Application {
    public void inject(ShowTricksActivity showTricksActivity) {
        showTricksActivity.tricks = getTricks();
    }

    @NonNull
    protected Tricks getTricks() {
        return new HardcodedTricks();
    }
}
