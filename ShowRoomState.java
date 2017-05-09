package com.example.promise;

import android.widget.SimpleAdapter;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;

public class ShowRoomState extends AppCompatActivity {

    String[] from = { "line1", "line2" };
    int[] to = { android.R.id.text1, android.R.id.text2 };
    String data="약속정보 : ";
    SharedPreferences getPreferID;
    SharedPreferences alarm;
    ListView lv;
    ArrayList<SharedPreferences> getRoomInfo = new ArrayList<SharedPreferences>();
    ArrayList<HashMap<String,String>> RoomName = new ArrayList<HashMap<String, String>>(2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_room_state);

        getPreferID = getSharedPreferences("PFID",MODE_PRIVATE);// 몇번쨰로 만들어진 방인지 알기위해
        int id = getPreferID.getInt("id",-1);
        if(id == -1 ){
            Toast.makeText(this,"방이 없습니다",Toast.LENGTH_SHORT).show();
        }
        else {
            for (int i = 0; i <= id; i++) {
                getRoomInfo.add(getSharedPreferences("Rooms" + i, MODE_PRIVATE));// 각방의 preference를 다 연결
            }
            for (int i = 0; i <= id; i++) {// 방이름들만 뽑아서 리스트에 담아줌
                for (int k=0;k<= 4; k++) {// 날짜 시간 정보 한줄로 만들어줌
                    data += " "+getRoomInfo.get(i).getString("else info" + k,"none")+" ";
                }
                Log.d("rrr","약속정보 == ? "+data);

                HashMap<String, String> map = new HashMap<String, String>();// 리스트뷰 두줄로 만들기
                map.put("line1", getRoomInfo.get(i).getString("Rname" +i, "none"));// 윗줄
                map.put("line2", data);// 아랫줄
                RoomName.add(map);
                data = "약속정보 : ";
            }
        }
/*
        for(int i=0;i<getRoomInfo.getInt("cnt",1);i++) {
            Toast.makeText(this,"이름~~~"+getRoomInfo.getString("name"+i,"none"),Toast.LENGTH_SHORT).show();
        }
        for(int i=0;i<=4;i++){
            Toast.makeText(this,"약속정보~~~ "+getRoomInfo.getString("else info"+i,"none"),Toast.LENGTH_SHORT).show();
        }
        //RoomName.add(getRoomInfo.getString("Rname","none"));
        //Toast.makeText(this, "방이름~~ " + getRoomInfo.getString("Rname", "none"), Toast.LENGTH_SHORT).show();
*/

        lv = (ListView)findViewById(R.id.room_state_list);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, RoomName,
                android.R.layout.simple_list_item_2, from, to);
        //lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv.setAdapter(simpleAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {// 눌렀을때 그 방 띄워줌

                Intent goToRoom = new Intent(ShowRoomState.this,Room.class);
                int c = getRoomInfo.get(position).getInt("cnt",-1);// 선택된 친구 수 뽑아서 저장

                // Intent 변수에 정보 실기
                goToRoom.putExtra("c",c);
                goToRoom.putExtra("room_name",getRoomInfo.get(position).getString("Rname" + position, "none"));// 방이름 실어줌
                for(int i=0;i<c;i++){// 선택된 친구 이름 뽑아서 실어줌
                    goToRoom.putExtra("items"+i,getRoomInfo.get(position).getString("name"+i,"none"));
                }
                for(int i=0;i<=4;i++){// 날짜정보 뽑아서 실어줌
                    goToRoom.putExtra("info"+i,getRoomInfo.get(position).getString("else info"+i,"none"));
                }
                ShowRoomState.this.startActivity(goToRoom);
                Toast.makeText(ShowRoomState.this, position+" 번째 눌림 !", Toast.LENGTH_SHORT).show();
        }
        });


    }//onCreate

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.room_state, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.delet)
        {// 알람의request code와 각 방정보를 저장하는 preference들을 모두 지워버린다
            SharedPreferences.Editor e;
            e = getPreferID.edit();

            e.clear();
            e.commit();

            alarm = getSharedPreferences("num", MODE_PRIVATE);
            SharedPreferences.Editor alarmEdt;
            alarmEdt = alarm.edit();
            alarmEdt.clear();
            alarmEdt.commit();

            Toast.makeText(this, "일단 삭제 id = "+getPreferID.getInt("id",-1), Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
