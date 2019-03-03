
package com.example.reshamverliani.bmicalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
     TextView tvPersonal;
     EditText etName;
    EditText etAge;
    EditText etPhoneNumber;
    RadioButton rbMale;
    RadioButton rbFemale;
    Button btnRegister;
    SharedPreferences sp;
    SharedPreferences sp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvPersonal=(TextView)findViewById(R.id.tvPersonal);
        etName=(EditText)findViewById(R.id.etName);
        etAge=(EditText)findViewById(R.id.etAge);
        etPhoneNumber=(EditText)findViewById(R.id.etPhoneNumber);
        rbMale=(RadioButton)findViewById(R.id.rbMale);
        rbFemale=(RadioButton)findViewById(R.id.rbFemale);
        btnRegister=(Button)findViewById(R.id.btnRegister);
        sp=getSharedPreferences("p1",MODE_PRIVATE);
         sp1=getSharedPreferences("p2",MODE_PRIVATE);
        int orientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientation);
        String n=sp.getString("n","");
       String m=sp1.getString("m","");
        String o=sp1.getString("o","");
        String z=sp1.getString("z","");
        String a=sp1.getString("a","");


        if(n.length()!=0)
        {
            Intent i=new Intent(DetailActivity.this,CalculateActivity.class);
            startActivity(i);
            finish();

        }
        else {


            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = etName.getText().toString();
                    String age = etAge.getText().toString();
                    String number = etPhoneNumber.getText().toString();
                    String sex="";
                    if(rbMale.isChecked())
                        sex =rbMale.getText().toString();
                    if(rbFemale.isChecked())
                            sex=rbFemale.getText().toString();


                    if (name.length() == 0) {
                        etName.setError("Please enter some text");
                        etName.requestFocus();
                        return;
                    }
                    if (age.length() == 0) {
                        etAge.setError("Enter some age");
                        etAge.requestFocus();
                        return;
                    }
                    if (age.equals("0")) {
                        etAge.setError("Invalid Age");
                        etAge.requestFocus();
                        return;
                    }
                    if (number.length() == 0) {
                        etPhoneNumber.setError("Enter some phone number");
                        etPhoneNumber.requestFocus();
                        return;
                    }
                    if (number.length() != 10) {
                        etPhoneNumber.setError("Enter 10 digit number");
                        etPhoneNumber.requestFocus();
                        return;
                    }
                    if(!rbFemale.isChecked()&&!rbMale.isChecked()) {
                        Toast.makeText(DetailActivity.this, "Please select sex...", Toast.LENGTH_SHORT).show();
                        return;

                    }


                    Toast.makeText(DetailActivity.this, "Registerd successfully...", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("n", name);
                    editor.commit();
                    SharedPreferences.Editor ed=sp1.edit();
                    ed.putString("m",name);
                    ed.putString("o",age);
                    ed.putString("z",number);
                    ed.putString("a",sex);
                    ed.commit();
                    Intent m = new Intent(DetailActivity.this, CalculateActivity.class);
                    startActivity(m);
                    finish();


                }


            });

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

        //super.onBackPressed();
    }


}
