package com.example.reshamverliani.bmicalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by RESHAM VERLIANI on 05-07-2018.
 */

public class MyDbHandler extends SQLiteOpenHelper {
    Context context;
    SQLiteDatabase db;
    MyDbHandler(Context context)
    {
        super(context,"BMIdb",null,1);
        this.context=context;
        db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String sql="create table student(rno integer primary key," + " name text)";
        db.execSQL("create table BMI(date text,"+" bmi String,"+" weight float)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}
    public  void addBmi(String bmi,float weight)
    {
        ContentValues cv=new ContentValues();
        Date d=new Date();
      // DateFormat dateFormat=new SimpleDateFormat();
        String date=""+d;
        cv.put("date",date);
        cv.put("bmi",bmi);
        cv.put("weight",weight);
        long rid=db.insert("BMI",null,cv);
        if(rid<0)
            Toast.makeText(context, "issue", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Saved...", Toast.LENGTH_SHORT).show();
    }


    public String viewHistory()
    {
        Cursor cursor=db.query("BMI",null,null,null,null,null,null);
        StringBuffer sb=new StringBuffer();
        cursor.moveToFirst();


        if(cursor.getCount()>0)
        {
            do
            {
                sb.append(""+cursor.getString(0)+" "+"BMI:"+cursor.getString(1)+" "+
                        "WEIGHT:"+cursor.getString(2)+"\n"+"\n");
            }while(cursor.moveToNext());

        }
            return sb.toString();
    }
}
