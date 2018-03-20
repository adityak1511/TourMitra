package com.irinnovative.onepagesigninsignup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import mehdi.sakout.fancybuttons.FancyButton;

public class BookTicketsActivity extends AppCompatActivity {

    String username;
    FancyButton bookTicket,myTicket;
    TextView hiUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_tickets);

        username=getIntent().getExtras().getString("username");

        bookTicket=(FancyButton) findViewById(R.id.bookticket);
        bookTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),DetailsActivity.class);
                Bundle b=new Bundle();
                b.putString("username",username);
                i.putExtras(b);
                startActivity(i);
            }
        });

        hiUser=(TextView)findViewById(R.id.hiuser);
        hiUser.setText("Hi "+ username.toUpperCase());

    }
}
