package com.example.createroute;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;


public class MainActivity extends AppCompatActivity {

    RecyclerView startPointRe;
    String bus[], station[];

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
        bus = getResources().getStringArray(R.array.bus_num);
        station = getResources().getStringArray(R.array.station_name);
        StartPointAdapter spAdapter = new StartPointAdapter(this, bus, station);
        startPointRe.setAdapter(spAdapter);
        startPointRe.setLayoutManager(new LinearLayoutManager(this));

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
        startActivity(startPosition);
    }
}