package com.example.reshamverliani.bmicalculator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class GenericShareActivity extends AppCompatActivity {
    RatingBar rabRatting;
    EditText etFeedback;
    Button btnSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_share);
        rabRatting=(RatingBar)findViewById(R.id.rabRatting);
        etFeedback=(EditText)findViewById(R.id.etFeedback);
        btnSend=(Button)findViewById(R.id.btnSend);
        int orientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientation);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rating=String.valueOf(rabRatting.getRating());
                String feedback=etFeedback.getText().toString();
                String msg="Rating:"+rating+"\n"+"Feedback:"+feedback+"\n";

                Intent m=new Intent(Intent.ACTION_SEND);
                m.setType("text/plain");
                m.putExtra(Intent.EXTRA_TEXT,msg);
                startActivity(m);
                finish();
            }


        });
    }
}
