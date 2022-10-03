package com.vnpt.mydailyfitness.HustCare.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.vnpt.mydailyfitness.R;

import org.json.JSONException;
import org.json.JSONObject;

public class DbManager extends SQLiteOpenHelper {
    public static String DATABASE_NAME="WorkoutDb";
    public static String TABLE_NAME="exercise_day";
    public static String DAY="day";
    public static String PROGRESS="progress";
    public static String COUNTER="counter";
    public static String CYCLES="cycles";
    public static int VERSION=1;
    public String SQLQuery;
    public Context context;
    public int[] cycles={R.array.day1_cycles, R.array.day2_cycles, R.array.day3_cycles, R.array.day4_cycles, R.array.day5_cycles, R.array.day6_cycles, R.array.day7_cycles, R.array.day8_cycles, R.array.day9_cycles, R.array.day10_cycles, R.array.day11_cycles, R.array.day12_cycles, R.array.day13_cycles, R.array.day14_cycles, R.array.day15_cycles, R.array.day16_cycles, R.array.day17_cycles, R.array.day18_cycles, R.array.day19_cycles, R.array.day20_cycles, R.array.day21_cycles, R.array.day22_cycles, R.array.day23_cycles, R.array.day24_cycles, R.array.day25_cycles, R.array.day26_cycles, R.array.day27_cycles, R.array.day28_cycles, R.array.day29_cycles, R.array.day30_cycles};
    public DbManager(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context=context;
        /*
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("CREATE TABLE "+TABLE_NAME);
//        stringBuilder.append(TABLE_NAME);
        stringBuilder.append(" (");
        stringBuilder.append(DAY);
        stringBuilder.append(" TEXT, ");
        stringBuilder.append(PROGRESS);
        stringBuilder.append(" REAL, ");
        stringBuilder.append(COUNTER);
        stringBuilder.append(" INTEGER, ");
        stringBuilder.append(CYCLES);
        stringBuilder.append(" TEXT)");
        */
        this.SQLQuery="CREATE TABLE "+ TABLE_NAME + " (" +
                DAY + " TEXT, " +
                PROGRESS + " REAL, " +
                COUNTER + " INTEGER, " +
                CYCLES+ " TEXT)";
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(this.SQLQuery);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String str="TAG";
        if(oldVersion==2 && newVersion==3){
            StringBuilder stringBuilder1=new StringBuilder();
            stringBuilder1.append("ALTER TABLE");
            stringBuilder1.append(TABLE_NAME);
            stringBuilder1.append(" ADD COLUMN");
            stringBuilder1.append(CYCLES);
            stringBuilder1.append(" TEXT");
            sqLiteDatabase.execSQL(stringBuilder1.toString());
            try{
                JSONObject jsonObject=new JSONObject();
                for(int i=1;i<=30;i++){
                    int count=0;
                    for(int put:this.context.getResources().getIntArray(this.cycles[i-1])) {
                        try {
                            jsonObject.put(String.valueOf(count), put);
                            count++;
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("json str databs: ");
                    sb2.append(jsonObject.toString());
                    Log.e(str, sb2.toString());
                    ContentValues contentValues=new ContentValues();
                    contentValues.put(CYCLES,jsonObject.toString());
                    if(sqLiteDatabase != null){
                        try{
                            StringBuilder sb3=new StringBuilder();
                            sb3.append(DAY);
                            sb3.append("'=DAY");
                            sb3.append(i);
                            sb3.append("'");
                            long update=(long)sqLiteDatabase.update(TABLE_NAME,contentValues,sb3.toString(),null);
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append("res: ");
                            sb4.append(update);
                            Log.e(str, sb4.toString());
                        } catch(Exception e3){
                            e3.printStackTrace();
                        }


                }
            }
            }catch(Exception e4){
                e4.printStackTrace();
            }
        }
    }
}
