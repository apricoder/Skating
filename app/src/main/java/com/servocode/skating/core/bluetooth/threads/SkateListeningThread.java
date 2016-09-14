package com.servocode.skating.core.bluetooth.threads;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.servocode.skating.core.bluetooth.BluetoothSkatingConstants;
import com.servocode.skating.core.events.SkateDataReceivedEvent;
import com.servocode.skating.core.events.SkateDeviceStopListeningEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.io.InputStream;

public class SkateListeningThread implements Runnable {
    private InputStream inputStream;
    private boolean stopListening = false;

    public SkateListeningThread(InputStream inputStream) {
        this.inputStream = inputStream;
        EventBus.getDefault().register(this);
    }

    @Override
    public void run() {
        Log.i("Skating", ">>>>>> Started reading data");
        while (!Thread.currentThread().isInterrupted() && !stopListening) {
            try {
                int bytesAvailable = inputStream.available();
                Log.i("Skating", ">>>>>> Available bytes: " + bytesAvailable);
                if (bytesAvailable > 0) {

                    byte[] packetBytes = new byte[bytesAvailable];
                    byte[] readBuffer = new byte[1024];
                    int readBufferPosition = 0;

                    inputStream.read(packetBytes);
                    for (int i = 0; i < bytesAvailable; i++) {
                        byte nextByte = packetBytes[i];
                        if (notNewLineSymbol(nextByte)) readBuffer[readBufferPosition++] = nextByte;
                        else {
                            byte[] bytesRed = new byte[readBufferPosition];
                            System.arraycopy(readBuffer, 0, bytesRed, 0, bytesRed.length);
                            Log.i("Skating", ">>>>>> BT is enabled");
                            broadcastData(new String(bytesRed, "US-ASCII"));
                            readBufferPosition = 0;
                        }
                    }
                }
            } catch (IOException ex) {
                stopListening = true;
                Log.i("Skating", ">>>>>> Stopping worker");
            }
        }
    }

    private boolean notNewLineSymbol(byte nextByte) {
        return nextByte != BluetoothSkatingConstants.NEW_LINE_CHAR;
    }

    private void broadcastData(final String data) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                Log.i("Skating", ">>>>>> Received data");
                Log.i("Skating", data);
                EventBus.getDefault().post(new SkateDataReceivedEvent(data));
            }
        });
    }

    @Subscribe
    public void onEvent(SkateDeviceStopListeningEvent event) {
        stopListening = true;
        Log.i("Skating", ">>>>>> Stopping to listen in thread");
    }
}
