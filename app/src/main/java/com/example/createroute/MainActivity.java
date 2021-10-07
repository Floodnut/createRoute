package com.example.createroute;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    final static int ADD_STARTPOINT = 1;
    final static int ADD_WAYPOINT = 2;
    final static int ADD_ENDPOINT = 3;


    RecyclerView startPointRe;
    String bus[], station[];

    List busList = new ArrayList<>();
    List stationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Time Setting Buttons
        Button btnSetStartTime = (Button) findViewById(R.id.btnSetStartTime);
        Button btnSetEndTime = (Button) findViewById(R.id.btnSetEndTime);
        TextView btnStartTime = findViewById(R.id.btnSetStartTime);
        TextView btnEndTime = findViewById(R.id.btnSetEndTime);

        //Start Point View
        startPointRe = findViewById(R.id.startPointView);
        //bus = getResources().getStringArray(R.array.bus_num);
        //station = getResources().getStringArray(R.array.station_name);

        //Collections.addAll(busList,bus);
        //Collections.addAll(stationList,station);


        //Time Setting listener
        btnSetStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog sTimePicker = new TimePickerDialog(
                        MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        btnStartTime.setText(String.format("%02d:%02d",hourOfDay,minute));
                    }
                }, 0, 0, false);
                sTimePicker.show();
            }
        });

        btnSetEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog eTimePicker = new TimePickerDialog(
                        MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        btnEndTime.setText(String.format("%02d:%02d",hourOfDay,minute));
                    }
                }, 0, 0, false);
                eTimePicker.show();
            }
        });
    }

    public void mOnClick(View v){
        Intent startPosition = new Intent(this, RouteSelector.class);
        startPosition.putExtra("startBusList",ADD_STARTPOINT);
        startActivityForResult(startPosition,ADD_STARTPOINT);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("RESULT_");
        switch (requestCode) {
            case ADD_STARTPOINT:
                if (resultCode == RESULT_OK) {
                    String busI[] = data.getStringExtra("busInfo").split(", ");
                    busList.add(busI[0]);
                    stationList.add(busI[1]);
                    StartPointAdapter spAdapter = new StartPointAdapter(this, busList, stationList);
                    startPointRe.setAdapter(spAdapter);
                    startPointRe.setLayoutManager(new LinearLayoutManager(this));
                    System.out.println("RESULT_OK: " + busList.get(0));
                }
        }

    }
}

