package com.vnpt.mydailyfitness.HustCare.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vnpt.mydailyfitness.HustCare.Exercise.WorkoutData;
import com.vnpt.mydailyfitness.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DbOperation {
    public Context mContext;
    public DbManager dbManager;
    public SQLiteDatabase sqLiteDatabase;
    int[] b;
    public int[] cycles = {R.array.day1_cycles, R.array.day2_cycles, R.array.day3_cycles, R.array.day4_cycles, R.array.day5_cycles, R.array.day6_cycles, R.array.day7_cycles, R.array.day8_cycles, R.array.day9_cycles, R.array.day10_cycles, R.array.day11_cycles, R.array.day12_cycles, R.array.day13_cycles, R.array.day14_cycles, R.array.day15_cycles, R.array.day16_cycles, R.array.day17_cycles, R.array.day18_cycles, R.array.day19_cycles, R.array.day20_cycles, R.array.day21_cycles, R.array.day22_cycles, R.array.day23_cycles, R.array.day24_cycles, R.array.day25_cycles, R.array.day26_cycles, R.array.day27_cycles, R.array.day28_cycles, R.array.day29_cycles, R.array.day30_cycles};

    public DbOperation(Context mContext) {
        this.mContext = mContext;
        this.dbManager = new DbManager(mContext);
    }

    public int checkDbEmpty() {
        this.sqLiteDatabase = this.dbManager.getReadableDatabase();
        /*
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("SELECT count(*) FROM ");
        stringBuilder.append(DbManager.TABLE_NAME);
         */
        String selectQuery = "SELECT count(*) FROM " + DbManager.TABLE_NAME;
        Cursor cursor = this.sqLiteDatabase.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        return cursor.getInt(0) > 0 ? 1 : 0;
    }

    public boolean tableIsExist(String str) {
        SQLiteDatabase sqLiteDatabase = this.dbManager.getWritableDatabase();
        boolean exist = false;
        if (sqLiteDatabase != null || str != null || sqLiteDatabase.isOpen()) {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND NAME = ?", new String[]{"table", str});
            if (!cursor.moveToFirst()) {
                return false;
            }
            int i = cursor.getInt(0);
            cursor.close();
            if (i > 0) {
                exist = true;
            }
        }
        return exist;
    }

    public int deleteTable() {
        this.sqLiteDatabase = this.dbManager.getWritableDatabase();
        int delete = this.sqLiteDatabase.delete(DbManager.TABLE_NAME, null, null);
        this.sqLiteDatabase.close();
        return delete;
    }

    @SuppressLint("Range")
    public List<WorkoutData> getAllDayData() {
        ArrayList arrayList = new ArrayList();
        this.sqLiteDatabase = this.dbManager.getReadableDatabase();
        try {
            if (sqLiteDatabase != null) {

                StringBuilder stringBuilder1 = new StringBuilder();
                stringBuilder1.append("select * from ");
                stringBuilder1.append(DbManager.TABLE_NAME);

                // String selectQuery="SELECT * FROM " + DbManager.TABLE_NAME;

                Cursor mCursor = this.sqLiteDatabase.rawQuery(stringBuilder1.toString(), null);
                if (mCursor.moveToFirst()) {
                    while (!mCursor.isAfterLast()) {
                        {
                            WorkoutData workoutData1 = new WorkoutData();
                            workoutData1.setDay(mCursor.getString(mCursor.getColumnIndex(DbManager.DAY)));
                            workoutData1.setProgress(mCursor.getFloat(mCursor.getColumnIndex(DbManager.PROGRESS)));
                            mCursor.moveToNext();
                            arrayList.add(workoutData1);
                        }
                    }
                }
                mCursor.close();
                this.sqLiteDatabase.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    @SuppressLint("Range")
    public float getDayProgress(String dayName) {
        this.sqLiteDatabase = this.dbManager.getReadableDatabase();
        float percent = 0.0f;
        if (this.sqLiteDatabase != null) {

            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("select * from ");
            stringBuilder2.append(DbManager.TABLE_NAME);
            stringBuilder2.append(" where ");
            stringBuilder2.append(DbManager.DAY);
            stringBuilder2.append(" = '");
            stringBuilder2.append(dayName);
            stringBuilder2.append("'");

            // String selectQuery="SELECT * FROM " + DbManager.TABLE_NAME + " WHERE " + DbManager.DAY + " = '" + dayName + " '";
            Cursor cursor1 = this.sqLiteDatabase.rawQuery(stringBuilder2.toString(), null);
            if (cursor1.moveToFirst()) {
                percent = cursor1.getFloat(cursor1.getColumnIndex(DbManager.PROGRESS));
            }
            cursor1.close();
            this.sqLiteDatabase.close();
        }
        return percent;
    }

    @SuppressLint("Range")
    public int getDayCounter(String dayName) {
        this.sqLiteDatabase = this.dbManager.getReadableDatabase();
        int count = 0;
        if (this.sqLiteDatabase != null) {

            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("select * from ");
            stringBuilder3.append(DbManager.TABLE_NAME);
            stringBuilder3.append(" where ");
            stringBuilder3.append(DbManager.DAY);
            stringBuilder3.append(" ='");
            stringBuilder3.append(dayName);
            stringBuilder3.append("'");


            // String selectQuery="SELECT * FROM " + DbManager.TABLE_NAME + " WHERE " + DbManager.DAY + " = '" + dayName + " '";
            Cursor cursor2 = this.sqLiteDatabase.rawQuery(stringBuilder3.toString(), null);
            if (cursor2.moveToFirst()) {
                count = cursor2.getInt(cursor2.getColumnIndex(DbManager.COUNTER));
            }
            cursor2.close();
            this.sqLiteDatabase.close();
        }
        return count;
    }

    @SuppressLint("Range")
    public String getDayCycles(String dayName) {
        this.sqLiteDatabase = this.dbManager.getReadableDatabase();
        String str = "";
        if (this.sqLiteDatabase != null) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("select * from ");
            stringBuilder3.append(DbManager.TABLE_NAME);
            stringBuilder3.append(" where ");
            stringBuilder3.append(DbManager.DAY);
            stringBuilder3.append(" = '");
            stringBuilder3.append(dayName);
            stringBuilder3.append("'");


            String selectQuery = "SELECT * FROM " + DbManager.TABLE_NAME + " WHERE " + DbManager.DAY + " = '" + dayName + "'";
            Cursor cursor3 = this.sqLiteDatabase.rawQuery(stringBuilder3.toString(), null);
            if (cursor3.moveToFirst()) {
                str = cursor3.getString(cursor3.getColumnIndex(DbManager.CYCLES));
            }
            this.sqLiteDatabase.close();
        }
        return str;
    }

    public long insertAllDayData() {
        long data = 0;
        try {
            this.sqLiteDatabase = this.dbManager.getWritableDatabase();
            for (int i = 1; i <= 30; i++) {
                JSONObject jsonObject = new JSONObject();
                int i1 = 0;
                this.b = this.mContext.getResources().getIntArray(this.cycles[i - 1]);
                for (int put : this.b) {
                    try {
                        jsonObject.put(String.valueOf(i1), put);
                        i1++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ContentValues contentValues = new ContentValues();
                StringBuilder stringBuilder4 = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Day ");
                sb2.append(i);
                contentValues.put(DbManager.DAY, sb2.toString());
                contentValues.put(DbManager.PROGRESS, Double.valueOf(0.0f));
                contentValues.put(DbManager.COUNTER, 0);
                contentValues.put(DbManager.CYCLES, jsonObject.toString());
                if (this.sqLiteDatabase != null) {
                    data = this.sqLiteDatabase.insert(DbManager.TABLE_NAME, null, contentValues);
                }
            }
            this.sqLiteDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public int updateExcProgress(String str, float f) {
        int i = 0;
        try {
            this.sqLiteDatabase = this.dbManager.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbManager.PROGRESS, Float.valueOf(f));
            if (this.sqLiteDatabase != null) {
                try {
                    SQLiteDatabase sqLiteDatabase1 = this.sqLiteDatabase;
                    StringBuilder stringBuilder5 = new StringBuilder();
                    String str2 = DbManager.TABLE_NAME;
                    stringBuilder5.append(DbManager.DAY);
                    stringBuilder5.append(" ='");
                    stringBuilder5.append(str);
                    stringBuilder5.append("'");
                    i = sqLiteDatabase1.update(str2, contentValues, stringBuilder5.toString(), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
                this.sqLiteDatabase.close();
        } catch (Exception e2) {
            e2.printStackTrace();
            this.sqLiteDatabase.close();
        }
        return i;
    }

    public int updateExcCounter(String str, int count) {
        int i2 = 0;
        try {
            this.sqLiteDatabase = this.dbManager.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(DbManager.COUNTER, count);
            if (this.sqLiteDatabase != null) {
                try {
                    SQLiteDatabase sqLiteDatabase2 = this.sqLiteDatabase;
                    StringBuilder stringBuilder6 = new StringBuilder();
                    stringBuilder6.append(DbManager.DAY);
                    stringBuilder6.append(" =' ");
                    stringBuilder6.append(str);
                    stringBuilder6.append("'");
                    i2 = sqLiteDatabase2.update(DbManager.TABLE_NAME, contentValues, stringBuilder6.toString(), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            this.sqLiteDatabase.close();
        }
        return i2;
    }

    public int updateExcCycles(String str, String str2) {
        int i = 0;
        String str3 = "'";
        try {
            this.sqLiteDatabase = this.dbManager.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbManager.CYCLES, str2);
            if (this.sqLiteDatabase != null) {
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("UPDATE ");
                    sb.append(DbManager.TABLE_NAME);
                    sb.append(" SET ");
                    sb.append(DbManager.CYCLES);
                    sb.append(" = ");
                    sb.append(str2);
                    sb.append(" WHERE ");
                    sb.append(DbManager.DAY);
                    sb.append(" = '");
                    sb.append(str);
                    sb.append(str3);
                    sb.toString();
                    SQLiteDatabase sQLiteDatabase = this.sqLiteDatabase;
                    String str4 = DbManager.TABLE_NAME;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(DbManager.DAY);
                    sb2.append("='");
                    sb2.append(str);
                    sb2.append(str3);
                    i = sQLiteDatabase.update(str4, contentValues, sb2.toString(), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.sqLiteDatabase.close();
        } catch (Exception e2) {
            e2.printStackTrace();
            this.sqLiteDatabase.close();
        }
        return i;
    }
}




