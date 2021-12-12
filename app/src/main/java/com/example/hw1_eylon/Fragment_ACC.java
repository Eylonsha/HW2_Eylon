package com.example.hw1_eylon;


import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


public class Fragment_ACC extends Fragment {


    private CallBack_Control callBack_control;
    public static final String SENSOR_TYPE = "SENSOR_TYPE";


    private SensorManager sensorManager;
    private Sensor accSensor;
    private Activity activity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acc, container, false);
        initSensor();
        return view;
    }

    public void setCallBack_control(CallBack_Control callBack_control){
        this.callBack_control = callBack_control;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    private void initSensor() {
        sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private SensorEventListener accSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];

            if ( x < -9 ){
                callBack_control.direction("Right");
            } else if ( x > 9 ) {
                callBack_control.direction("Left");
            }


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(accSensorEventListener, accSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(accSensorEventListener);
    }

    public boolean isSensorExist(int sensorType) {
        return (sensorManager.getDefaultSensor(sensorType) != null);
    }
}