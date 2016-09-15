package com.servocode.skating.presentation.utils.seekbar;

import android.view.MotionEvent;
import android.view.View;

public class DummyOnTouchListener implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }
}
