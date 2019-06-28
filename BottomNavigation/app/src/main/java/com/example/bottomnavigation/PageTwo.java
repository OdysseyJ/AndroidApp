package com.example.bottomnavigation;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
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
public class PageTwo extends Fragment {

    //ImageID
    Integer[] imageIDs = ImageAdapter.imageIDs;

    public PageTwo() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragment_two = inflater.inflate(R.layout.fragment_two, container, false);
        Gallery gallery = (Gallery) fragment_two.findViewById(R.id.gallery);
        gallery.setAdapter(new ImageAdapter(getActivity()));

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                Toast toast = Toast.makeText(container.getContext(), "이미지" + (position + 1) + "가 선택되었습니다.", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, 200);
                toast.show();

                ImageView imageView = (ImageView) fragment_two.findViewById(R.id.image);
                imageView.setImageResource(imageIDs[position]);
            }
        });
        return fragment_two;
    }

}
