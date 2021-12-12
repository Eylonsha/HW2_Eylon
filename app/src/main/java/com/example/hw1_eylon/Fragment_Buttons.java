package com.example.hw1_eylon;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;



public class Fragment_Buttons extends Fragment {

    private ImageButton panel_BTN_Left;
    private ImageButton panel_BTN_Right;
    private CallBack_Control callBack_control;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buttons, container, false);
        findViews(view);
        initViews();
        return view;
    }

    public void setCallBack_control(CallBack_Control callBack_control){
        this.callBack_control = callBack_control;
    }

    private void initViews() {
        panel_BTN_Left.setOnClickListener(v -> callBack_control.direction("Left"));
        panel_BTN_Right.setOnClickListener(v -> callBack_control.direction("Right"));

    }

    private void findViews(View view) {
        panel_BTN_Left = view.findViewById(R.id.panel_BTN_Left);
        panel_BTN_Right = view.findViewById(R.id.panel_BTN_Right);
    }
}
