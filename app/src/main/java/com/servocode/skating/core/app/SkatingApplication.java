package com.servocode.skating.core.app;

import android.app.Application;

import com.servocode.skating.core.bluetooth.BluetoothSkatingService;
import com.servocode.skating.core.resources.ResourceIdService;
import com.servocode.skating.core.resources.TrickImageIdService;
import com.servocode.skating.core.services.TricksService;
import com.servocode.skating.presentation.activities.MainActivity;
import com.servocode.skating.presentation.activities.ShowTricksActivity;
import com.servocode.skating.presentation.fragments.TrickFragment;
import com.servocode.skating.presentation.utils.animation.ActivityNavigator;
import com.servocode.skating.presentation.utils.font.FontCollection;

import lombok.Getter;

public class SkatingApplication extends Application {

    @Getter
    private static SkatingApplication instance = new SkatingApplication();

    private boolean initializedDependencies = false;
    private FontCollection fontCollection;
    private BluetoothSkatingService bluetoothSkatingService;
    private ActivityNavigator activityNavigator;
    private ResourceIdService resourceIdService;
    private TrickImageIdService trickImageIdService;

    public SkatingApplication() {
        instance = this;
    }

    private void initializedDependenciesIfNeeded() {
        if (initializedDependencies) return;
        fontCollection = new FontCollection(this);
        bluetoothSkatingService = new BluetoothSkatingService(this);
        activityNavigator = new ActivityNavigator();
        resourceIdService = new ResourceIdService(this);
        trickImageIdService = new TrickImageIdService();
        initializedDependencies = true;
    }

    public void inject(MainActivity mainActivity) {
        initializedDependenciesIfNeeded();
        mainActivity.fontCollection = fontCollection;
        mainActivity.navigator = activityNavigator;
        mainActivity.bluetoothSkatingService = bluetoothSkatingService;
    }

    public void inject(ShowTricksActivity showTricksActivity) {
        initializedDependenciesIfNeeded();
        showTricksActivity.tricksService = new TricksService();
        showTricksActivity.fontCollection = fontCollection;
        showTricksActivity.navigator = activityNavigator;
        showTricksActivity.trickImageIdService = trickImageIdService;
    }

    public void inject(TrickFragment trickFragment) {
        trickFragment.trickImageIdService = trickImageIdService;
    }

    public void inject(TrickImageIdService trickImageIdService) {
        trickImageIdService.resourceIdService = resourceIdService;
    }
}
