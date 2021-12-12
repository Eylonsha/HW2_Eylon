package com.example.hw1_eylon;



import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class Fragment_Records extends Fragment {

    private final int ROWS = 10;
    private CallBack_Records callBackRecords;
    private ArrayList<Records> records;
    private final LinearLayout[] Records_ROW_rows = new LinearLayout[ROWS] ;
    private final TextView[] Records_TXT_Times = new TextView    [ROWS] ;
    private final TextView    [] Records_TXT_scores = new TextView    [ROWS] ;
    private Activity activity;


    public void setCallBackList(CallBack_Records callBackRecords) {
        this.callBackRecords = callBackRecords;
    }

    public void setRecords(ArrayList<Records> records) {
        this.records = records;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_records, container, false);
        findViews(view);
        initViews();
        updateRows();
        return view;
    }

    public void updateRows() {
        int i = 0;
        for (Records record: this.records) {
            Records_TXT_Times[i].setText(record.getTime());
            Records_TXT_scores[i].setText(record.getCoins() + "");
            Records_ROW_rows[i].setVisibility(View.VISIBLE);
            i++;
        }
    }


    private void initViews() {
        for (int index = 0; index < ROWS; index++) {
            Records_ROW_rows[index].setOnClickListener(setOnMap(index));
        }
    }

    private View.OnClickListener setOnMap(int index) {
        return view -> callBackRecords.findChosenPlayer(index);
    }

    private void findViews(View view) {
        for (int i = 0; i < ROWS; i++) {
            Records_TXT_Times[i] = view.findViewById(getResources().getIdentifier(
                    "list_TXT_Time" + i,
                    "id",
                    activity.getPackageName()));
            Records_TXT_scores[i] = view.findViewById(getResources().getIdentifier(
                    "list_TXT_score" + i,
                    "id",
                    activity.getPackageName()));
            Records_ROW_rows[i] = view.findViewById(getResources().getIdentifier(
                    "list_ROW_row" + i,
                    "id",
                    activity.getPackageName()));
        }
    }
}