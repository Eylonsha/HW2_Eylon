package com.example.hw1_eylon;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;


public class record_Activity extends AppCompatActivity {

    private Bundle bundle;
    public static final String RECORD_MANAGER_KEY ="RECORD_MANAGER_KEY";
    public static final String PLAYER_SCORE_KEY ="PLAYER_SCORE_KEY";

    private Fragment_Records fragmentRecords;
    private Fragment_Map fragmentMap;
    private Record_Manager record_manager;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_menu);

        this.bundle = getIntent().getBundleExtra(activity_Menu.BUNDLE_KEY);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        String json = MSPV3.getMe().getString(RECORD_MANAGER_KEY, new Gson().toJson(new Record_Manager()));
        record_manager = new Gson().fromJson(json, Record_Manager.class);
        getLatLon((lat, lon) -> {
            updateRecords(lat,lon);
            initFragments();

            MSPV3.getMe().putString(record_Activity.RECORD_MANAGER_KEY, new Gson().toJson(record_manager));
        });

    }

    private void updateRecords(double lat, double lon) {
        int currentScore=bundle.getInt(PLAYER_SCORE_KEY,-1);
        if (currentScore == -1){
            return;
        }
        Records currentRecord = new Records();
        String time = new Date().toString();

        currentRecord.setTime(time).setCoins(currentScore).setLatitude(lat).setLongitude(lon);
        record_manager.updateRecords(currentRecord);

    }

    private void initFragments() {
        fragmentRecords = new Fragment_Records();
        fragmentRecords.setActivity(this);
        fragmentRecords.setCallBackList(callBackRecords);
        fragmentRecords.setRecords(record_manager.getRecords());
        getSupportFragmentManager().beginTransaction().add(R.id.ScoreBoardFrame, fragmentRecords).commit();

        fragmentMap =new  Fragment_Map();
        fragmentMap.setActivity(this);
        getSupportFragmentManager().beginTransaction().add(R.id.mapFrame, fragmentMap).commit();
    }

    CallBack_Records callBackRecords = new CallBack_Records() {

        @Override
        public void findChosenPlayer(int index) {
            Records record = record_manager.getRecords().get(index);
            fragmentMap.setLocation(record.getTime(),record.getCoins()  + "",record.getLatitude(),record.getLongitude());
        }
    };


    public void getLatLon(CallBack_LatLon cb){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnCompleteListener( task->{
                Location location = task.getResult();
                if(location!=null){
                    cb.getLatLon(location.getLatitude(), location.getLongitude());
                }
                else{
                    cb.getLatLon(0.0, 0.0);
                }
            });
        }
        else {
            cb.getLatLon(0.0, 0.0);
        }
    }
}