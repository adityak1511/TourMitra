package com.irinnovative.onepagesigninsignup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import mehdi.sakout.fancybuttons.FancyButton;

public class DetailsActivity extends AppCompatActivity {

    String[] SPINNERLIST = {"Jaipur","Udaipur"};
    Spinner mSprCountries;
    ImageButton stplus,stminus,adplus,adminus;
    TextView stcount,adcount;
    int stc,adc;
    mehdi.sakout.fancybuttons.FancyButton submit;
    String username,city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mSprCountries = (Spinner) findViewById(R.id.spr_country_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, SPINNERLIST);

        // Setting the array adapter containing country list to the spinner widget
        mSprCountries.setAdapter(adapter);

        city = SPINNERLIST[0];
        stc=0;
        adc=0;

        AdapterView.OnItemSelectedListener countrySelectedListener = new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> spinner, View container,
                                       int position, long id) {
                Toast.makeText(getApplicationContext(),SPINNERLIST[position],Toast.LENGTH_SHORT).show();

                city = SPINNERLIST[position];
                //mTvCountry.setText(countries[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        };

        // Setting ItemClick Handler for Spinner Widget
        mSprCountries.setOnItemSelectedListener(countrySelectedListener);

        stplus=(ImageButton)findViewById(R.id.stplus);
        stminus=(ImageButton)findViewById(R.id.stminus);
        adplus=(ImageButton)findViewById(R.id.adplus);
        adminus=(ImageButton)findViewById(R.id.adminus);
        stcount=(TextView) findViewById(R.id.stcount);
        adcount=(TextView) findViewById(R.id.adcount);

        stplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stc++;
                stcount.setText(String.valueOf(stc));
            }
        });

        stminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stc--;
                if(stc<0)
                    stc=0;
                stcount.setText(String.valueOf(stc));
            }
        });

        adplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adc++;
                adcount.setText(String.valueOf(adc));
            }
        });

        adminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adc--;
                if(adc<0)
                    adc=0;
                adcount.setText(String.valueOf(adc));
            }
        });

        username=getIntent().getExtras().getString("username");

        submit= (FancyButton) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),MonumentsActivity.class);
                Bundle b=new Bundle();
                b.putString("username",username);
                b.putString("city",city);
                b.putInt("stcount",stc);
                b.putInt("adcount",adc);
                i.putExtras(b);
                //Log.d("XXXXXXXX","YO MAN");
                //Toast.makeText(getApplicationContext(), username,Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });

    }
}
