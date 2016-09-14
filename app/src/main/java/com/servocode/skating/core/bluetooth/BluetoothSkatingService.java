package com.servocode.skating.core.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.servocode.skating.core.app.SkatingApplication;
import com.servocode.skating.core.bluetooth.exceptions.BluetoothIsDisabledException;
import com.servocode.skating.core.bluetooth.exceptions.NoBluetoothAdapterException;
import com.servocode.skating.core.bluetooth.threads.SkateConnectingThread;
import com.servocode.skating.core.bluetooth.threads.SkateListeningThread;
import com.servocode.skating.core.events.NoBluetoothAdapterAvailableEvent;
import com.servocode.skating.core.events.SkateDeviceConnectedEvent;
import com.servocode.skating.core.events.SkateDeviceStopListeningEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class BluetoothSkatingService {

    private SkatingApplication application;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice skate;
    private BluetoothSocket skateDataSocket = null;
    private InputStream inputStream;

    public BluetoothSkatingService(SkatingApplication application) {
        EventBus.getDefault().register(this);
        this.application = application;
    }


    public void runMagic() {
        try {
            findSkateInPairedDevices();
            connectToSkate();
        } catch (NoBluetoothAdapterException e) {
            EventBus.getDefault().post(new NoBluetoothAdapterAvailableEvent());
            Log.i("Skating", ">>>>>> No BT exception");
        } catch (BluetoothIsDisabledException e) {
            Log.i("Skating", ">>>>>> BT disabled exception");
        } catch (IOException ignored) {
            Log.i("Skating", ">>>>>> IO Exception");
        }
    }

    private void findSkateInPairedDevices() throws NoBluetoothAdapterException, BluetoothIsDisabledException {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null)
            throw new NoBluetoothAdapterException("No Bluetooth adapter");
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            enableBluetooth.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            application.startActivity(enableBluetooth);
            throw new BluetoothIsDisabledException("Bluetooth is disabled");
        } else {
            Log.i("Skating", ">>>>>> BT is enabled");
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    if (device.getName().equals(BluetoothSkatingConstants.SKATEBOARD_BLUETOOTH_DEVICE_NAME)) {
                        skate = device;
                        break;
                    }
                }
            }
        }
    }

    private void connectToSkate() throws IOException {
        AsyncTask.execute(new SkateConnectingThread(skate, skateDataSocket));
    }

    @Subscribe
    public void onEvent(SkateDeviceConnectedEvent event) throws IOException {
        Log.i("Skating", ">>>>>> Connected inside BluetoothSkatingService");
        skateDataSocket = (BluetoothSocket) event.getData();
        inputStream = skateDataSocket.getInputStream();
        AsyncTask.execute(new SkateListeningThread(inputStream));
    }

    @Subscribe
    public void onEvent(SkateDeviceStopListeningEvent event) throws IOException {
        Log.i("Skating", ">>>>>> Closing connection from service");
        closeConnection();
    }

    private void closeConnection() throws IOException{
        Log.i("Skating", ">>>>>> BT closed");
        try { skateDataSocket.close(); inputStream.close();}
        catch (NullPointerException ignored) { }
    }
}
