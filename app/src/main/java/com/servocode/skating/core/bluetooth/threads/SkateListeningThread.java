package com.servocode.skating.core.bluetooth.threads;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import com.servocode.skating.core.bluetooth.BluetoothSkatingConstants;
import com.servocode.skating.core.events.SkateDataReceivedEvent;
import com.servocode.skating.core.events.SkateDeviceStopListeningEvent;
import com.servocode.skating.core.model.SkatePosition;

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
        byte[] buffer = new byte[1024];
        while (!Thread.currentThread().isInterrupted() && !stopListening) {
            try {

//                int bytes = inputStream.read(buffer);
//                String data = new String(buffer, 0, bytes);
//                Log.i("Skating", ">>>>>> Data received! " + data);

                int bytesAvailable = inputStream.available();
                if (bytesAvailable > 30) {

                    byte[] packetBytes = new byte[bytesAvailable];
                    byte[] readBuffer = new byte[1024];
                    int readBufferPosition = 0;

                    inputStream.read(packetBytes);
                    for (int i = 0; i < bytesAvailable; i++) {
                        byte nextByte = packetBytes[i];
                        if (nextByte == '\n') continue;
                        if (nextByte != '\r') readBuffer[readBufferPosition++] = nextByte;
                        else {
                            byte[] bytesRed = new byte[readBufferPosition];
                            System.arraycopy(readBuffer, 0, bytesRed, 0, bytesRed.length);
                            String data = new String(bytesRed, "US-ASCII");
                            broadcastCompleteData(data);
                            readBufferPosition = 0;
                        }
                    }
                } else SystemClock.sleep(200);
            } catch (IOException ex) {
                stopListening = true;
                Log.i("Skating", ">>>>>> Stopping worker");
            }
        }
    }

    private boolean notNewLineSymbol(byte nextByte) {
        return nextByte != BluetoothSkatingConstants.RETURN_CHAR;
    }

    private void broadcastCompleteData(final String data) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                if (isComplete(data)) {
                    //Log.i("Skating", ">>>>>> Got data " + data);
                    SkatePosition position = getSkatePositionFrom(data);
                    EventBus.getDefault().post(new SkateDataReceivedEvent(position));
                }
            }
        });
    }

    private SkatePosition getSkatePositionFrom(String data) {
        String[] dataComponents = data.split(";");
        float frontBackAngle = Float.parseFloat(dataComponents[3]);
        float leftRightAngle = Float.parseFloat(dataComponents[4]);
        float magicValue = Float.parseFloat(dataComponents[5]);
        return new SkatePosition(frontBackAngle, leftRightAngle, magicValue);
    }

    private boolean isComplete(String data) {
        return data.matches("((-?[0-9]+\\.[0-9]+;){6})(\\-?[0-9]+\\.[0-9]+)");
    }

    @Subscribe
    public void onEvent(SkateDeviceStopListeningEvent event) {
        stopListening = true;
        Log.i("Skating", ">>>>>> Stopping to listen in thread");
    }
}
