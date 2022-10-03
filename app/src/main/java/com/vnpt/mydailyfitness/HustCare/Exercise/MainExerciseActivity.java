package com.vnpt.mydailyfitness.HustCare.Exercise;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.vnpt.mydailyfitness.HustCare.Helper.CustomDialog;
import com.vnpt.mydailyfitness.HustCare.Database.DbOperation;
import HomePage.MainActivity;
import com.vnpt.mydailyfitness.HustCare.Utils.AppUtilss;
import HomePage.g;
import com.vnpt.mydailyfitness.R;

import java.util.ArrayList;

public class MainExerciseActivity extends AppCompatActivity {
    private LayoutInflater inflater;
    private TextToSpeech excSpeech;
    private Context myContext;
    private DbOperation mDbOperation;
    private ArrayList<WorkoutData> workoutDataList;
    private String day;
    private double progress;
    private double afterProgress;
    private int excCounter;
    private AnimationDrawable mainExcAnimation;
    private CountDownTimer exerciseTimer;
    private long l,s1;
    private int i=0;
    private float k=1.0f;
    private boolean C;
    private Boolean F;
    private Boolean G;



    // MainExerciseActivity
    private TextView excName,excTimer,progressMax,timerTop;
    private ImageView leftArrow,rightArrow,pauseArrow,resumeArrow,excAnimation;
    private LinearLayout rootLayout,progressLayout;
    private RoundCornerProgressBar roundCornerProgressBar;
    private ProgressBar mainExcProgress;
    private long m = 22000;
    private int mainExcCounter = 1;
    // ReadyActivity
    private Toolbar readyToolbar;
    private TextView excReadyName,readySkip,textDescription,readyTimer;
    private ImageView readyAnimation,pauseImage;
    private ProgressBar readyTimerProgressBar;
    private CoordinatorLayout readyContainer;
    private CountDownTimer readyToGoTimer;
    //  RestTimeActivity
    private ProgressBar restProgressBar;
    private TextView restTime,nextExcName,nextExcTime;
    private Button restButton;
    private ImageView nextExcAnimation,pauseRestTime,resumeRestTime;
    private LinearLayout restContainer;
    private CountDownTimer restTimer;
    public MainExerciseActivity(){

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        getWindow().addFlags(128);
        setContentView(R.layout.exercises_main);
        this.mDbOperation=new DbOperation(this);
        this.readyToolbar=findViewById(R.id.ready_toolbar);
        this.readyToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainExerciseActivity.this.finish();
            }
        });
        this.workoutDataList=((ArrayList) getIntent().getExtras().getSerializable("workoutDataList"));
        Bundle extras=getIntent().getExtras();
        extras.getClass();
        this.day=extras.getString("day");
        this.progress=extras.getFloat("progress");
        this.excCounter= (int) ((this.progress)/(100/(this.workoutDataList.size())));
        // Mapping View MainExcActivity
        this.excAnimation=findViewById(R.id.mainExc_animation);
        this.excName=findViewById(R.id.excName);
        this.leftArrow=findViewById(R.id.previous_exc);
        this.rightArrow=findViewById(R.id.skip_exc);
        this.pauseArrow=findViewById(R.id.pauseMainExcersise);
        this.resumeArrow=findViewById(R.id.resumeMainExcersise);
        this.mainExcProgress=findViewById(R.id.main_progress);
        this.excTimer=findViewById(R.id.main_timer);
        // Mapping View ReadyActivity
        this.excReadyName=findViewById(R.id.movement_name);
        this.readySkip=findViewById(R.id.skip);

        this.textDescription=findViewById(R.id.text_description);
        this.readyTimer=findViewById(R.id.timer);
        this.readyAnimation=findViewById(R.id.ready_image);
        this.pauseImage=findViewById(R.id.pause_image);
        this.readyTimerProgressBar=findViewById(R.id.ready_progress);
        this.readyContainer=findViewById(R.id.ready_background);
        // Mapping View RestActivity
        this.restContainer=findViewById(R.id.rest_time_container);
        this.restProgressBar=findViewById(R.id.rest_progress);
        this.restTime=findViewById(R.id.rest_timer);
        this.nextExcName=findViewById(R.id.next_exercise);
        this.nextExcTime=findViewById(R.id.next_exc_time);
        this.restButton=findViewById(R.id.rest_skip);
        this.nextExcAnimation=findViewById(R.id.next_exc_animation);
        this.pauseRestTime=findViewById(R.id.pauseRestTime);
        this.resumeRestTime=findViewById(R.id.resumeRestTime);

        try{
            this.excAnimation.setBackgroundResource(((WorkoutData)this.workoutDataList.get(excCounter)).getImageId());
        } catch(IndexOutOfBoundsException unused){
            this.excCounter=this.workoutDataList.size()-1;
            this.excAnimation.setBackgroundResource(((WorkoutData)this.workoutDataList.get(excCounter)).getImageId());
        }
        this.mainExcAnimation=(AnimationDrawable) excAnimation.getBackground();
        this.mainExcAnimation.start();
        this.restButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainExerciseActivity.this.restTimer.cancel();
                MainExerciseActivity.this.restTimer.onFinish();
                MainExerciseActivity.this.pauseRestTime.setVisibility(View.GONE);
                MainExerciseActivity.this.resumeRestTime.setVisibility(View.VISIBLE);
            }
        });
        this.readySkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainExerciseActivity.this.readyToGoTimer.cancel();
                MainExerciseActivity.this.readyToGoTimer.onFinish();
            }
        });
        this.rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder sb = new StringBuilder();
                sb.append("mainExcCounter");
                sb.append(MainExerciseActivity.this.excCounter);
                Log.i("mainexc", sb.toString());
                MainExerciseActivity.this.exerciseTimer.cancel();
                MainExerciseActivity mainExcerciseActivity = MainExerciseActivity.this;
                MainExerciseActivity mainExcerciseActivity2 = MainExerciseActivity.this;
                MainExerciseActivity.this.exerciseTimer.onFinish();
            }
        });
        this.leftArrow.setOnClickListener(new ToPreviousExercise(this));
        this.pauseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainExerciseActivity.this.i % 2 ==0){
                    MainExerciseActivity.this.pauseImage.setImageResource(R.drawable.play);
                    MainExerciseActivity mainExcerciseActivity = MainExerciseActivity.this;
                    mainExcerciseActivity.C = true;
                    mainExcerciseActivity.readyToGoTimer.cancel();
                } else{
                    MainExerciseActivity.this.pauseImage.setImageResource(R.drawable.pause);
                    MainExerciseActivity mainExcerciseActivity = MainExerciseActivity.this;
                    mainExcerciseActivity.C = false;
                    mainExcerciseActivity.readyToGoFun(mainExcerciseActivity.s1);
                }
                MainExerciseActivity.this.i=MainExerciseActivity.this.i + 1;
            }
        });
        this.pauseRestTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainExerciseActivity.this.resumeRestTime.setVisibility(View.VISIBLE);
                MainExerciseActivity.this.pauseRestTime.setVisibility(View.GONE);
                MainExerciseActivity.this.restTimer.cancel();
            }
        });
        this.resumeRestTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainExerciseActivity.this.resumeRestTime.setVisibility(View.GONE);
                MainExerciseActivity.this.pauseRestTime.setVisibility(View.VISIBLE);
                MainExerciseActivity mainExcerciseActivity = MainExerciseActivity.this;
                MainExerciseActivity.this.restExcTimer(mainExcerciseActivity.s1);
            }
        });
        this.pauseArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainExerciseActivity.this.pauseArrow.setVisibility(View.GONE);
                MainExerciseActivity.this.resumeArrow.setVisibility(View.VISIBLE);
                MainExerciseActivity.this.exerciseTimer.cancel();
                MainExerciseActivity.this.mainExcAnimation.stop();
            }
        });
        this.resumeArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainExerciseActivity.this.pauseArrow.setVisibility(View.VISIBLE);
                MainExerciseActivity.this.resumeArrow.setVisibility(View.GONE);
                MainExerciseActivity mainExcerciseActivity = MainExerciseActivity.this;
                mainExcerciseActivity.mainExcTimer(s1-1000);
            }
        });
        readyToGoFun(12000);

    }

    @Override
    public void onBackPressed() {
        b();
    }

    private void b() {
            new MaterialDialog.Builder(this).title((CharSequence) "Confirm!").titleColor(ContextCompat.getColor(this, R.color.black)).content((CharSequence) "Would you like to quit the workout?").contentColor(ContextCompat.getColor(this, R.color.textColor)).positiveText((CharSequence) "Yes").positiveColor(ContextCompat.getColor(this, R.color.colorAccent)).onPositive(new g(this)).negativeText((CharSequence) "No").negativeColor(ContextCompat.getColor(this, R.color.textColor)).onNegative(CustomDialog.customDialog).show();
    }

    private void restExcTimer(long j) {
        String upperCase = ((WorkoutData) this.workoutDataList.get(this.excCounter)).getExcName().replace("_", " ").toUpperCase();
        this.nextExcName.setText(upperCase);
        this.nextExcAnimation.setBackgroundResource(this.workoutDataList.get(excCounter).getImageId());
        AnimationDrawable restExcAnimation=(AnimationDrawable) this.nextExcAnimation.getBackground();
        restExcAnimation.start();
        this.restProgressBar.setMax((int) ((this.m)/1000));
        CountDownTimer countDownTimer2=new CountDownTimer(j,1000) {
            @Override
            public void onTick(long j) {
                int i;
                long j3=(j-1000)/1000;
                MainExerciseActivity.this.restProgressBar.setProgress((int) (j3));
                StringBuilder sb3=new StringBuilder();
                sb3.append((int)j3);
                sb3.append("");
                TextView textView4=MainExerciseActivity.this.restTime;
                textView4.setText(sb3.toString());
                MainExerciseActivity.this.s1 = j;
                MainExerciseActivity mainExerciseActivity=MainExerciseActivity.this;
                i=mainExerciseActivity.workoutDataList.get(excCounter).getExcCycles();
                StringBuilder stringBuilder=new StringBuilder();
                stringBuilder.append("00:");
                stringBuilder.append(i);
                MainExerciseActivity.this.nextExcTime.setText(stringBuilder.toString());
            }

            @Override
            public void onFinish() {
                MainExerciseActivity.this.restContainer.setVisibility(View.GONE);
                MainExerciseActivity.this.F = Boolean.FALSE;
                try {
                    MainExerciseActivity.this.pauseArrow.setVisibility(View.VISIBLE);
                    MainExerciseActivity.this.resumeArrow.setVisibility(View.GONE);
                    MainExerciseActivity.this.readyToGoFun(12000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        this.restTimer=countDownTimer2.start();
    }

    private void readyToGoFun(long j) {
        WorkoutData workoutData = this.workoutDataList.get(excCounter);
        this.textDescription.setText(this.workoutDataList.get(excCounter).getExcDescResId());
        String upperCase = this.workoutDataList.get(this.excCounter).getExcName().replace("_", " ").toUpperCase();
        this.excReadyName.setText(upperCase);
        this.readyAnimation.setBackgroundResource(workoutData.getImageId());
        this.mainExcAnimation = (AnimationDrawable) readyAnimation.getBackground();
        this.mainExcAnimation.start();

        this.readyTimerProgressBar.setMax(10);
        CountDownTimer r2=new CountDownTimer(j,1000) {
            @Override
            public void onTick(long j) {
                MainExerciseActivity.this.readyContainer.setVisibility(View.VISIBLE);
                StringBuilder sb = new StringBuilder();
                sb.append("progressbar: ");
                sb.append(((int) j) / 1000);
                Log.i("readyToGoTimer", sb.toString());
                long j2=j-1000;
                MainExerciseActivity.this.readyTimerProgressBar.setProgress((int) (j2/1000));
                TextView count=MainExerciseActivity.this.readyTimer;
                StringBuilder sb2=new StringBuilder();
                sb2.append((int) (j2/1000));
                sb2.append("");
                count.setText(sb2.toString());
            }

            @Override
            public void onFinish() {
                int i;
                MainExerciseActivity.this.G = Boolean.FALSE;
                MainExerciseActivity.this.readyTimerProgressBar.setProgress(0);
                MainExerciseActivity.this.readyContainer.setVisibility(View.GONE);
                MainExerciseActivity mainExerciseActivity = MainExerciseActivity.this;
                i=mainExerciseActivity.workoutDataList.get(excCounter).getExcCycles();
                long j=(i+2)* 1000L;
                mainExerciseActivity.pauseArrow.setVisibility(View.VISIBLE);
                mainExerciseActivity.resumeArrow.setVisibility(View.GONE);
                mainExerciseActivity.mainExcTimer(j);
            }
        };
        this.readyToGoTimer=r2.start();


    }

    private void mainExcTimer(long j1) {
        WorkoutData workoutData = this.workoutDataList.get(excCounter);
        this.excAnimation.setBackgroundResource(workoutData.getImageId());
        this.mainExcAnimation = (AnimationDrawable) excAnimation.getBackground();
        this.restContainer.setVisibility(View.GONE);
        this.mainExcAnimation.start();
        this.mainExcProgress.setMax((int) ((j1 / 1000)));
        CountDownTimer mainExcCountDownTimer = new CountDownTimer(j1, 1000) {
            @Override
            public void onTick(long j1) {
                String upperCase;
                String str2 = " ";
                long j3 = j1 - 1000;
                MainExerciseActivity.this.mainExcProgress.setProgress((int) (j3/1000));
                TextView count = MainExerciseActivity.this.excTimer;
                StringBuilder sb = new StringBuilder();
                sb.append((int) (j3 / 1000));
                sb.append("");
                count.setText(sb.toString());
                MainExerciseActivity.this.s1 = j1;
                upperCase = ((WorkoutData) MainExerciseActivity.this.workoutDataList.get(MainExerciseActivity.this.excCounter)).getExcName().toUpperCase();
                MainExerciseActivity.this.excName.setText(upperCase);
            }

            @Override
            public void onFinish() {
                MainExerciseActivity.this.excCounter = MainExerciseActivity.this.excCounter + 1;
                MainExerciseActivity.this.mainExcAnimation.stop();
                if (MainExerciseActivity.this.excCounter < MainExerciseActivity.this.workoutDataList.size()) {
                    MainExerciseActivity.this.restContainer.setVisibility(View.VISIBLE);
                    float excDayProgress = MainExerciseActivity.this.mDbOperation.getDayProgress(MainExerciseActivity.this.day);
                    MainExerciseActivity mainExcerciseActivity3 = MainExerciseActivity.this;
                    double size = (double) ((float) mainExcerciseActivity3.workoutDataList.size());
                    Double.isNaN(size);
                    mainExcerciseActivity3.progress = 100.0d / size;
                    MainExerciseActivity mainExcerciseActivity4 = MainExerciseActivity.this;
                    double d2 = (double) excDayProgress;
                    double o = mainExcerciseActivity4.progress;
                    Double.isNaN(d2);
                    mainExcerciseActivity4.progress = d2 + o;
                    if (MainExerciseActivity.this.progress <= 100.0d) {
                        MainExerciseActivity.this.mDbOperation.updateExcProgress(MainExerciseActivity.this.day, (float) MainExerciseActivity.this.progress);
                    }
                    try {
                        Intent intent = new Intent(AppUtilss.WORKOUT_BROADCAST_FILTER);
                        intent.putExtra(AppUtilss.KEY_PROGRESS, MainExerciseActivity.this.progress);
                        MainExerciseActivity.this.sendBroadcast(intent);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        Log.e("Failed update progress", e2.getMessage());
                    }
                    MainExerciseActivity.this.pauseRestTime.setVisibility(View.VISIBLE);
                    MainExerciseActivity.this.resumeRestTime.setVisibility(View.GONE);
                    MainExerciseActivity mainExcerciseActivity5 = MainExerciseActivity.this;
                    mainExcerciseActivity5.restExcTimer(mainExcerciseActivity5.m);

                } else {
                    MainExerciseActivity mainExcerciseActivity6 = MainExerciseActivity.this;
                    double size2 = (double) ((float) mainExcerciseActivity6.workoutDataList.size());
                    Double.isNaN(size2);
                    mainExcerciseActivity6.progress = 100.0d / size2;
                    float excDayProgress2 = MainExerciseActivity.this.mDbOperation.getDayProgress(MainExerciseActivity.this.day);
                    MainExerciseActivity mainExcerciseActivity7 = MainExerciseActivity.this;
                    double d3 = (double) excDayProgress2;
                    double o2 = mainExcerciseActivity7.progress;
                    Double.isNaN(d3);
                    mainExcerciseActivity7.progress = d3 + o2;
                    if (((int) MainExerciseActivity.this.progress) <= 100) {
                        MainExerciseActivity.this.mDbOperation.updateExcProgress(MainExerciseActivity.this.day, (float) MainExerciseActivity.this.progress);
                    }
                    try {
                        Intent intent2 = new Intent(AppUtilss.WORKOUT_BROADCAST_FILTER);
                        intent2.putExtra(AppUtilss.KEY_PROGRESS, MainExerciseActivity.this.progress);
                        MainExerciseActivity.this.sendBroadcast(intent2);
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    MainExerciseActivity.this.restContainer.setVisibility(View.GONE);
                    Intent intent3 = new Intent(MainExerciseActivity.this, ExcCompletedActivity.class);
                    intent3.putExtra("day", MainExerciseActivity.this.day);
                    int i2 = 0;
                    int i3 = MainExerciseActivity.this.workoutDataList.size();
                    for (int i4 = 0; i4 < MainExerciseActivity.this.workoutDataList.size(); i4++) {
                        i2 += ((WorkoutData) MainExerciseActivity.this.workoutDataList.get(i4)).getExcCycles();
                    }
                    intent3.putExtra("totalExc", i3);
                    intent3.putExtra("totalTime", i2);
                    MainExerciseActivity.this.startActivity(intent3);
                    MainExerciseActivity mainExcerciseActivity8 = MainExerciseActivity.this;
                    mainExcerciseActivity8.k = 1.0f;
                    mainExcerciseActivity8.mainExcCounter = 1;
                }
            }
        };
        this.exerciseTimer=mainExcCountDownTimer.start();
    }

    public void previousExercise(View view) {
        if(this.excCounter>0){
            this.exerciseTimer.cancel();
            this.excCounter--;
            this.mainExcProgress.setMax(60);
            this.pauseArrow.setVisibility(View.VISIBLE);
            this.resumeArrow.setVisibility(View.GONE);
            double excDayProgress = (double) this.mDbOperation.getDayProgress(this.day);
            double size = (double) ((float) this.workoutDataList.size());
            Double.isNaN(size);
            double d=100/size;
            Double.isNaN(excDayProgress);
            this.afterProgress=excDayProgress-d;
            dataBaseProgressUpdate(this.afterProgress);
            readyToGoFun(20000);
        } else {
            Toast.makeText(this,"This is first exercise",Toast.LENGTH_SHORT).show();
        }

    }

    public void dataBaseProgressUpdate(double d) {
        this.mDbOperation.updateExcCounter(this.day,excCounter);
        this.mDbOperation.updateExcProgress(this.day, (float) d);
        StringBuilder sb = new StringBuilder();
        sb.append(this.excCounter);
        sb.append("");
        Log.d("CounterValue", sb.toString());
        try {
            Intent intent = new Intent(AppUtilss.WORKOUT_BROADCAST_FILTER);
            intent.putExtra(AppUtilss.KEY_PROGRESS, d);
            sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        CountDownTimer countDownTimer = this.readyToGoTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        CountDownTimer countDownTimer2 = this.exerciseTimer;
        if (countDownTimer2 != null) {
            countDownTimer2.cancel();
        }
        CountDownTimer countDownTimer3 = this.restTimer;
        if (countDownTimer3 != null) {
            countDownTimer3.cancel();
        }
        if (!this.C) {
            this.i--;
        }
        this.pauseImage.setImageResource(R.drawable.play);
        this.resumeArrow.setVisibility(View.VISIBLE);
        this.pauseArrow.setVisibility(View.GONE);
        this.resumeRestTime.setVisibility(View.VISIBLE);
        this.pauseRestTime.setVisibility(View.GONE);
        this.mainExcAnimation.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.pauseArrow.setVisibility(View.GONE);
        this.resumeArrow.setVisibility(View.VISIBLE);
    }

    public void a(MaterialDialog materialDialog, DialogAction dialogAction) {
        try {
            materialDialog.dismiss();
            if (this.readyToGoTimer != null) {
                this.readyToGoTimer.cancel();
            }
            if (this.exerciseTimer != null) {
                this.exerciseTimer.cancel();
            }
            if (this.restTimer != null) {
                this.restTimer.cancel();
            }
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            onSuperBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void onSuperBackPressed() {
        super.onBackPressed();
    }
}
