package com.example.bottomnavigation;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageThree extends Fragment {

    //ImageID
    Integer[] imageIDs = ImageAdapter.imageIDs;

    public PageThree() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment_three = inflater.inflate(R.layout.fragment_three, container, false);
//        Gallery gallery = (Gallery) fragment_three.findViewById(R.id.gallery);
//        ImageAdapter adapter = new ImageAdapter(getActivity().getApplicationContext());
//        gallery.setAdapter(adapter);
//
//        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
//                Toast.makeText(getContext(), "이미지" + (position + 1) + "가 선택되었습니다.", Toast.LENGTH_SHORT).show();
//                ImageView imageView = (ImageView) getView().findViewById(R.id.image);
//                imageView.setImageResource(imageIDs[position]);
//            }
//        });
        return fragment_three;
    }



}
