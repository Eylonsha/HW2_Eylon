package com.example.hw1_eylon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    private final int numOfRows =(6); //1=cars
    private final int numOfCols =3;
    private final int numOfHearts = 3;
    private final int numOfRocks = (numOfRows - 1) * numOfCols;
    private int carPostion = numOfCols / 2;
    private final int DELAY = 1000;
    private final Handler handler = new Handler();
    private Runnable timerRunnable;
    private ImageButton panel_BTN_Left;
    private ImageButton panel_BTN_Right;
    private ArrayList<ImageView> rocks;
    private ArrayList<ImageView> cars;
    private ArrayList<ImageView> hearts;
    private int maxHearts=3;
    private int heartCount=maxHearts;
    private int random;
    private boolean rowWithRock = true;



    @Override
    protected void onStart() {
        super.onStart();
        startTicker();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopTicker();
    }

    private void startTicker() {
        handler.postDelayed(timerRunnable,DELAY);
    }

    private void stopTicker() {
        handler.removeCallbacks(timerRunnable);
    }

    private void updateClockView() {
        updateView();
        rollingStones();
        checkCrash();
        fixCarView();
    }

    private void checkCrash() {
        if(rocks.get(getIndex(numOfRows-1,carPostion)).getVisibility()==View.VISIBLE){
            crushByRock();
    vibrate();


        }
    }

    private void crushByRock() {
        heartCount--;
        removeHeart();
        checkHearts();
    }

    private void removeHeart() {
        Toast.makeText(MainActivity.this, "שברת לי ת'לב", Toast.LENGTH_SHORT).show();
        if(heartCount>=0){
            hearts.get(maxHearts-heartCount-1).setVisibility(View.INVISIBLE);

        }
    }

    private void checkHearts() {
        if(heartCount==0){
            gameOver();

        }
    }

    private void gameOver() {
        heartCount=maxHearts;
        for (int i = 0; i < maxHearts; i++) {
            hearts.get(i).setVisibility(View.VISIBLE);

        }
    }

    private void fixCarView() {

        cars.get(carPostion).setVisibility(View.VISIBLE);
        cars.get(carPostion).setImageResource(R.drawable.car);
    }

    private void updateView() {
        for (int i =numOfRows-1; i >0 ; i--) {
            for (int j = 0; j <numOfCols ; j++) {
                rocks.get(getIndex(i, j)).setVisibility(rocks.get(getIndex(i - 1, j)).getVisibility());
                rocks.get(getIndex(i, j)).setImageDrawable(rocks.get(getIndex(i - 1, j)).getDrawable());
            }
        }

    }

    private int getIndex(int i, int j) {
        return i*numOfCols+j;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vibrate();
        findViews();
        gameLogic gl = new gameLogic();
        gl.setPanel_BTN_Left(panel_BTN_Left).setPanel_BTN_Right(panel_BTN_Right).setRocks(rocks).setHearts(hearts).setCars(cars);
        timerRunnable = () -> {
            updateClockView();
            handler.postDelayed(timerRunnable, DELAY);
        };
        cars = createCars();
        rocks = createRocks();
        hearts =createHearts();

        //Left Bottom
        gl.getPanel_BTN_Left().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            vibrate();
                leftClick();
            }
        });
        //Right Bottom
        gl.getPanel_BTN_Right().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                vibrate();
                rightClick();
            }
        });



    }

    private void rollingStones() {
        clearFirstRow();
        if(rowWithRock){
            random= new Random().nextInt(numOfCols);
            rocks.get(random).setVisibility(View.VISIBLE);
        }
        rowWithRock = !rowWithRock;

    }

    private void clearFirstRow() {
        for (int i = 0; i <numOfCols ; i++) {
            rocks.get(i).setVisibility(View.INVISIBLE);
        }
    }

    private ArrayList<ImageView> createHearts() {
        hearts = new ArrayList<ImageView>();
        for (int i = 0; i < numOfHearts; i++) {
            hearts.add(findViewById(getResources().getIdentifier("imageViewHeart" + i, "id", getPackageName())));
        }
        return hearts;
    }

    private ArrayList<ImageView> createCars() {
        cars = new ArrayList<ImageView>();
        for (int i = 0; i < numOfCols; i++) {
            cars.add(findViewById(getResources().getIdentifier("car" + i, "id", getPackageName())));
        }
        return cars;
    }


    private ArrayList<ImageView> createRocks() {
        rocks = new ArrayList<ImageView>();
        for (int i = 0; i < numOfRocks; i++) {
            rocks.add(findViewById(getResources().getIdentifier("imageView_Rock_" + i, "id", getPackageName())));
        }
        for (int i = 0; i < numOfCols; i++) {
            rocks.add(findViewById(getResources().getIdentifier("car" + i, "id", getPackageName())));
        }
        return rocks;
    }


    private void vibrate(){
        Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            vib.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
        else
            vib.vibrate(100);  //deprecated in API 26
    }
    //####################################################################
//On Click
    public void leftClick() {
        if(carPostion < 1 ){
            return;
        }
        cars.get(carPostion).setVisibility(View.INVISIBLE);
        carPostion--;
        checkCrash();
        fixCarView();
    }

    public void rightClick() {
        if(carPostion < numOfCols-1){
            cars.get(carPostion).setVisibility(View.INVISIBLE);
            carPostion++;
            checkCrash();
            fixCarView();
        }
    }

    private void findViews() {
            panel_BTN_Left = findViewById(R.id.panel_BTN_Left);
            panel_BTN_Right = findViewById(R.id.panel_BTN_Right);

        }


    }



