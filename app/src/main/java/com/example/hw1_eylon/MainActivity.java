package com.example.hw1_eylon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private Bundle bundle;
    public final static String CONTROL_KEY = "CONTROL_KEY";
    public final static String CONTROL_VALUE_BUTTONS = "CONTROL_VALUE_BUTTONS";
    public final static String CONTROL_VALUE_ACC = "CONTROL_VALUE_ACC";
    public final static String SPEED_KEY = "SPEED_KEY";
    public final static String SPEED_VALUE_SLOW = "SPEED_VALUE_SLOW";
    public final static String SPEED_VALUE_FAST = "SPEED_VALUE_FAST";



    public final static String COIN_STRING = "COIN_STRING";
    public final static String ROCK_STRING = "ROCK_STRING";
    private final Random rand = new Random();
    public final static int StoneRotation = 5;
    public final static int STONE_WEIGHT = 5;
    public final static int COIN_WEIGHT = 2;
    private final int numOfRows = (5 + 1); //1=cars
    private final int numOfCols = 5;
    private final int numOfHearts = 3;
    private final int numOfRocks = (numOfRows - 1) * numOfCols;
    private int carPostion = numOfCols / 2;
    private int DELAY = 1000;
    private final Handler handler = new Handler();
    private Runnable timerRunnable;
    private MaterialTextView Game_distance;
    private MaterialTextView Game_score;
    private ArrayList<ImageView> rocks;
    private ArrayList<ImageView> cars;
    private ArrayList<ImageView> hearts;
    private ArrayList<ImageView> coins;
    private MediaPlayer[] coinSounds;
    private MediaPlayer crushSound;
    private MediaPlayer coinSound;
    private MediaPlayer coin2Sound;
    private int maxHearts = 3;
    private int heartCount = maxHearts;
//    private int random;
    private boolean rowWithRock = true;
    private int score = 0;
    private int distance=0;


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
        handler.postDelayed(timerRunnable, DELAY);
    }

    private void stopTicker() {
        handler.removeCallbacks(timerRunnable);
    }

    private void updateClockView() {
        distance+=10;
        Game_distance.setText("Distance: " + distance + "M");
        updateView();
        rollingStones();
        checkCrash();
        fixCarView();
    }

    private void checkCrash() {
        ImageView obj = rocks.get(getIndex(numOfRows - 1, carPostion));
        if (obj.getVisibility() == View.VISIBLE) {
            if (obj.getTag().equals(ROCK_STRING)) {

                crushByRock();

            } else {
                updateScore();

                coinSounds[rand.nextInt(coinSounds.length)].start();
            }
            vibrate();
        }else
        fixCarView();

        }



    private void raiseScore() {
        score+=100;
    }

    private void crushByRock() {
        crushSound.start();
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
        Intent intent = new Intent(this, record_Activity.class);
        bundle.putInt(record_Activity.PLAYER_SCORE_KEY,score);
        intent.putExtra(activity_Menu.BUNDLE_KEY, bundle);
        startActivity(intent);
        finish();
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
                rocks.get(getIndex(i, j)).setRotation(rocks.get(getIndex(i - 1, j)).getRotation());
                rocks.get(getIndex(i, j)).setTag(rocks.get(getIndex(i - 1, j)).getTag());
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

        this.bundle = getIntent().getBundleExtra(activity_Menu.BUNDLE_KEY);

        vibrate();
        gameLogic gl = new gameLogic();
        Game_score=findViewById(R.id.Game_score);
        crushSound = MediaPlayer.create(MainActivity.this, R.raw.crash);
        coinSound = MediaPlayer.create(MainActivity.this, R.raw.coin);
        coin2Sound = MediaPlayer.create(MainActivity.this, R.raw.coin2);
        coinSounds = new MediaPlayer[]{coinSound,coin2Sound};

        Game_distance=findViewById(R.id.Game_distance);

        score=0;
        distance=0;
        cars = createCars();
        rocks = createRocks();
        hearts =createHearts();
        coins = createCoins();

        initSettings();

        timerRunnable = () -> {
            updateClockView();
            handler.postDelayed(timerRunnable, DELAY);
        };


    }

    private void moveCar(String direction){
        vibrate();
        if(direction.equalsIgnoreCase("Left")){
            leftClick();
        }else{
            rightClick();
        }
    }

    private void initSettings() {
        if (bundle.getString(CONTROL_KEY,CONTROL_VALUE_BUTTONS).equals(CONTROL_VALUE_BUTTONS)){
            displayButtons();
        }else {
            displayAcc();
        }
        if (bundle.getString(SPEED_KEY,SPEED_VALUE_SLOW).equals(SPEED_VALUE_FAST)){
            DELAY/=2;
        }

    }

    private void displayButtons() {
        Fragment_Buttons fragmentButtons = new Fragment_Buttons();
        fragmentButtons.setCallBack_control(direction -> moveCar(direction));
        getSupportFragmentManager().beginTransaction().add(R.id.ArrowsLayout, fragmentButtons).commit();

    }

    private void displayAcc() {
        Fragment_ACC fragmentACC = new Fragment_ACC();
        fragmentACC.setCallBack_control(direction -> moveCar(direction));
        fragmentACC.setActivity(this);
        getSupportFragmentManager().beginTransaction().add(R.id.ArrowsLayout, fragmentACC).commit();
    }


    private ArrayList<ImageView> createCoins() {

            coins = new ArrayList<ImageView>();
            for (int i = 0; i < numOfRocks; i++) {
                coins.add(findViewById(getResources().getIdentifier("linearLayoutCoin" + i, "id", getPackageName())));
            }
        for (int i = 0; i < numOfCols; i++) {
            coins.add(findViewById(getResources().getIdentifier("car" + i, "id", getPackageName())));
        }
            return coins;
        }


    private void rollingStones() {
        clearFirstRow();
        if(rowWithRock){
//            random= new Random().nextInt(numOfCols);
////            rocks.get(random).setVisibility(View.VISIBLE);
////            coins.get(random).setVisibility(View.VISIBLE);
            randomFirstRow();
        }
        rowWithRock = !rowWithRock;

    }

    private void randomFirstRow() {
        int col = rand.nextInt(numOfCols);
        int obj = rand.nextInt(STONE_WEIGHT + COIN_WEIGHT);

        if (obj < STONE_WEIGHT) {
            rocks.get(getIndex(0, col)).setImageResource(R.drawable.rock);
            //rocks.get(getIndex(0, col)).setRotation(StoneRotation);
            //rocks.get(getIndex(0, col)).setImageDrawable(rocks.get(getIndex(0, col)).getDrawable());
            rocks.get(getIndex(0, col)).setTag(ROCK_STRING);
        }else{
            rocks.get(getIndex(0, col)).setImageResource(R.drawable.coin);
           // rocks.get(getIndex(0, col)).setRotation(0);
            rocks.get(getIndex(0, col)).setTag(COIN_STRING);

        }
        rocks.get(getIndex(0, col)).setVisibility(View.VISIBLE);
    }

    private void clearFirstRow() {
        for (int i = 0; i <numOfCols ; i++) {
            rocks.get(i).setVisibility(View.INVISIBLE);
            coins.get(i).setVisibility(View.INVISIBLE);
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


    private void updateScore() {
        score += 10;
        //coinSound.start();

        Game_score.setText("Coins" + ": " + score + "$");
    }
    }



