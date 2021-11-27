package com.example.createroute;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    final static int ADD_STARTPOINT = 1;
    final static int ADD_WAYPOINT = 2;
    final static int ADD_ENDPOINT = 3;
    private static final String LOG_TAG = "MainActivity";
    private MapView mMapView;
    TextView txtResult;
    TextView nodeName;
    TextView nodeID;

    Intent INTENTRESULT;
    String NODEID = null;
    String NODENAME = null;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};

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
                }, 0, 0, true);
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
                }, 0, 0, true);
                eTimePicker.show();
            }
        });
    }


    public void mOnPopupClick(View v){
        //데이터 담아서 팝업(액티비티) 호출
        Intent intent = new Intent(this, RouteSelector.class);
        resultLauncher.launch(intent);
    }

    private ActivityResultLauncher<Intent> resultLauncher  =  registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d("Enter", " 확인");
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Log.d("Result Code Check", "결과 코드 확인");

                        INTENTRESULT = result.getData();
                        //데이터 받기
                        NODEID = INTENTRESULT.getStringExtra("NODE_ID");
                        NODENAME = INTENTRESULT.getStringExtra("NODE_NAME");

                        Log.d("DATA",NODENAME);
                        if (NODEID == null || result == null) {
                            Log.d("Result Node Error", "No data");
                        } else {
                            Log.d("Result Node Check", NODENAME);
                        }
                    }

                }});

    public void mOnBusClick(View v){
        if(NODEID == null){
            Toast.makeText(getApplicationContext(), "정류장을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(this, RouteViewer.class);
            intent.putExtra("nodeid",NODEID);
            busLauncher.launch(intent);
        }
    }
    private ActivityResultLauncher<Intent> busLauncher  =  registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d("Enter", " 확인");
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Log.d("Result Code Check", "결과 코드 확인");

                        INTENTRESULT = result.getData();
                        //데이터 받기

                    }

                }});
}



