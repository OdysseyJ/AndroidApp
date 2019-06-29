package com.example.bottomnavigation;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import com.example.bottomnavigation.databinding.FragmentThreeBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageThree extends Fragment {

    Vector<Integer> _1to25, _26to50;
    FragmentThreeBinding binding;
    ItemAdapter adapter;
    Observable<Long> duration;
    Disposable disposable;
    int now;
    Handler handler;
    int first = 0;

    public PageThree() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment_three = inflater.inflate(R.layout.fragment_three, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_three, container, false);
       // binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_three);
        handler = new Handler();
        init();
        binding.retryBtn.setOnClickListener(view -> {
            stop();
            init();

        });
        View view = binding.getRoot();
        return view;
    }

    private void timer() {
        duration = Observable.interval(10, TimeUnit.MILLISECONDS)
                .map(milli -> milli += 1L);
        disposable = duration.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    long sec = s / 100;
                    long milli = s % 100;
                    getActivity().runOnUiThread(() -> binding.timeTxtView.setText(sec + " : " + milli));
                });

    }

    private void stop() {
        CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(this.disposable);
        disposable.dispose();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop();
    }
    private void init() {
        timer();
        binding.gridView.removeOnItemTouchListener(select);
        now = 1;
        _1to25 = new Vector<>();
        _26to50 = new Vector<>();
        binding.gridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                int length = binding.gridView.getWidth() / 5 - 10;
                adapter.setLength(length, length);
                adapter.notifyDataSetChanged();

                binding.gridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        for (int i = 1; i <= 25; i++) {
            _1to25.add(i);
            _26to50.add(i + 25);
        }
        binding.gridView.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        adapter = new ItemAdapter(getActivity());
        binding.gridView.setAdapter(adapter);
        for (int i = 1; i <= 25; i++) {
            int rand = (int) (Math.random() * _1to25.size());
            adapter.init1to25(_1to25.get(rand));
            _1to25.remove(rand);
            adapter.notifyDataSetChanged();
        }
        binding.gridView.addOnItemTouchListener(select);
    }

    private RecyclerView.OnItemTouchListener select = new RecyclerView.OnItemTouchListener() {
        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView parent, @NonNull MotionEvent evt) {
            try {
                switch (evt.getAction()) {
                    case MotionEvent.ACTION_UP:
                        Button child = (Button) parent.findChildViewUnder(evt.getX(), evt.getY());
                        if (child != null) {
                            int selected = Integer.parseInt(child.getText().toString());
                            if (selected == now) {
                                int position = parent.getChildAdapterPosition(child);
                                Log.e("position", " => " + selected);
                                if (selected >= 26 && selected == now)
                                    adapter.setUpVisible(position);
                                now++;
                                if (_26to50 != null) {
                                    int rand = (int) (Math.random() * _26to50.size());
                                    adapter.update26to50(position, _26to50.get(rand));
                                    _26to50.remove(rand);
                                    if (_26to50.size() == 0) _26to50 = null;
                                }
                                adapter.notifyItemChanged(position);
                            } else {
                                Toast.makeText(getActivity(), "다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                            }
                            if (now == 51) stop();
                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean b) {

        }
    };




}
