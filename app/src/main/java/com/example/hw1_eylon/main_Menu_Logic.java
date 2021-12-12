package com.example.hw1_eylon;

import android.widget.ImageButton;

public class main_Menu_Logic {
    private ImageButton panel_BTN_Start;
    private ImageButton panel_BTN_Records;

    public main_Menu_Logic() {

    }

    public ImageButton getPanel_BTN_Start() {

        return panel_BTN_Start;
    }

    public main_Menu_Logic setPanel_BTN_Start(ImageButton panel_BTN_Start) {
        this.panel_BTN_Start = panel_BTN_Start;
        return this;
    }

    public ImageButton getPanel_BTN_Records() {
        return panel_BTN_Records;
    }

    public main_Menu_Logic setPanel_BTN_Records(ImageButton panel_BTN_Records) {
        this.panel_BTN_Records = panel_BTN_Records;
        return this;
    }
}


