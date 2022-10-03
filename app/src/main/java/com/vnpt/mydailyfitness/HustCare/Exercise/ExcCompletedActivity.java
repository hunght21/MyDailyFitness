package com.vnpt.mydailyfitness.HustCare.Exercise;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import HomePage.MainActivity;
import com.vnpt.mydailyfitness.HustCare.Utils.Constants;
import com.vnpt.mydailyfitness.R;

import java.util.List;

public class ExcCompletedActivity extends AppCompatActivity {
    private LinearLayoutManager linearLayoutManager;
    private TextView exCompleted,duration,totalCompleted;
    private int e,d;
    private int daysCompletionConter=0;
    private Button shareButton;
    private Context mContext;
    private LayoutInflater layoutInflater;
    private SharedPreferences launchDataPreferences;
    private List<WorkoutData> workoutDataList;
    private Toolbar excCompletedToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_completed_layout);
        StringBuilder sb;
        String str;
        this.launchDataPreferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.exCompleted=findViewById(R.id.congrts_ExNo);
        this.duration=findViewById(R.id.congrts_duration);
        this.excCompletedToolbar=findViewById(R.id.completedToolbar);
        this.excCompletedToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExcCompletedActivity.this.finish();
                Intent intent = new Intent(ExcCompletedActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        this.e=getIntent().getExtras().getInt("totalExc");
        this.d=getIntent().getExtras().getInt("totalTime");
        this.duration.setText(this.change(this.d));
        StringBuilder stringBuilder9=new StringBuilder();
        stringBuilder9.append(e);
        stringBuilder9.append("");
        this.exCompleted.setText(stringBuilder9.toString());



        /*
        String str2 = "";
        int i2 = this.d;
        int i3 = i2 / 60;
        int i4 = i2 % 60;
        String str3 = "0";
        if (i3 < 10) {
            sb = new StringBuilder();
            sb.append(str3);
        } else {
            sb = new StringBuilder();
            sb.append(str2);
        }
        sb.append(i3);
        String sb2 = sb.toString();
        if (i4 < 10) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str3);
            sb3.append(i4);
            str = sb3.toString();
        } else {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str2);
            sb4.append(i4);
            str = sb4.toString();
        }
        TextView textView = this.duration;
        StringBuilder sb5 = new StringBuilder();
        sb5.append(sb2);
        sb5.append(":");
        sb5.append(str);
        textView.setText(sb5.toString());
        TextView textView2 = this.exCompleted;
        StringBuilder sb6 = new StringBuilder();
        sb6.append(str2);
        sb6.append(this.e);
        textView2.setText(sb6.toString());
         */
    }
    public String change(int n){
        int hour,minute,second;
        hour=n/3600;
        minute=((n-(hour*3600))/60);
        second=n-(hour*3600+minute*60);
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(hour);
        stringBuilder.append(":");
        stringBuilder.append(minute);
        stringBuilder.append(":");
        stringBuilder.append(second);
        return stringBuilder.toString();
    }

    public void allCompletionDialogCreate() {
        String str = "OK";
        String str2 = "Cancel";
        new AlertDialog.Builder(this).setTitle((CharSequence) "Congratulations !").setMessage((CharSequence) "You have completed all 30 days workouts. To achieve consistency, please repeat the exercise from day one.").setPositiveButton((CharSequence) str, (DialogInterface.OnClickListener) null).setNegativeButton((CharSequence) str2, (DialogInterface.OnClickListener) null).setPositiveButton((CharSequence) str, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(ExcCompletedActivity.this.getApplicationContext()).edit();
                edit.putBoolean("thirtyday", true);
                edit.apply();
                ExcCompletedActivity.this.finish();
                Constants.TOTAL_DAYS = 30;
                Intent intent = new Intent(ExcCompletedActivity.this, Workout.class);
                ExcCompletedActivity.this.startActivity(intent);
            }
        }).setNegativeButton((CharSequence) str2, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(ExcCompletedActivity.this, Workout.class);
                ExcCompletedActivity.this.startActivity(intent);
                dialogInterface.dismiss();
            }
        }).show();
    }

    @Override
    public void onBackPressed() {
        String str = "TAG";
        if (Constants.TOTAL_DAYS - this.daysCompletionConter == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("daysCompletionConter on backpress if");
            sb.append(Constants.TOTAL_DAYS - this.daysCompletionConter);
            Log.d(str, sb.toString());
            allCompletionDialogCreate();
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

}
