package com.servocode.skating.presentation.utils.font;

import android.content.Context;
import android.graphics.Typeface;

import lombok.Getter;

@SuppressWarnings("SpellCheckingInspection")
public class FontCollection {
    @Getter private Typeface bigNoodleTitling;
    @Getter private Typeface bariolBold;

    public FontCollection(Context context) {
        bigNoodleTitling = Typeface.createFromAsset(context.getAssets(), "fonts/big_noodle_titling.ttf");
        bariolBold = Typeface.createFromAsset(context.getAssets(), "fonts/bariol_bold.otf");
    }
}
