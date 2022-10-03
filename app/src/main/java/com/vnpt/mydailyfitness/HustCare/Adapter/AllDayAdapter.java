package com.vnpt.mydailyfitness.HustCare.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.vnpt.mydailyfitness.HustCare.Exercise.ItemClickListener;
import com.vnpt.mydailyfitness.HustCare.Exercise.WorkoutData;
import com.vnpt.mydailyfitness.R;

import java.util.List;

public class AllDayAdapter extends RecyclerView.Adapter<AllDayAdapter.AllDayViewHolder> {
    private Context mContext;
    private List<WorkoutData> workoutDataList;
    private int position=-1;
    private ItemClickListener itemClickListener;
    private WorkoutData mWorkoutData;

    public AllDayAdapter(Context context,List<WorkoutData> mWorkoutDataList) {
        this.workoutDataList = mWorkoutDataList;
    }
    @NonNull
    @Override
    public AllDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.all_day_rows,parent,false);
        return new AllDayViewHolder(view);
    }
    public long getItemId(int i) {
        return (long) i;
    }

    public int getItemViewType(int i) {
        return i;
    }
    @Override
    public void onBindViewHolder(@NonNull AllDayViewHolder holder, int position) {
        holder.numberProgressBar.setMax(100);
        if((position+1)%4!=0||position>27){
            holder.imageView.setVisibility(View.GONE);
            holder.rl2.setVisibility(View.GONE);

            holder.textView.setText(this.workoutDataList.get(position).getDay());
            holder.numberProgressBar.setVisibility(View.VISIBLE);
            holder.rl1.setVisibility(View.VISIBLE);
            if(((int) this.workoutDataList.get(position).getProgress())>=99){
                holder.numberProgressBar.setProgress(100);
                holder.rl3.setVisibility(View.VISIBLE);
            } else{
                holder.numberProgressBar.setProgress((int) this.workoutDataList.get(position).getProgress());
            }
        } else {
            holder.numberProgressBar.setVisibility(View.GONE);
            holder.textView.setText("Rest Day");
            holder.rl1.setVisibility(View.GONE);
            holder.rl2.setVisibility(View.VISIBLE);
            holder.imageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        List<WorkoutData> list = this.workoutDataList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }
    public class AllDayViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private RelativeLayout rl1,rl2,rl3;
        private CardView cardView;
        private ImageView imageView;
        private NumberProgressBar numberProgressBar;


        public AllDayViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.text_view_row);
            cardView=itemView.findViewById(R.id.card_view_1);
            rl1=itemView.findViewById(R.id.relative_layout_1);
            rl2=itemView.findViewById(R.id.relative_layout_2);
            imageView=itemView.findViewById(R.id.rest_day_image);
            numberProgressBar=itemView.findViewById(R.id.number_progressBar);
            rl3=itemView.findViewById(R.id.relative_layout_3);
        }
    }
}
