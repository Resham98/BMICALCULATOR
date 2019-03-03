package com.example.reshamverliani.bmicalculator;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewHistoryActivity extends AppCompatActivity {
    TextView tvRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);
        tvRecord=(TextView)findViewById(R.id.tvRecord);
        tvRecord.setMovementMethod(new ScrollingMovementMethod());
        int orientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientation);


        String n="";
        n=CalculateActivity.db.viewHistory();
        if(n.length()==0)
            tvRecord.setText("No records to show");
        else
            tvRecord.setText(n);

    }
}
