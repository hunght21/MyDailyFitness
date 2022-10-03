package com.vnpt.mydailyfitness.HustCare.Adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vnpt.mydailyfitness.HustCare.Exercise.WorkoutData;
import com.vnpt.mydailyfitness.R;

import java.util.ArrayList;
import java.util.HashMap;

public class IndividualDayAdapter extends RecyclerView.Adapter<IndividualDayAdapter.IndividualDayViewHolder> {
    private Context mContext;
    private String day;
    private ArrayList<WorkoutData> mWorkOutData;
    private HashMap<String,ArrayList<Integer>> arrayListHashMap ;

    public IndividualDayAdapter(Context mContext, String day,ArrayList<WorkoutData> mWorkOutData){
        this.mWorkOutData = mWorkOutData;
        this.mContext = mContext;
        this.day = day;
    }

    @NonNull
    @Override
    public IndividualDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_day_rows,parent,false);
        return new IndividualDayViewHolder(view);
    }
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull IndividualDayViewHolder holder, int position) {
        TextView textView;
        StringBuilder stringBuilder;
        if(position<mWorkOutData.size()) {
            holder.relativeLayout.setVisibility(View.VISIBLE);
            holder.imageView.setBackgroundResource(this.mWorkOutData.get(position).getImageId());
            if(holder.imageView == null){
                Log.i("Image","null");
            }
            AnimationDrawable animationDrawable= (AnimationDrawable) holder.imageView.getBackground();
            animationDrawable.start();
            holder.textView1.setText(this.mWorkOutData.get(position).getExcName().replace("_", " ").toUpperCase());
            textView = holder.textView2;
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.mWorkOutData.get(position).getExcCycles());
            stringBuilder.append("s");
            textView.setText(stringBuilder.toString());
        }
    }


    @Override
    public int getItemCount() {
      return this.mWorkOutData.size() + 1;
    }

    public class IndividualDayViewHolder extends RecyclerView.ViewHolder{
        public TextView textView1,textView2;
        public ImageView imageView;
        public RelativeLayout relativeLayout;
        public IndividualDayViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.exercise_name);
            textView2=itemView.findViewById(R.id.duration);
            imageView=itemView.findViewById(R.id.exercise_animation);
            relativeLayout=itemView.findViewById(R.id.individual_recycleView);
        }
    }
}
