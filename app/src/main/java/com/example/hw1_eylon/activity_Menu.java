package com.example.hw1_eylon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class activity_Menu extends AppCompatActivity {
    public static final String BUNDLE_KEY ="BUNDLE_KEY";
    private ImageButton panel_BTN_Start;
    private ImageButton panel_BTN_Records;
    private SwitchMaterial sensor_Switch;
    private SwitchMaterial speed_Switch;
    private int switchOn = 0;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        bundle = new Bundle();
        grantLocationPermission();

        findViews();
        initViews();



    }

    private void initViews() {
        main_Menu_Logic mml = new main_Menu_Logic();
        mml.setPanel_BTN_Start(panel_BTN_Start).setPanel_BTN_Records(panel_BTN_Records);
        mml.getPanel_BTN_Start().setOnClickListener(v -> startGame());
        panel_BTN_Records.setOnClickListener(v -> showRecords());

    }

    private void findViews() {
        panel_BTN_Records = findViewById(R.id.panel_BTN_Records);
        panel_BTN_Start = findViewById(R.id.panel_BTN_Start);
        sensor_Switch = findViewById(R.id.sensor_Switch);
        speed_Switch = findViewById(R.id.speed_Switch);
    }

    private void startGame() {
        Intent intent = new Intent(this, MainActivity.class);

        if(sensor_Switch.isChecked()){
            bundle.putString(MainActivity.CONTROL_KEY,MainActivity.CONTROL_VALUE_ACC);
        }else{
            bundle.putString(MainActivity.CONTROL_KEY,MainActivity.CONTROL_VALUE_BUTTONS);
        }

        if(speed_Switch.isChecked()){
            bundle.putString(MainActivity.SPEED_KEY,MainActivity.SPEED_VALUE_FAST);
        }else{
            bundle.putString(MainActivity.SPEED_KEY,MainActivity.SPEED_VALUE_SLOW);
        }


        intent.putExtra(BUNDLE_KEY, bundle);
        startActivity(intent);
    }
    private void showRecords() {
        Intent intent = new Intent(this, record_Activity.class);
        intent.putExtra(BUNDLE_KEY, bundle);
        startActivity(intent);

    }

    private void grantLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}  , 44);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 44);
    }


}
