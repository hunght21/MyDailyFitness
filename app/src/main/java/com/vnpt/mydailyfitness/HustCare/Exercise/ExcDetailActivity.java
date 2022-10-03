package com.vnpt.mydailyfitness.HustCare.Exercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;
import com.vnpt.mydailyfitness.HustCare.Database.DbOperation;
import com.vnpt.mydailyfitness.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ExcDetailActivity extends AppCompatActivity {
    private int dayValue;
    private int imageId;
    private String excName, mDay;
    private int excCycles;
    private int excPos;
    private int excNameDesId;
    private int editCycle;
    private boolean editedValue = false;
    private SharedPreferences sharedPreferences;
    private NumberPicker numberPicker;
    private DbOperation mDbOperation;
    private Context mContext;
    private TextView description, textCycles;
    public int[] s = {R.array.day1_cycles, R.array.day2_cycles, R.array.day3_cycles, R.array.day4_cycles, R.array.day5_cycles, R.array.day6_cycles, R.array.day7_cycles, R.array.day8_cycles, R.array.day9_cycles, R.array.day10_cycles, R.array.day11_cycles, R.array.day12_cycles, R.array.day13_cycles, R.array.day14_cycles, R.array.day15_cycles, R.array.day16_cycles, R.array.day17_cycles, R.array.day18_cycles, R.array.day19_cycles, R.array.day20_cycles, R.array.day21_cycles, R.array.day22_cycles, R.array.day23_cycles, R.array.day24_cycles, R.array.day25_cycles, R.array.day26_cycles, R.array.day27_cycles, R.array.day28_cycles, R.array.day29_cycles, R.array.day30_cycles};
    private ImageView imageView;
    private AnimationDrawable animationDrawable;

    public String createJsonArray(String str) {
        String valueOf = null;
        int i = 0;
        JSONObject jSONObject = null;
        if (str != null) {
            try {
                jSONObject = new JSONObject(str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        int[] intArray = getResources().getIntArray(this.s[this.dayValue - 1]);
        JSONObject jSONObject2 = new JSONObject();
        int i2 = 0;
        for (int i3 : intArray) {
            try {
                if (this.excPos == i2) {
                    valueOf = String.valueOf(this.excPos);
                    i = this.editCycle;
                } else if (jSONObject == null || !jSONObject.has(String.valueOf(i2))) {
                    jSONObject2.put(String.valueOf(i2), i3);
                    i2++;
                } else {
                    valueOf = String.valueOf(i2);
                    i = jSONObject.getInt(String.valueOf(i2));
                }
                jSONObject2.put(valueOf, i);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            i2++;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("json str: ");
        sb.append(jSONObject2.toString());
        Log.e("JsonObject2", sb.toString());
        return jSONObject2.toString();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exc_detail);
        TextView textView;
        int i;
        Resources resources;
        this.mContext=this;
        this.mDbOperation = new DbOperation(this);
        Bundle extras = getIntent().getExtras();
        this.imageId = extras.getInt("framesId");
        String string = extras.getString("excName");
        int i2 = extras.getInt("excCycle");
        this.excPos = extras.getInt("excPosition");
        this.excNameDesId = extras.getInt("excNameDescResId");
        this.mDay = extras.getString("day");
        String str = "";
        this.dayValue = Integer.parseInt(this.mDay.replaceAll("[^0-9]", str));
        StringBuilder sb = new StringBuilder();
        sb.append("exercise position ");
        sb.append(this.excPos);
        Log.e("TAG", sb.toString());
        String upperCase = string.replace("_", " ").toUpperCase();
        Toolbar toolbar = (Toolbar) findViewById(R.id.exc_details_layout_mToolbar);
        ((TextView) toolbar.findViewById(R.id.exc_details_layout_toolbar_title)).setText(upperCase);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DbOperation b = ExcDetailActivity.this.mDbOperation;
                String a2 = ExcDetailActivity.this.mDay;

                String str5=ExcDetailActivity.this.mDbOperation.getDayCycles(ExcDetailActivity.this.mDay);
                Log.e("TAG", str5);
                b.updateExcCycles(a2, ExcDetailActivity.this.createJsonArray(str5));
                if (ExcDetailActivity.this.editedValue) {
                    Toast.makeText(ExcDetailActivity.this.getApplicationContext(), "Excercise cycles are updated.", Toast.LENGTH_SHORT).show();
                }

                ExcDetailActivity.this.editedValue = false;
                ExcDetailActivity.this.finish();
            }
        });
        this.description = findViewById(R.id.description_exDetail);
        this.description.setText(this.excNameDesId);
        this.imageView = findViewById(R.id.animation_exDetail);
        this.imageView.setBackgroundResource(imageId);
        this.animationDrawable = (AnimationDrawable) imageView.getBackground();
        this.animationDrawable.start();
        this.textCycles = findViewById(R.id.numberpicker_cycles);
        textView = this.textCycles;
        resources = getResources();
        i = R.string.seconds;
        textView.setText(resources.getString(i));
        this.numberPicker = findViewById(R.id.number_picker);
        this.numberPicker.setMax(100);
        this.numberPicker.setMin(1);
        this.numberPicker.setValue(i2);
        this.editCycle = i2;
        this.numberPicker.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int i, ActionEnum action) {
                ExcDetailActivity.this.editCycle = i;
                StringBuilder sb = new StringBuilder();
                sb.append("Np val ");
                sb.append(i);
                Log.e("TAG", sb.toString());
                ExcDetailActivity.this.editedValue = true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.e("TAG", " exc details onbackpress saved");
        DbOperation databaseOperations2 = this.mDbOperation;
        String str = this.mDay;
        databaseOperations2.updateExcCycles(str, createJsonArray(databaseOperations2.getDayCycles(str)));
        if (this.editedValue) {
            Toast.makeText(getApplicationContext(), "Exercise cycles are updated.", Toast.LENGTH_SHORT).show();
        }
        this.editedValue = false;

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AnimationDrawable mAnimationDrawable = this.animationDrawable;
        if (mAnimationDrawable != null) {
            mAnimationDrawable.stop();
        }
    }
}