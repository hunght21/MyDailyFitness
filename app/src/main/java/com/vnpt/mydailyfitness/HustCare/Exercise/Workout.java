package com.vnpt.mydailyfitness.HustCare.Exercise;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.vnpt.mydailyfitness.HustCare.Adapter.AllDayAdapter;
import com.vnpt.mydailyfitness.HustCare.Helper.CustomDialog;
import com.vnpt.mydailyfitness.HustCare.Database.DbOperation;
import com.vnpt.mydailyfitness.HustCare.Utils.AppUtilss;
import com.vnpt.mydailyfitness.HustCare.Utils.Constants;
import com.vnpt.mydailyfitness.HustCare.Helper.DialogRepeatExc;
import com.vnpt.mydailyfitness.R;

import java.util.ArrayList;
import java.util.List;

public class Workout extends Fragment  {
    private AllDayAdapter allDayAdapter;
    private double k = 0.0d;
    private ArrayList<String> arrayList;
    private int daysCompletion = 0;
    private int height, width;
    private RecyclerView recyclerView;
    private List<WorkoutData> workoutDataList;
    private int workoutPosition = -1;
    private SharedPreferences sharedPreferences;
    private ProgressBar progressBar;
    private TextView percentScore1, dayLeft;
    private DbOperation mDbOperation;
    private CardView cardView;

    @Override
    public void onDestroy() {
        super.onDestroy();
        BroadcastReceiver broadcastReceiver = this.progressBroadcastReceiver;
        if (broadcastReceiver != null) {
            getActivity().unregisterReceiver(broadcastReceiver);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public BroadcastReceiver progressBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            double doubleExtra = intent.getDoubleExtra(AppUtilss.KEY_PROGRESS, 0.0);
            try {
                Workout.this.workoutDataList.get(Workout.this.workoutPosition).setProgress((float) doubleExtra);
                Workout Workout = Workout.this;
                Workout.k = 0.0d;
                Workout.daysCompletion = 0;
                for (int i = 0; i < Constants.TOTAL_DAYS; i++) {
                    Workout Workout2 = Workout.this;
                    double d = Workout2.k;
                    double progress = Workout2.workoutDataList.get(i).getProgress();
                    Double.isNaN(progress);
                    Workout2.k = (float) (d + ((progress * 4.348d) / 100.0d));
                    if (Workout.this.workoutDataList.get(i).getProgress() >= 99.0f) {
                        Workout.this.daysCompletion = Workout.this.daysCompletion + 1;
                    }

                }
                Workout Workout3 = Workout.this;
                Workout3.daysCompletion = Workout3.daysCompletion + (Workout3.daysCompletion / 3);
                Workout.this.progressBar.setProgress((int) Workout.this.k);
                TextView g = Workout.this.percentScore1;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append((int) Workout.this.k);
                stringBuilder.append("%");
                g.setText(stringBuilder.toString());
                TextView h = Workout.this.dayLeft;
                StringBuilder stringBuilder1 = new StringBuilder();
                stringBuilder1.append(Constants.TOTAL_DAYS - Workout.this.daysCompletion);
                stringBuilder1.append("Day left");
                h.setText(stringBuilder1.toString());
                Workout.this.allDayAdapter.notifyDataSetChanged();
                StringBuilder sb3 = new StringBuilder();
                sb3.append("");
                sb3.append(doubleExtra);
                Log.i("progress broadcast", sb3.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 0) {
            this.mDbOperation.deleteTable();
            SharedPreferences.Editor edit = this.sharedPreferences.edit();
            String str = "daysInserted";
            edit.putBoolean(str, false);
            edit.apply();
            edit.putBoolean(str, true);
            edit.apply();
            List<WorkoutData> list = this.workoutDataList;
            if (list != null) {
                list.clear();
            }
            this.workoutDataList = this.mDbOperation.getAllDayData();
            this.allDayAdapter = new AllDayAdapter(getActivity(), this.workoutDataList);
            this.recyclerView.getRecycledViewPool().clear();
            this.recyclerView.setAdapter(this.allDayAdapter);
            this.progressBar.setProgress(0);
            this.percentScore1.setText("0%");
            TextView textView = this.dayLeft;
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.TOTAL_DAYS);
            sb.append(" Days left");
            textView.setText(sb.toString());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_workout, container, false);
        this.percentScore1 = view.findViewById(R.id.percentScore);
        this.dayLeft = view.findViewById(R.id.daysLeft);
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        this.mDbOperation = new DbOperation(getActivity());
        String str1 = "thirtyDay";
        String str2 = "dayInserted";
        boolean z1 = sharedPreferences.getBoolean(str1, false);
        boolean z2 = sharedPreferences.getBoolean(str2, false);
        if (!z2 && this.mDbOperation.checkDbEmpty() == 0) {
            this.mDbOperation.insertAllDayData();
            SharedPreferences.Editor editor = this.sharedPreferences.edit();
            editor.putBoolean(str2, true);
            editor.apply();
        }
        if (this.workoutDataList != null) {
            this.workoutDataList.clear();
        }
        this.workoutDataList = this.mDbOperation.getAllDayData();
        this.progressBar = view.findViewById(R.id.progress);
        this.progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.launch_progressbar));
        for (int i = 0; i < Constants.TOTAL_DAYS; i++) {
            double d = this.k;
            double progress = (double) this.workoutDataList.get(i).getProgress();
            Double.isNaN(progress);
            this.k = (double) (d + ((progress * 4.348d) / 100));
            if (((WorkoutData) (this.workoutDataList.get(i))).getProgress() >= 99.0d) {
                this.daysCompletion++;
            }
        }

