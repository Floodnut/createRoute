package com.example.createroute;

public class Preparation_item {

    //체크박스로 선택되었는지 판단
    boolean checked;

    //버스 정보
    String ItemStringid;
    String ItemStringname;


    //생성자
    Preparation_item(boolean b, String i, String n){
        checked = b;
        ItemStringid = i;
        ItemStringname = n;
    }

    public boolean isChecked(){
        return checked;
    }
}
