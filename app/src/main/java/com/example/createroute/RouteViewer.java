package com.example.createroute;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteViewer extends AppCompatActivity {
    String whereSelected = "창원시";
    String startBusList;
    //final static int changwonCode = 38010;
    private final String key = "-";
    private List<String> list;
    private ListView listView;
    private EditText editSearch;
    private SearchAdapter searchAdapter;
    private ArrayList<String> arrayList;
    private int eventType;
    Resources res;
    String[] localNodes_Name;
    String[] localNodes_ID;
    String[] localNodes_TP;
    String[] localNodes_GPS_LONG;
    String[] localNodes_GPS_LATI;

    private String selectedName;
    private String selectedID;
    private String selectedGPS_Long;
    private String selectedGPS_Lati;
    private String selectedNodeId;
    private ArrayList<String> selectedRouteList;

    private myDBHelper busHelper = new myDBHelper(this);;
    SQLiteDatabase bandy;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        //Window Blur
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.0f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.activity_bus_selector);


        //Window Title
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        //WindowSize
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = (int) (dm.widthPixels * 0.9); // Display 사이즈의 90%
        int height = (int) (dm.heightPixels * 0.6);  // Display 사이즈의 90%
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;


        //정류장 번호 받기
        Intent node = getIntent();
        selectedNodeId = node.getStringExtra("nodeid");
        bandy = busHelper.getReadableDatabase();
        //busHelper.onCreate(bandy);
        String getBusListQuery = "1";
        //bandy.execSQL(getBusListQuery);

        cursor = bandy.rawQuery("Select * from routeInNode where nodeid = ?;",new String[] {selectedNodeId});
        cursor.moveToFirst();
        Log.d("se",selectedNodeId);
        Log.d("nodeid",cursor.getString(1));
        Log.d("routeid",cursor.getString(2));
        Log.d("routeno",cursor.getString(3));
    }


    //버스 선택 후 메인액티비티로 전달
    public void mOnBusClose(View v){
        //if(selectedRouteList.size() == 0 || selectedRouteList == null){
        //    bandy.close();
        //    finish();
        //}else {
        //데이터 전달하기
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Route_List", selectedRouteList);
        setResult(RESULT_OK, intent);
        bandy.close();
        //액티비티(팝업) 닫기
        finish();
        //}
    }

    public class myDBHelper extends SQLiteOpenHelper{
        @Override
        public void onCreate(SQLiteDatabase db) {
            String createNoticeQuery = "CREATE TABLE IF NOT EXISTS Notice(notiId integer primary key, notiName text, nodeId text,notiTime int ,startAt text, endAt text, days integer,  isOn integer(1));";
            String createRouteInNoticeQuery = "CREATE TABLE IF NOT EXISTS RouteInNotice(id primary key, notiId integer, routeID text, routeName text, foreign key(notiId) references Notice(notiId));";
            db.execSQL(createNoticeQuery);
            db.execSQL(createRouteInNoticeQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        }

        public myDBHelper(Context context){
            super(context, "bandy",null,1);
        }
    }
}

