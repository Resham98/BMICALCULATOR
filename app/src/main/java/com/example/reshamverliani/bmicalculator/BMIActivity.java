package com.example.reshamverliani.bmicalculator;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



import java.util.Date;
import java.util.Locale;

public class BMIActivity extends AppCompatActivity {
    TextView tvBmi;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    Button btnBack;
    Button btnShare;
    Button btnSave;
    FloatingActionButton fabSpeak;
    TextToSpeech tts;
   // TextToSpeech tts;
    static  MyDbHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        tvBmi=(TextView)findViewById(R.id.tvBmi);
        tv1=(TextView)findViewById(R.id.tv1);
        tv2=(TextView)findViewById(R.id.tv2);
        tv3=(TextView)findViewById(R.id.tv3);
        tv4=(TextView)findViewById(R.id.tv4);
        btnBack=(Button)findViewById(R.id.btnBack);
        btnShare=(Button)findViewById(R.id.btnShare);
        btnSave=(Button)findViewById(R.id.btnSave);
        fabSpeak=(FloatingActionButton)findViewById(R.id.fabSpeak);
        db=new MyDbHandler(this);
        int orientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientation);

        Intent i=getIntent();
        final  String msg=i.getStringExtra("msg");
        tvBmi.setText(msg);

      SharedPreferences  sp=getSharedPreferences("p2",MODE_PRIVATE);
        String m=sp.getString("m","");
        String o=sp.getString("o","");
        String z=sp.getString("z","");
        String a=sp.getString("a","");
      final String msg1="Name:"+m+"\n"+"Age:"+o+"\n"+"Phone Number:"+z+"\n"+"Sex:"+a+"\n"+msg;
        // double bmi=0.0;
       final double bmi= i.getDoubleExtra("bmi",0.0);
      final float wt=i.getFloatExtra("weight",0.0f);
        if(bmi<=18.5)
           tv1.setTextColor(Color.RED);
        if(bmi>18.5&&bmi<=25)
            tv2.setTextColor(Color.RED);
        if(bmi>25&&bmi<=30)
            tv3.setTextColor(Color.RED);

        if(bmi>30)
            tv4.setTextColor(Color.RED);


  btnBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          Intent i=new Intent(BMIActivity.this,CalculateActivity.class);
          startActivity(i);
          finish();
         // tts.stop();



      }
  });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent m=new Intent(Intent.ACTION_SEND);
                m.setType("text/plain");
                m.putExtra(Intent.EXTRA_TEXT,msg1);
                startActivity(m);
               // tts.stop();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String con = "";
                con = Double.toString(bmi);
                BMIActivity.db.addBmi(con,wt);



            }
        });
        fabSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String v=tvBmi.getText().toString();
                tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        tts.setLanguage(Locale.ENGLISH);
                        tts.speak(v,TextToSpeech.QUEUE_FLUSH,null);
                        //tts.stop();


                    }
                });




            }
        });
    }
    @Override
    public void onPause(){
        if(tts !=null){
            tts.stop();
            tts.shutdown();
        }

        super.onPause();
    }
    @Override
    protected void onStop()
    {
        super.onStop();

        if(tts != null){
            tts.shutdown();
        }

    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog a=builder.create();
        a.setTitle("EXIT");
        a.show();
       //tts.stop();

        //super.onBackPressed();
    }


    }






