package com.example.promise;
/*
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class OneOfTheRooms extends AppCompatActivity {


    String[] t = new String[5];
    String roomName;
    ListView lv;
    int count;
    ArrayList<String> get_list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_of_the_rooms);

        Intent room_info = getIntent();
        lv = (ListView)findViewById(R.id.listview);
        count = room_info.getIntExtra("c", 1212);

        for(int i=0;i<count;i++) {
            get_list.add(room_info.getStringExtra("items"+i));// 선택된 친구목록 받음
        }
        roomName = room_info.getStringExtra("room_name");// 방이름 받음
        Toast.makeText(this, "방이름은 ? " + roomName, Toast.LENGTH_SHORT).show();////
        for(int i=0;i<=4;i++){
            t[i] = room_info.getStringExtra("info"+i);// 날짜, 시간 정보받음
            Toast.makeText(this,"시간, 날짜 ? "+t[i],Toast.LENGTH_SHORT).show();////
        }

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,get_list);
        lv.setAdapter(aa);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inroom, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.modify)// 방만들기 눌렀을때
        {
            Toast.makeText(this,"정보수정 화면이 뜬다",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
*/