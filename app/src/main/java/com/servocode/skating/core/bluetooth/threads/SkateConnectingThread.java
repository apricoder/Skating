package com.servocode.skating.core.bluetooth.threads;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Looper;

import com.servocode.skating.core.bluetooth.BluetoothSkatingConstants;
import com.servocode.skating.core.events.SkateDeviceConnectionErrorEvent;
import com.servocode.skating.core.events.SkateDeviceConnectedEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.UUID;

import lombok.AllArgsConstructor;

@AllArgsConstructor(suppressConstructorProperties = true)
public class SkateConnectingThread implements Runnable {

    private BluetoothDevice bluetoothDevice;
    private BluetoothSocket bluetoothSocket;

    @Override
    public void run() {
        try {
            UUID uuid = UUID.fromString(BluetoothSkatingConstants.STANDARD_SERIAL_PORT_SERVICE_ID);
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
            bluetoothSocket.connect();
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new SkateDeviceConnectedEvent(bluetoothSocket));
                }
            });

        } catch (IOException e) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new SkateDeviceConnectionErrorEvent());
                }
            });
        }
    }
}
