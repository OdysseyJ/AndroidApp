package com.example.bottomnavigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RewardRecyclerAdapter extends RecyclerView.Adapter<RewardRecyclerAdapter.ItemViewHolder2> {

    // adapter에 들어갈 list 입니다.
    private static ArrayList<RewardData> listData = new ArrayList<RewardData>();

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;


    @NonNull
    @Override
    public ItemViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 rewardrecycler_item을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rewardrecycler_item, parent, false);
        return new ItemViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder2 holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        final int Position = position;
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    void addItem(RewardData data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
        sort();
    }

    void sort() {}
    void resetItem(){
        listData.clear();
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder2 extends RecyclerView.ViewHolder {

        private TextView rewardtextView1;
        private TextView rewardtextView2;

        ItemViewHolder2(View itemView) {
            super(itemView);
            rewardtextView1 = itemView.findViewById(R.id.rewardtextView1);
            rewardtextView2 = itemView.findViewById(R.id.rewardtextView2);
        }

        void onBind(RewardData data) {
            System.out.println("onbound########################");
            rewardtextView1.setText(data.getSec());
            rewardtextView2.setText(data.getMil());
        }
    }
}
