package com.example.bottomnavigation;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

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

    private static RecyclerAdapter adapter;

    static String json;
    Integer size;

    private static List<Data> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private RecyclerView recyclerView;
    private ArrayList<Data> arraylist;


    public PageOne() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment_one = inflater.inflate(R.layout.fragment_one, container, false);
        RecyclerView recyclerView = fragment_one.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        getData();
        adapter = new RecyclerAdapter(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+arraylist.get(position).getContent()));
                //Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:12345"));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        setData();
        editSearch = (EditText) fragment_one.findViewById(R.id.editSearch);
        recyclerView = (RecyclerView) fragment_one.findViewById(R.id.recyclerView);

        arraylist = new ArrayList<Data>();
        arraylist.addAll(list);

        for(int i = 0; i < arraylist.size(); i++) {
            System.out.println(arraylist.get(i).getTitle());
        }

        // TextEdit에 텍스트가 변경될 경우 실행되는 Listener이다.
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            // 텍스트가 하나 입력될때마다 실행된다.
            @Override
            public void afterTextChanged(Editable editable) {
            String text = editSearch.getText().toString();
            System.out.println(text);
            search(text);
            }
        });
        return fragment_one;
    }
    public void search(String charText){

        // 리스트 클리어 및 adapter에 있는 아이템 리셋.
        list.clear();
        adapter.resetItem();

        if (charText.length() == 0){
            list.addAll(arraylist);
            System.out.println("A");
        }

        else{
            for(int i = 0; i < arraylist.size(); i++){
                System.out.println(arraylist.get(i).getTitle().toLowerCase());
                if(arraylist.get(i).getTitle().toLowerCase().contains(charText)){
                    System.out.println("C");
                    list.add(arraylist.get(i));
                }
            }
        }
        for(int i = 0; i < list.size(); i++){
            adapter.addItem(list.get(i));
        }
        adapter.notifyDataSetChanged();
    }

    static JSONArray arr = new JSONArray();
    private static void getData() {
        try {
            // JSON객체 만들기.
            JSONObject data1 = new JSONObject();
            data1.put("image",R.drawable.man_icon);
            data1.put("name","Jeong");
            data1.put("number","010-1234-5678");

            JSONObject data2 = new JSONObject();
            data2.put("image",R.drawable.woman_icon);
            data2.put("name","Kim");
            data2.put("number","010-4321-7654");

            JSONObject data3 = new JSONObject();
            data3.put("image",R.drawable.man_icon);
            data3.put("name","YeongHee");
            data3.put("number","010-9876-5432");

            JSONObject data4 = new JSONObject();
            data4.put("image",R.drawable.woman_icon);
            data4.put("name","GilDong");
            data4.put("number","010-1234-4321");

            //만든 객체 JSONArray에 추가.
            //JSONArray arr = new JSONArray();
            arr.put(data1);
            arr.put(data2);
            arr.put(data3);
            arr.put(data4);

            // userInfo라는 하나의 객체로 Array묶기.
            JSONObject userInfo = new JSONObject();
            userInfo.put("userInfo",arr);

            json = userInfo.toString();
            System.out.println(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static void AddData(String name, String number) {
        try {
            // JSON객체 만들기.
            arr = new JSONArray();
            adapter.resetItem();

            JSONObject new_data = new JSONObject();
            new_data.put("image",R.drawable.man_icon);
            new_data.put("name",name);
            new_data.put("number",number);

            //만든 객체 JSONArray에 추가.

            arr.put(new_data);

            getData();
            setData();

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
    private static void setData(){
        // JSON Parsing을 위한 ArrayList
        List<String> listTitle = new ArrayList<>();
        List<String> listContent = new ArrayList<>();
        List<Integer> listResId = new ArrayList<>();
        list = new ArrayList<Data>();

        try {
            // 한줄의 스트링으로 된 JSON을 파싱하는 과정

            // 스트링 json -> JSONObject타입으로 바꾸기.
            JSONObject root = (JSONObject) new JSONTokener(json).nextValue();

            // userInfo라는 키값을 가진 Value를 JSONArray에 저장하기.
            JSONArray userarray = new JSONArray(root.getString("userInfo"));

            // JSONArray를 이용해서 list타입에 저장시키기. (get하기 위해 try-catch문 필요)
            for (int i = 0; i < userarray.length(); i++) {
                System.out.println(userarray.getJSONObject(i));
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
            list.add(data);
        }

        for (int i = 0; i < list.size(); i++){
            adapter.addItem(list.get(i));
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();
    }
}
