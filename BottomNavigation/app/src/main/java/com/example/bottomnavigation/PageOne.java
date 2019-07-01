package com.example.bottomnavigation;


import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageOne extends Fragment {

    private static RecyclerAdapter adapter;

    static String json;

    private List<ContactItem> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private ArrayList<ContactItem> contactItems;

    private Button addButton;

    private final int ADD_RESULT = 22222;

    public PageOne() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
        for(int i = 0; i < contactItems.size(); i++) {
            System.out.println("PersonID : " + contactItems.get(i).getPerson_id());
            System.out.println("PhotoID : " + contactItems.get(i).getPhoto_id());
        }
        setData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment_one = inflater.inflate(R.layout.fragment_one, container, false);
        RecyclerView recyclerView = fragment_one.findViewById(R.id.recyclerView);


        System.out.println("oncreateViewcalled");


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        addButton = (Button) fragment_one.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 인텐트 실행시키기.
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivityForResult(intent,ADD_RESULT);
            }
        });

//        getData();
        //연락처 데이터 불러오기.

        System.out.println(json);

        adapter = new RecyclerAdapter(getContext(), new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+contactItems.get(position).getUser_phNumber()));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

//        setData();

        editSearch = (EditText) fragment_one.findViewById(R.id.editSearch);

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
            search(text);
            }
        });
        return fragment_one;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        // 이름 받아오는 액티비티 무사히 갔다올 경우
        if (requestCode==ADD_RESULT){
            if(resultCode== Activity.RESULT_OK){
                //
                String UserName = data.getStringExtra("name");
                String Userphone = data.getStringExtra("phone");
                String Useruri = data.getStringExtra("uri");

            }
        }
    }

    private void refresh(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.detach(this).attach(this).commit();}


    public void search(String charText){

        // 리스트 클리어 및 adapter에 있는 아이템 리셋.
        list.clear();
        adapter.resetItem();

        if (charText.length() == 0){
            list.addAll(contactItems);
        }

        else{
            for(int i = 0; i < contactItems.size(); i++){
                if(contactItems.get(i).getUser_Name().toLowerCase().contains(charText)||contactItems.get(i).getUser_phNumber().toLowerCase().contains(charText)){
                    list.add(contactItems.get(i));
                }
            }
        }
        for(int i = 0; i < list.size(); i++){
            adapter.addItem(list.get(i));
        }
        adapter.notifyDataSetChanged();
    }

    //기기 연락처에서 데이터 받아오기.
    private void getData() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        // 받아올 연락처 타입 4가지
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_ID,
                ContactsContract.Contacts._ID
        };
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                + " COLLATE LOCALIZED ASC";
        // 연락처 받아오기.
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null,
                selectionArgs, sortOrder);
        LinkedHashSet<ContactItem> hashlist = new LinkedHashSet<>();
        // 파싱하기.
        if (cursor.moveToFirst()) {
            do {
                long photo_id = cursor.getLong(2);
                long person_id = cursor.getLong(3);
                ContactItem contactItem = new ContactItem();
                contactItem.setUser_phNumber(cursor.getString(0));
                contactItem.setUser_Name(cursor.getString(1));
                contactItem.setPhoto_id(photo_id);
                contactItem.setPerson_id(person_id);

                hashlist.add(contactItem);

            } while (cursor.moveToNext());
        }

        // 연락처 받아와서 contactItems에 넣어줌.(클래스 멤버변수)
        contactItems = new ArrayList<>(hashlist);
        for (int i = 0; i < contactItems.size(); i++) {
            contactItems.get(i).setId(i);
        }

        try {
            // JSON객체 만들기.
            JSONArray arr = new JSONArray();

            for (int i = 0; i < contactItems.size(); i++){
                JSONObject data1 = new JSONObject();

                data1.put("photo_id",contactItems.get(i).getPhoto_id());
                data1.put("person_id",contactItems.get(i).getPerson_id());
                data1.put("name",contactItems.get(i).getUser_Name());
                data1.put("number",contactItems.get(i).getUser_phNumber());
                arr.put(data1);
            }

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

        List<String> listUserName = new ArrayList<>();
        List<String> listNumber = new ArrayList<>();
        List<Integer> listPersonId = new ArrayList<>();
        List<Integer> listImageId = new ArrayList<>();
        list = new ArrayList<ContactItem>();

        try {
            // 한줄의 스트링으로 된 JSON을 파싱하는 과정

            // 스트링 json -> JSONObject타입으로 바꾸기.
            JSONObject root = (JSONObject) new JSONTokener(json).nextValue();

            // userInfo라는 키값을 가진 Value를 JSONArray에 저장하기.
            JSONArray userarray = new JSONArray(root.getString("userInfo"));

            // JSONArray를 이용해서 list타입에 저장시키기. (get하기 위해 try-catch문 필요)
            for (int i = 0; i < userarray.length(); i++) {
                String photo_id = userarray.getJSONObject(i).getString("photo_id");
                String person_id = userarray.getJSONObject(i).getString("person_id");
                String name = userarray.getJSONObject(i).getString("name");
                String number = userarray.getJSONObject(i).getString("number");
                listUserName.add(name);
                listNumber.add(number);
                listImageId.add(Integer.parseInt(photo_id));
                listPersonId.add(Integer.parseInt(person_id));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 각 List의 값들을 data 객체에 set 해줍니다.
        for (int i = 0; i < listUserName.size(); i++) {
            ContactItem contactItem = new ContactItem();
            contactItem.setUser_Name(listUserName.get(i));
            contactItem.setUser_phNumber(listNumber.get(i));
            contactItem.setPerson_id(listPersonId.get(i));
            contactItem.setPhoto_id(listImageId.get(i));


            // 각 값이 들어간 data를 adapter에 추가합니다.
            list.add(contactItem);
        }

        adapter.resetItem();

        for (int i = 0; i < list.size(); i++){
            adapter.addItem(list.get(i));
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();
    }
}
