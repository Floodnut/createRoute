package com.example.createroute;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RouteSelector extends AppCompatActivity {
    String startBusList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_selector);

        String url = "http://openapi.changwon.go.kr/rest/bis/Bus/?serviceKey=&";
        try {
            this.startBusList = new OpenApi(url,"100").execute().get();
            String bus[] = startBusList.split(", ");
            System.out.println("RETURN RESULT : "+bus[0]+" bus : "+bus[1]);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //MapView mapView = new MapView(this);

        //ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        //mapViewContainer.addView(mapView);
    }

    public void mOnClick(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("busInfo",this.startBusList);
        setResult(RESULT_OK,intent);
        finish();
    }

}

