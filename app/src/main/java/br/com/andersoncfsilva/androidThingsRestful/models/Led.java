package br.com.andersoncfsilva.androidThingsRestful.models;

/**
 * Created by Anderson Silva on 25/08/17.
 */


import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;

public class Led {
    private static Led instance = null;
    private PeripheralManagerService mPMSvc;
    private Gpio mBCM19;

    public static Led getInstance() {
        if (instance == null) {
            instance = new Led();
        }
        return instance;
    }

    private Led() {
        mPMSvc = new PeripheralManagerService();
        try {
            mBCM19 = mPMSvc.openGpio("BCM19");
            mBCM19.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setState(boolean state) {
        try {
            getInstance().mBCM19.setValue(state);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean getState() {
        boolean value = false;
        try {
            value = getInstance().mBCM19.getValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
}
