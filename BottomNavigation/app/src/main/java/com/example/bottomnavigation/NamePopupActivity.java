package com.example.bottomnavigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NamePopupActivity extends AppCompatActivity {

    RewardRecyclerAdapter rewardRecyclerAdapter;

    Button returnBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.resultaward);

        //UI 객체생성
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.awardrecyclerView);

        //데이터 가져오기
        Intent intent = getIntent();
        String sec = intent.getStringExtra("sec");
        String mil = intent.getStringExtra("mil");

        rewardRecyclerAdapter = new RewardRecyclerAdapter();
        RewardData temp = new RewardData(sec,mil);
        rewardRecyclerAdapter.addItem(temp);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(rewardRecyclerAdapter);
        rewardRecyclerAdapter.notifyDataSetChanged();

        returnBtn = (Button) findViewById(R.id.button_return);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}
