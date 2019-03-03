package com.example.reshamverliani.bmicalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CalculateActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    TextView tvHeight;
    TextView tvFeet;
    TextView tvInch;
    TextView tvWeight;
    EditText etWeight;
    Button btnCalculate;
    Button btnViewHistory;
    Spinner sp1;
    Spinner sp2;
    TextView tvData;
    SharedPreferences sp;
    GoogleApiClient gac;
    Location loc;
    TextView tvLocation;
    TextView tvWeather;
    static MyDbHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);
        tvHeight = (TextView) findViewById(R.id.tvHeight);
        tvFeet = (TextView) findViewById(R.id.tvFeet);
        tvInch = (TextView) findViewById(R.id.tvInch);
        tvWeight = (TextView) findViewById(R.id.tvWeight);
        etWeight = (EditText) findViewById(R.id.etWeight);
        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        btnViewHistory = (Button) findViewById(R.id.btnViewHistory);
        tvWeather=(TextView)findViewById(R.id.tvWeather);
        tvLocation=(TextView)findViewById(R.id.tvLocation);
        sp1 = (Spinner) findViewById(R.id.sp1);
        sp2 = (Spinner) findViewById(R.id.sp2);
        int orientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientation);


        tvData = (TextView) findViewById(R.id.tvData);
        sp = getSharedPreferences("p1", MODE_PRIVATE);
        String n = sp.getString("n", "");
        tvData.setText("Welcome  " + n);
        db = new MyDbHandler(this);

        final ArrayList<String> feet = new ArrayList<>();
        //feet.add("----Select----");
        feet.add("1");
        feet.add("2");
        feet.add("3");
        feet.add("4");
        feet.add("5");
        feet.add("6");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, feet);
        sp1.setAdapter(adapter);


        final ArrayList<String> inch = new ArrayList<>();
        //inch.add("----Select----");
        inch.add("0");
        inch.add("1");
        inch.add("2");
        inch.add("3");
        inch.add("4");
        inch.add("5");
        inch.add("6");
        inch.add("7");
        inch.add("8");
        inch.add("9");
        inch.add("10");
        inch.add("11");



        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, inch);
        sp2.setAdapter(adapter);
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        builder.addApi(LocationServices.API);
        builder.addOnConnectionFailedListener(this);
        builder.addConnectionCallbacks(this);
        gac = builder.build();
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wt = etWeight.getText().toString();

                if (wt.equals("0")) {
                    etWeight.setError("Invalid Weight");
                    etWeight.requestFocus();
                    return;
                }
                if (wt.length() == 0) {
                    etWeight.setError("Please Enter Some weight");
                    etWeight.requestFocus();
                    return;
                }
                float weight=Float.parseFloat(wt);

                int id1 = sp1.getSelectedItemPosition();
                String s = feet.get(id1);
                double t = Double.parseDouble(s);
                t = t * 0.3048;

                int id2 = sp2.getSelectedItemPosition();
                String s1 = inch.get(id2);
                double r = Double.parseDouble(s1);
                r = r * 0.0254;


                double m = Double.parseDouble(wt);
                double bmi = (m) / ((t + r) * (t + r));
                bmi = Double.parseDouble(String.format("%.2f", bmi));
                String msg = "";
                if (bmi <= 18.5)
                    msg = "Your BMI is " + bmi + " and you are underweight";
                if (bmi > 18.5 && bmi <= 25)
                    msg = "Your BMI is " + bmi + " and you are Normal";
                if (bmi > 25 && bmi <= 30)
                    msg = "Your BMI is " + bmi + " and you are Overweight";
                if (bmi > 30)
                    msg = "Your BMI is " + bmi + " and you are Obese";

                Intent i = new Intent(CalculateActivity.this, BMIActivity.class);
                i.putExtra("msg", msg);
                i.putExtra("bmi", bmi);
                i.putExtra("weight",weight);
               // Date d=new Date();
               // DateFormat dateFormat=new SimpleDateFormat();
                //String date=dateFormat.format(d);
                //i.putExtra("date",date);

                startActivity(i);
                finish();
            }
        });
        btnViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent i = new Intent(CalculateActivity.this, ViewHistoryActivity.class);
                startActivity(i);

            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.website) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://" + "www.smartbmicalculator.com"));
            startActivity(i);

        }
        if (item.getItemId() == R.id.about) {
            Intent i=new Intent(CalculateActivity.this,aboutActivity.class);
            startActivity(i);
        }
        if (item.getItemId() == R.id.RateUs) {
            Intent i = new Intent(CalculateActivity.this, GenericShareActivity.class);
            startActivity(i);


        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(gac!=null)
            gac.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(gac!=null)
            gac.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        loc = LocationServices.FusedLocationApi.getLastLocation(gac);
        if (loc != null) {
            double lat = loc.getLatitude();
            double lon = loc.getLongitude();
            //tvLocation.setText(lat + " " + lon);
            Geocoder g = new Geocoder(this, Locale.ENGLISH);
            try {
                List<Address> la = g.getFromLocation(lat, lon, 1);
                android.location.Address add = la.get(0);
                String msg =  add.getLocality();
                tvLocation.setText(msg);
                String url="http://api.openweathermap.org/";
                String sp="data/2.5/weather?units=metric";
                String c=tvLocation.getText().toString();
                String qu="&q="+c;
                String id="b0bd5673322b460f4db21359eed55f32";
                String m=url+sp+qu+"&appid="+id;
                MyTask t=new MyTask();
                t.execute(m);

            } catch (IOException e) {


            }

        }


    }

    @Override
    public void onConnectionSuspended(int i) {


    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
    class MyTask extends AsyncTask<String,Void,Double> {
        @Override
        protected Double doInBackground(String... strings) {
            double temp=0.0;
            String line="";
            String json="";
            try {




                URL ur=new URL(strings[0]);
                HttpURLConnection con=(HttpURLConnection)ur.openConnection();
                con.connect();

                InputStreamReader isr=new InputStreamReader(con.getInputStream());
                BufferedReader br=new BufferedReader(isr);


                while ((line=br.readLine())!=null)
                {
                    json=json+line+"\n";
                }

                JSONObject O=new JSONObject(json);
                JSONObject p=O.getJSONObject("main");
                temp=p.getDouble("temp");

            } catch (Exception e) {}
            return temp;
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            tvWeather.setText("Temperature: "+aDouble);
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