        this.arrayList = new ArrayList<>();
        int i1 = this.daysCompletion;
        this.daysCompletion = i1 + (i1 / 3);
        this.progressBar.setProgress((int) this.k);
        TextView textView1 = this.percentScore1;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((int) this.k);
        stringBuilder.append("%");
        textView1.setText(stringBuilder.toString());
        StringBuilder stringBuilder1 = new StringBuilder();
        TextView textView2 = this.dayLeft;
        stringBuilder1.append(Constants.TOTAL_DAYS - this.daysCompletion);
        stringBuilder.append("Days Left");
        textView2.append(stringBuilder1.toString());

        this.recyclerView = view.findViewById(R.id.workout_rcl);
        this.allDayAdapter = new AllDayAdapter(getActivity(), this.workoutDataList);
        this.recyclerView.getRecycledViewPool().clear();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        for (int i3 = 1; i3 <= Constants.TOTAL_DAYS; i3++) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Day ");
            sb3.append(i3);
            this.arrayList.add(sb3.toString());
        }
        if (z1) {
            SharedPreferences.Editor edit2 = this.sharedPreferences.edit();
            edit2.putBoolean(str1, false);
            edit2.apply();
            restartExcercise();
            this.daysCompletion = 0;
        }
        this.recyclerView.setAdapter(this.allDayAdapter);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.addOnItemTouchListener(new ItemClickListener(getActivity(), new ItemClickListener.onItemClickListener() {
            @Override
            public void OnItem(View view, int i) {
                Intent intent;
                Workout.this.workoutPosition = i;
                if ((Workout.this.workoutPosition + 1) % 4 == 0) {
                    intent = new Intent(getContext(), RestDayActivity.class);
                } else if (((WorkoutData) Workout.this.workoutDataList.get(i)).getProgress() < 99f) {
                    intent = new Intent(getContext(), DayActivity.class);
                    intent.putExtra("day", (String) Workout.this.arrayList.get(i));
                    intent.putExtra("day_num", i);
                    intent.putExtra("progress", (((WorkoutData) Workout.this.workoutDataList.get(i)).getProgress()));
                } else {
                    Workout.this.ShowDialog(i);

                    return;
                }
                Workout.this.startActivity(intent);
            }
        }));
        getActivity().registerReceiver(this.progressBroadcastReceiver, new IntentFilter(AppUtilss.WORKOUT_BROADCAST_FILTER));
        if (this.daysCompletion > 4) {

        }
        return view;
    }

    public void ShowDialog(int i) {
        new MaterialDialog.Builder(getActivity()).title((CharSequence) "Confirm!").titleColor(ContextCompat.getColor(getActivity(), R.color.black)).content((CharSequence) "Would you like to repeat this workout?").contentColor(ContextCompat.getColor
                        (getActivity(), R.color.textColor)).positiveText((CharSequence) "Yes").positiveColor
                        (ContextCompat.getColor(getActivity(), R.color.colorAccent))
                .onPositive(new DialogRepeatExc(this, i)).negativeText((CharSequence) "No")
                .negativeColor(ContextCompat.getColor(getActivity(), R.color.textColor))
                .onNegative(CustomDialog.customDialog).show();
    }

    public void restartExcercise() {
        SharedPreferences.Editor edit = this.sharedPreferences.edit();
        String str1 = "daysInserted";
        edit.putBoolean(str1, false);
        edit.apply();
        for (int i = 1; i <= 30; i++) {
            String str2 = (String) this.arrayList.get(i);
            this.mDbOperation.updateExcProgress(str2, 0.0f);
            this.mDbOperation.updateExcCounter(str2, 0);
        }
        edit.putBoolean(str1, true);
        edit.apply();
        this.workoutDataList = this.mDbOperation.getAllDayData();
        this.allDayAdapter = new AllDayAdapter(getActivity(), this.workoutDataList);
        this.recyclerView.getRecycledViewPool().clear();
        this.recyclerView.setAdapter(this.allDayAdapter);
        this.progressBar.setProgress(0);
        this.percentScore1.setText("0%");
        TextView textView = this.dayLeft;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.TOTAL_DAYS);
        stringBuilder.append("Days left");
        textView.setText(stringBuilder.toString());
    }

    public void repeatExcInform(int i, MaterialDialog materialDialog, DialogAction dialogAction) {
        TextView textView = null;
        String str = null;
        try {
            materialDialog.dismiss();
            String str2 = this.arrayList.get(i);
            if (this.workoutDataList != null) {
                this.workoutDataList.clear();
            }
            this.mDbOperation.updateExcProgress(str2, 0.0f);
            this.mDbOperation.updateExcCounter(str2, 0);
            this.workoutDataList = this.mDbOperation.getAllDayData();
            this.allDayAdapter = new AllDayAdapter(getActivity(), this.workoutDataList);
            this.recyclerView.getRecycledViewPool().clear();
            this.recyclerView.setAdapter(allDayAdapter);
            TextView textView2 = this.dayLeft;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Constants.TOTAL_DAYS - this.daysCompletion);
            stringBuilder.append("Days Left");
            textView2.setText(stringBuilder.toString());
            if (this.daysCompletion > 0) {
                this.progressBar.setProgress((int) (this.k - 4.348d));
                textView = this.percentScore1;
                StringBuilder stringBuilder1 = new StringBuilder();
                stringBuilder1.append((int) (this.k - 3.348d));
                stringBuilder1.append("%");
                str = stringBuilder1.toString();
            } else {
                if (this.daysCompletion == 0) {
                    this.progressBar.setProgress(0);
                    textView = this.percentScore1;
                    str = "0%";
                }
                Intent intent = new Intent(getContext(), DayActivity.class);
                intent.putExtra("day", (String) Workout.this.arrayList.get(i));
                intent.putExtra("day_num", i);
                intent.putExtra("progress", (((WorkoutData) Workout.this.workoutDataList.get(i)).getProgress()));
                startActivity(intent);
            }
            textView.setText(str);
            Intent intent1 = new Intent(getContext(), DayActivity.class);
            intent1.putExtra("day", (String) Workout.this.arrayList.get(i));
            intent1.putExtra("day_num", i);
            intent1.putExtra("progress", (((WorkoutData) Workout.this.workoutDataList.get(i)).getProgress()));
            startActivity(intent1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

