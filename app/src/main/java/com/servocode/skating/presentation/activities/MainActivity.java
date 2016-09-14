package com.servocode.skating.presentation.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.servocode.skating.R;
import com.servocode.skating.core.app.SkatingApplication;
import com.servocode.skating.core.bluetooth.BluetoothSkatingService;
import com.servocode.skating.core.events.NoBluetoothAdapterAvailableEvent;
import com.servocode.skating.core.events.SkateDeviceConnectedEvent;
import com.servocode.skating.core.events.SkateDeviceConnectionErrorEvent;
import com.servocode.skating.core.events.SkateDeviceStopListeningEvent;
import com.servocode.skating.presentation.utils.animation.ActivityNavigator;
import com.servocode.skating.presentation.utils.font.FontCollection;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

@SuppressWarnings("ConstantConditions")
public class MainActivity extends AppCompatActivity {

    public FontCollection fontCollection;
    public BluetoothSkatingService bluetoothSkatingService;
    public ActivityNavigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        injectDependencies();
        initLearnTricksButton();
        attachFonts();
        registerOnEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bluetoothSkatingService.runMagic();
    }

    private void injectDependencies() {
        ((SkatingApplication)getApplication()).inject(this);
    }

    private void initLearnTricksButton() {
        findViewById(R.id.learn_tricks_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.goForwardWithSlideAnimation(MainActivity.this, ShowTricksActivity.class);
            }
        });
    }

    private void attachFonts() {
        ((TextView)findViewById(R.id.toolbar_title)).setTypeface(fontCollection.getBigNoodleTitling());
        ((TextView)findViewById(R.id.speed_label)).setTypeface(fontCollection.getBigNoodleTitling());
        ((TextView)findViewById(R.id.speed_indicator)).setTypeface(fontCollection.getBigNoodleTitling());
        ((TextView)findViewById(R.id.tilted_text)).setTypeface(fontCollection.getBigNoodleTitling());
        ((Button)findViewById(R.id.learn_tricks_button)).setTypeface(fontCollection.getBigNoodleTitling());
        ((Button)findViewById(R.id.learn_tricks_button)).setTypeface(fontCollection.getBigNoodleTitling());
    }

    private void registerOnEvents() {
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onEvent(NoBluetoothAdapterAvailableEvent event){
        showMessage(event.getMessage());
    }

    @Subscribe
    public void onEvent(SkateDeviceConnectionErrorEvent event){
        Log.i("Skating", ">>>>>> Connection error from Activity!");
        showMessage(event.getMessage());
    }

    @Subscribe
    public void onEvent(SkateDeviceConnectedEvent event){
        Log.i("Skating", ">>>>>> Connected!");
        showMessage(event.getMessage());
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().post(new SkateDeviceStopListeningEvent());
    }

    private void showMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 240);
        toast.show();
    }
}
