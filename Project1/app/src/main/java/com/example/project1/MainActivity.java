package com.example.project1;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerAdapter adapter;

    Integer[] imageIDs = {
            R.drawable.sample_1,
            R.drawable.sample_2,
            R.drawable.sample_3,
            R.drawable.sample_4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        getData();

        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 100; i++){
            list.add(String.format("TEXT %d", i));
        }

        TabHost tabHost1 = (TabHost) findViewById(R.id.tabHost1) ;
        tabHost1.setup() ;

        Gallery gallery = (Gallery) findViewById(R.id.gallery);
        gallery.setAdapter(new ImageAdapter(this));

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                Toast.makeText(getBaseContext(), "이미지" + (position + 1) + "가 선택되었습니다.", Toast.LENGTH_SHORT).show();
                ImageView imageView = (ImageView) findViewById(R.id.image);
                imageView.setImageResource(imageIDs[position]);
            }
        });

        // 첫 번째 Tab. (탭 표시 텍스트:"연락처"), (페이지 뷰:"phoneBook")
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1") ;
        ts1.setContent(R.id.tab1) ;
        ts1.setIndicator("연락처") ;
        tabHost1.addTab(ts1)  ;

        // 두 번째 Tab. (탭 표시 텍스트:"갤러리"), (페이지 뷰:"gallery")
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2") ;
        ts2.setContent(R.id.tab2) ;
        ts2.setIndicator("갤러리") ;
        tabHost1.addTab(ts2) ;

        // 세 번째 Tab. (탭 표시 텍스트:"미정"), (페이지 뷰:"unknown")
        TabHost.TabSpec ts3 = tabHost1.newTabSpec("Tab Spec 3") ;
        ts3.setContent(R.id.tab3) ;
        ts3.setIndicator("미정") ;
        tabHost1.addTab(ts3) ;
    }

    private void init() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        // 임의의 데이터입니다.
        List<String> listTitle = Arrays.asList("국화", "사막", "수국", "해파리");
        List<String> listContent = Arrays.asList(
                "이 꽃은 국화입니다.",
                "여기는 사막입니다.",
                "이 꽃은 수국입니다.",
                "이 동물은 해파리입니다."
        );
        List<Integer> listResId = Arrays.asList(
                R.drawable.sample_1,
                R.drawable.sample_2,
                R.drawable.sample_3,
                R.drawable.sample_4
        );
        for (int i = 0; i < listTitle.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            Data data = new Data();
            data.setTitle(listTitle.get(i));
            data.setContent(listContent.get(i));
            data.setResId(listResId.get(i));

            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(data);
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();
    }
}
