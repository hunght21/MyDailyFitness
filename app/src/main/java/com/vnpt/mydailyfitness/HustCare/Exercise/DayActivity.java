package com.vnpt.mydailyfitness.HustCare.Exercise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vnpt.mydailyfitness.HustCare.Adapter.IndividualDayAdapter;
import com.vnpt.mydailyfitness.HustCare.Database.DbOperation;
import com.vnpt.mydailyfitness.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DayActivity extends AppCompatActivity {
    private DbOperation mDbOperation;
    private IndividualDayAdapter individualDayAdapter;
    private LinearLayout linearLayout;
    private Button button;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SharedPreferences sharedPreferences;
    private ArrayList<WorkoutData> mWorkOutData;
    private HashMap<String,Integer> q;
    private HashMap<String,Integer> r;
    private String day;
    private float progress;
    private int dayNumber = -1;
    private Intent intent2;
    private final int[] s = {R.array.day1, R.array.day2, R.array.day3, R.array.day4, R.array.day5, R.array.day6, R.array.day7, R.array.day8, R.array.day9, R.array.day10, R.array.day11, R.array.day12, R.array.day13, R.array.day14, R.array.day15, R.array.day16, R.array.day17, R.array.day18, R.array.day19, R.array.day20, R.array.day21, R.array.day22, R.array.day23, R.array.day24, R.array.day25, R.array.day26, R.array.day27, R.array.day28, R.array.day29, R.array.day30};

    public void addExcDsc(){
        this.r=new HashMap<>();
        this.r.put("cat cow stretch", Integer.valueOf(R.string.desc_cat_cow_stretch));
        this.r.put("downward facing dog pose", Integer.valueOf(R.string.desc_downward_facing_dog_pose));
        this.r.put("dry land swim", Integer.valueOf(R.string.desc_dry_land_swim));
        this.r.put("forward spine stretch", Integer.valueOf(R.string.desc_forward_spine_stretch));
        this.r.put("hopping with one leg", Integer.valueOf(R.string.desc_hopping_with_one_leg));
        this.r.put("hands on the head bow down", Integer.valueOf(R.string.desc_hands_on_the_head_bow_down));
        this.r.put("pelvic shift", Integer.valueOf((R.string.desc_pelvic_shift)));
        this.r.put("pilates roll over", Integer.valueOf(R.string.desc_pilates_roll_over));
        this.r.put("plank", Integer.valueOf(R.string.desc_plank));
        this.r.put("rope jumping", Integer.valueOf(R.string.desc_rope_jumping));
        this.r.put("seated toe touch", Integer.valueOf(R.string.desc_seated_toe_touch));
        this.r.put("spot jumping", Integer.valueOf(R.string.desc_spot_jumping));
        this.r.put("standing vertical stretch", Integer.valueOf(R.string.desc_standing_vertical_stretch));
        this.r.put("super cobra stretch", Integer.valueOf(R.string.desc_super_cobra_stretch));
        this.r.put("table top", Integer.valueOf(R.string.desc_table_top));
        this.r.put("triangle pose", Integer.valueOf(R.string.desc_triangle_pose));
        this.r.put("two straight legs up", Integer.valueOf(R.string.desc_two_straight_legs_up));
        this.r.put("wall stretch", Integer.valueOf(R.string.desc_wall_stretch));
        this.r.put("hanging exercise", Integer.valueOf(R.string.desc_hanging_exercise));
    }
    public void addExcImage(){
        this.q=new HashMap<>();
        this.q.put("cat cow stretch", Integer.valueOf(R.drawable.catcowstretch));
        this.q.put("downward facing dog pose", Integer.valueOf(R.drawable.dogpose));
        this.q.put("dry land swim", Integer.valueOf(R.drawable.drrtlandswim));
        this.q.put("forward spine stretch", Integer.valueOf(R.drawable.forwardspinestretch));
        this.q.put("hopping with one leg", Integer.valueOf(R.drawable.hoppingwithoneleg));
        this.q.put("hands on the head bow down", Integer.valueOf(R.drawable.handsontheheadbowdown));
        this.q.put("pelvic shift", Integer.valueOf(R.drawable.pelvicshift));
        this.q.put("pilates roll over", Integer.valueOf(R.drawable.pilatesroolover));
        this.q.put("plank",Integer.valueOf(R.drawable.plank));
        this.q.put("rope jumping", Integer.valueOf(R.drawable.ropejumping));
        this.q.put("seated toe touch",Integer.valueOf(R.drawable.seatedtoetouch));
        this.q.put("spot jumping",Integer.valueOf(R.drawable.spotjumping));
        this.q.put("standing vertical stretch", Integer.valueOf(R.drawable.standingverticalstretch));
        this.q.put("super cobra stretch",Integer.valueOf(R.drawable.supercobrastrech));
        this.q.put("table top",Integer.valueOf(R.drawable.tabletop));
        this.q.put("triangle pose",Integer.valueOf(R.drawable.trianglepose));
        this.q.put("two straight legs up", Integer.valueOf(R.drawable.twostraight));
        this.q.put("wall stretch",Integer.valueOf(R.drawable.wallstretch));
        this.q.put("hanging exercise",Integer.valueOf(R.drawable.hangingexercise));
    }
    public ArrayList<WorkoutData> onBindWorkoutData() {
        ArrayList<WorkoutData> arrayList = new ArrayList<>();
        String[] stringArray = getResources().getStringArray(this.s[this.dayNumber]);
        String dayExcCycles = this.mDbOperation.getDayCycles(this.day);
        StringBuilder sb = new StringBuilder();
        sb.append("Day exc cycles DayActivity: ");
        sb.append(dayExcCycles);
        String str = "TAG";
        Log.e(str, sb.toString());
        int[] iArr = new int[0];
        JSONObject jSONObject = null;
            try {
                jSONObject = new JSONObject(dayExcCycles);
            } catch (Exception e) {
                e.printStackTrace();
            }
        if (jSONObject != null) {
            iArr = new int[jSONObject.length()];
        }
        Log.i("Json", String.valueOf(iArr));
        Log.i(str,r.toString());
        for (int i = 0; i < stringArray.length; i++) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("prepareAdapterData: ");
            sb2.append(stringArray[i]);
            Log.i(str, sb2.toString());
            WorkoutData workoutData = new WorkoutData();
            workoutData.setExcName(stringArray[i]);
            String excName="";
            excName= stringArray[i];
            Log.i("excName: ",excName);
            if(this.r.containsKey(excName)) {
                workoutData.setExcDescResId((Integer) this.r.get(excName));
            } else {
                Log.i("Exception: ","Null pointer Exception");
            }
            try {
                if (jSONObject != null) {
                    iArr[i] = jSONObject.getInt(String.valueOf(i));
                    Log.i("JSonObject", String.valueOf(iArr[i]));
                }
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
            workoutData.setExcCycles(iArr[i]);
            workoutData.setPosition(i);
            workoutData.setImageId((Integer) this.q.get(stringArray[i]));
            arrayList.add(workoutData);
        }
        return arrayList;
        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_day_layout);
        this.button=findViewById(R.id.start_exercise);
        this.recyclerView=findViewById(R.id.individual_list);
        this.linearLayout=findViewById(R.id.individual_container);
        this.linearLayoutManager=new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        this.addExcDsc();
        this.addExcImage();
        Bundle extras=getIntent().getExtras();
        this.day=extras.getString("day");
        this.progress=extras.getFloat("progress");
        this.dayNumber=extras.getInt("day_num");
        this.mDbOperation=new DbOperation(this);
        Toolbar toolbar=findViewById(R.id.individual_toolbar);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                DayActivity.this.finish();
            }
        });
        TextView textView=findViewById(R.id.day_title);
        textView.setText(this.day);
        this.mWorkOutData=onBindWorkoutData();
        this.individualDayAdapter=new IndividualDayAdapter(this,this.day,this.mWorkOutData);
        this.recyclerView.setLayoutManager(this.linearLayoutManager);
        this.recyclerView.setAdapter(this.individualDayAdapter);
        this.recyclerView.addOnItemTouchListener(new ItemClickListener(this, (view, i) -> {
            if(i<DayActivity.this.mWorkOutData.size()){
                Intent intent=new Intent(DayActivity.this,ExcDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("day",day);
                bundle.putInt("framesId", DayActivity.this.mWorkOutData.get(i).getImageId());
                bundle.putString("excName", DayActivity.this.mWorkOutData.get(i).getExcName());
                DayActivity dayActivity = DayActivity.this;
                bundle.putInt("excCycle", DayActivity.this.mWorkOutData.get(i).getExcCycles());
                bundle.putInt("excPosition", i);
                bundle.putInt("excNameDescResId", dayActivity.r.get(dayActivity.mWorkOutData.get(i).getExcName()).intValue());
                intent.putExtras(bundle);
                DayActivity.this.startActivity(intent);
            }

        }));
        this.button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                DayActivity dayActivity = DayActivity.this;
                dayActivity.intent2 = new Intent(dayActivity, MainExerciseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("workoutDataList", DayActivity.this.mWorkOutData);
                DayActivity.this.intent2.putExtras(bundle);
                DayActivity dayActivity2 = DayActivity.this;
                dayActivity2.intent2.putExtra("day", dayActivity2.day);
                DbOperation databaseOperations = new DbOperation(DayActivity.this);
                DayActivity dayActivity3 = DayActivity.this;
                dayActivity3.progress = databaseOperations.getDayProgress(dayActivity3.day);
                DayActivity dayActivity4 = DayActivity.this;
                dayActivity4.intent2.putExtra("progress", dayActivity4.progress);
                DayActivity dayActivity5 = DayActivity.this;
                dayActivity5.startActivity(dayActivity5.intent2);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        this.mWorkOutData = this.onBindWorkoutData();
        this.individualDayAdapter = new IndividualDayAdapter(this, day, mWorkOutData);
        this.recyclerView.setLayoutManager(this.linearLayoutManager);
        this.recyclerView.setAdapter(this.individualDayAdapter);
        this.individualDayAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        this.recyclerView.setAdapter(null);
        super.onDestroy();
    }
}
