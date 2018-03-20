package com.irinnovative.onepagesigninsignup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import mehdi.sakout.fancybuttons.FancyButton;

public class MonumentsActivity extends AppCompatActivity {

    ArrayList dataModels;
    ListView listView;
    private CustomAdapter adapter;
    int jaipurPrice[][];
    int stcount,adcount;
    String city,username;
    int price;
    FancyButton pay;
    int monumentSelected[];
    String monuments;
    String jaipurMonuments[] = {"Hawa Mahal", "Amer Fort", "City Palace", "Jantar Mantar", "Jal Mahal"};
    LinearLayout llsignin,llsignup;
    ProgressDialog pdialog ;
    Connection jParser = new Connection();
    String json = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monuments);

        listView = (ListView) findViewById(R.id.listView);

        dataModels = new ArrayList();
        stcount=0;
        adcount=0;
        monumentSelected = new int[5];

        dataModels.add(new DataModel("Hawa Mahal", false,R.drawable.hawamahal));
        dataModels.add(new DataModel("Amer Fort", false,R.drawable.amerfort));
        dataModels.add(new DataModel("City Palace", false,R.drawable.citypalace));
        dataModels.add(new DataModel("Jantar Mantar", false,R.drawable.jantarmantar));
        dataModels.add(new DataModel("Jal Mahal", false,R.drawable.jalmahal));

        adapter = new CustomAdapter(dataModels, getApplicationContext());

        pay=(FancyButton) findViewById(R.id.paybtn);
        price=0;

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                DataModel dataModel= (DataModel) dataModels.get(position);
                if(dataModel.checked==true) {
                    price = price - stcount*jaipurPrice[position][1] - adcount*jaipurPrice[position][0];
                } else {
                    price = price + stcount*jaipurPrice[position][1] + adcount*jaipurPrice[position][0];
                }
                monumentSelected[position] = monumentSelected[position] ^ 1;
                pay.setText("PAY(Rs " + price + ")");
                dataModel.checked = !dataModel.checked;
                adapter.notifyDataSetChanged();
            }
        });

        jaipurPrice=new int[5][2];
        jaipurPrice[0][0]=50;
        jaipurPrice[0][1]=5;
        jaipurPrice[1][0]=40;
        jaipurPrice[1][1]=5;
        jaipurPrice[2][0]=60;
        jaipurPrice[2][1]=10;
        jaipurPrice[3][0]=70;
        jaipurPrice[3][1]=12;
        jaipurPrice[4][0]=50;
        jaipurPrice[4][1]=5;

        city=getIntent().getExtras().getString("city");
        stcount=getIntent().getExtras().getInt("stcount");
        adcount=getIntent().getExtras().getInt("adcount");
        username=getIntent().getExtras().getString("username");

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monuments="";
                for(int i=0;i<5;i++) {
                    if(monumentSelected[i]==1)
                        monuments=monuments+jaipurMonuments[i]+"#";
                }
                //Toast.makeText(getApplicationContext(),username,Toast.LENGTH_SHORT).show();
                new qrserver().execute();
            }
        });

    }

    class qrserver extends AsyncTask<String,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new ProgressDialog(getApplicationContext());
            pdialog.setMessage("Fetch Details");
            pdialog.setIndeterminate(false);
            pdialog.setCancelable(false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pdialog.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //tv.setText(json);
                }
            });
        }

        @Override
        protected Void doInBackground(String... args) {

            try {
                HashMap<String,Object> params = new HashMap<>();
                params.put("username",username);
                params.put("price",String.valueOf(price));
                params.put("monuments",monuments);
                params.put("stcount",String.valueOf(stcount));
                params.put("adcount",String.valueOf(adcount));
                /*json = jParser.makeHttpRequest("http://10.42.0.1:3000/hello","GET",params);
                Log.d("json", String.valueOf(json));*/
                json = jParser.makeHttpRequest("http://10.42.0.1:3000/ticket","POST",params);
                if(json!="") {
                    Intent i=new Intent(getApplicationContext(),QRActivity.class);
                    Bundle b=new Bundle();
                    b.putString("id",String.valueOf(json));
                    i.putExtras(b);
                    startActivity(i);
                }
                Log.d("json", String.valueOf(json));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }


}
