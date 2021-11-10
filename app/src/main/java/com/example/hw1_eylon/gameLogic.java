package com.example.hw1_eylon;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

public class gameLogic {

    private ImageButton panel_BTN_Left;
    private ImageButton panel_BTN_Right;
    private ArrayList<ImageView> rocks;
    private ArrayList<ImageView> cars;
    private ArrayList<ImageView> hearts;

   // private int heartCount;

    public gameLogic() {

    }
    //Get & Set Cars

    public ArrayList<ImageView> getCars(){

        return cars;
    }
    public gameLogic setCars(ArrayList<ImageView> cars) {
        this.cars = cars;
        return this;
    }

//Get & Set Hearts

    public ArrayList<ImageView> getHearts(){

        return hearts;
    }
    public gameLogic setHearts(ArrayList<ImageView> hearts) {
        this.hearts = hearts;
        return this;
    }


    //####################################################################
    //Get & Set Rocks
        public ArrayList<ImageView> getRocks(){

        return rocks;
    }
    public gameLogic setRocks(ArrayList<ImageView> rocks) {
        this.rocks = rocks;
        return this;
    }
    //####################################################################
    //Get & Set BTNs
    public ImageButton getPanel_BTN_Left() {
        return panel_BTN_Left;
    }

    public gameLogic setPanel_BTN_Left(ImageButton panel_BTN_Left) {
        this.panel_BTN_Left = panel_BTN_Left;
        return this;
    }

    public ImageButton getPanel_BTN_Right() {
        return panel_BTN_Right;
    }

    public gameLogic setPanel_BTN_Right(ImageButton panel_BTN_Right) {
        this.panel_BTN_Right = panel_BTN_Right;
        return this;
    }
    //####################################################################
//On Click

}
