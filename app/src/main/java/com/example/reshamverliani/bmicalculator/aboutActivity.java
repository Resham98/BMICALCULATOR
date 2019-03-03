package com.example.reshamverliani.bmicalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class aboutActivity extends AppCompatActivity {
    TextView tvabt1,tvabt2,tvabt3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        tvabt1=(TextView)findViewById(R.id.tvabt1);
        tvabt2=(TextView)findViewById(R.id.tvabt2);
        tvabt3=(TextView)findViewById(R.id.tvabt3);
    }
}
