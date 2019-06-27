package com.example.bottomnavigation;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageOne extends Fragment {

    private RecyclerAdapter adapter;

    String json;
    Integer size;

    public PageOne() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment_one = inflater.inflate(R.layout.fragment_one, container, false);
        RecyclerView recyclerView =fragment_one.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
        getData();
        setData();
        return fragment_one;
    }

    private void getData() {
        try {
            // JSON객체 만들기.
            JSONObject data1 = new JSONObject();
            data1.put("image",R.drawable.sample_1);
            data1.put("name","정성운");
            data1.put("number","010-1234-5678");

            JSONObject data2 = new JSONObject();
            data2.put("image",R.drawable.sample_2);
            data2.put("name","김철수");
            data2.put("number","010-4321-7654");

            JSONObject data3 = new JSONObject();
            data3.put("image",R.drawable.sample_3);
            data3.put("name","김영희");
            data3.put("number","010-9876-5432");

            JSONObject data4 = new JSONObject();
            data4.put("image",R.drawable.sample_4);
            data4.put("name","홍길동");
            data4.put("number","010-1234-4321");

            //만든 객체 JSONArray에 추가.
            JSONArray arr = new JSONArray();
            arr.put(data1);
            arr.put(data2);
            arr.put(data3);
            arr.put(data4);

            // userInfo라는 하나의 객체로 Array묶기.
            JSONObject userInfo = new JSONObject();
            userInfo.put("userInfo",arr);

            json = userInfo.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // SetData 메서드
    // 한줄의 String으로 이루어진 JSON자료를 파싱해서 Recycler 어댑터에 전달.
    // 예) {"userInfo":{"image":2131165304,"name":"정성운","number":"010-1234-5678"}}
    // 라는 JSON 형태의 String을 Data 객체에
    // Title = "정성운", Content = "010-1234-5678", resId = "2131165304"의 꼴로 저장시킨다.
    // 이후 Data객체를 Recycler Adapter에 전달.
    private void setData(){
        // JSON Parsing을 위한 ArrayList
        List<String> listTitle = new ArrayList<>();
        List<String> listContent = new ArrayList<>();
        List<Integer> listResId = new ArrayList<>();

        try {
            // 한줄의 스트링으로 된 JSON을 파싱하는 과정

            // 스트링 json -> JSONObject타입으로 바꾸기.
            JSONObject root = (JSONObject) new JSONTokener(json).nextValue();

            // userInfo라는 키값을 가진 Value를 JSONArray에 저장하기.
            JSONArray userarray = new JSONArray(root.getString("userInfo"));

            // JSONArray를 이용해서 list타입에 저장시키기. (get하기 위해 try-catch문 필요)
            for (int i = 0; i < userarray.length(); i++) {
                String image = userarray.getJSONObject(i).getString("image");
                String name = userarray.getJSONObject(i).getString("name");
                String number = userarray.getJSONObject(i).getString("number");
                listTitle.add(name);
                listContent.add(number);
                listResId.add(Integer.parseInt(image));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 각 List의 값들을 data 객체에 set 해줍니다.
        for (int i = 0; i < listContent.size(); i++) {
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
