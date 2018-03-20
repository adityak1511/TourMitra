package com.irinnovative.onepagesigninsignup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private boolean isSigninScreen = true;
    private TextView tvSignupInvoker;
    private LinearLayout llSignup;
    private TextView tvSigninInvoker;
    private LinearLayout llSignin;
    private Button btnSignup;
    private Button btnSignin;
    LinearLayout llsignin,llsignup;
    ProgressDialog pdialog ;
    Connection jParser = new Connection();
    TextView tv = null;
    String json = null;
    private EditText uname,fname,pwd,signinuname,signinpwd;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llSignin = (LinearLayout) findViewById(R.id.llSignin);
        llSignin.setOnClickListener(this);
        //LinearLayout singnin =(LinearLayout)findViewById(R.id.signin);
         llsignup =(LinearLayout)findViewById(R.id.llSignup);
        llsignup.setOnClickListener(this);
        tvSignupInvoker = (TextView) findViewById(R.id.tvSignupInvoker);
        tvSigninInvoker = (TextView) findViewById(R.id.tvSigninInvoker);

        btnSignup= (Button) findViewById(R.id.btnSignup);
        btnSignin= (Button) findViewById(R.id.btnSignin);

        llSignup = (LinearLayout) findViewById(R.id.llSignup);
        llSignin = (LinearLayout) findViewById(R.id.llSignin);

        image=(ImageView) findViewById(R.id.testimage);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent i=new Intent(MainActivity.this, DetailsActivity.class);
                //startActivity(i);
            }
        });

        tvSignupInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSigninScreen = false;
                showSignupForm();
            }
        });

        tvSigninInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSigninScreen = true;
                showSigninForm();
            }
        });
        showSigninForm();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname=(EditText)findViewById(R.id.fname);
                uname=(EditText)findViewById(R.id.uname);
                pwd=(EditText)findViewById(R.id.pwd);
                new signupserver().execute();
                Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_right_to_left);
                if(isSigninScreen)
                    btnSignup.startAnimation(clockwise);
            }
        });


        btnSignin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            signinuname=(EditText)findViewById(R.id.signinuname);
            signinpwd=(EditText)findViewById(R.id.signinpwd);
            new signinserver().execute();
        }
    });
}

    private void showSignupForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.15f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.85f;
        llSignup.requestLayout();

        tvSignupInvoker.setVisibility(View.GONE);
        tvSigninInvoker.setVisibility(View.VISIBLE);
        Animation translate= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_right_to_left);
        llSignup.startAnimation(translate);

        Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_right_to_left);
        btnSignup.startAnimation(clockwise);

    }

    private void showSigninForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.85f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.15f;
        llSignup.requestLayout();

        Animation translate= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_left_to_right);
        llSignin.startAnimation(translate);

        tvSignupInvoker.setVisibility(View.VISIBLE);
        tvSigninInvoker.setVisibility(View.GONE);
        Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_left_to_right);
        btnSignin.startAnimation(clockwise);
    }
 @Override
    public void onClick(View v) {
        if(v.getId() == R.id.llSignin || v.getId() ==R.id.llSignup){
           // Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
            InputMethodManager methodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            methodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        }

    }

    class signupserver extends AsyncTask<String,Void,Void> {

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
                params.put("name",String.valueOf(fname.getText()));
                params.put("username",String.valueOf(uname.getText()));
                params.put("password",String.valueOf(pwd.getText()));
                /*json = jParser.makeHttpRequest("http://10.42.0.1:3000/hello","GET",params);
                Log.d("json", String.valueOf(json));*/
                json = jParser.makeHttpRequest("http://10.42.0.1:3000/register","POST",params);
                Log.d("json", String.valueOf(json));

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }


    class signinserver extends AsyncTask<String,Void,Void> {

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
                    Log.d("json", String.valueOf(json));
                }
            });
        }

        @Override
        protected Void doInBackground(String... args) {

            try {
                HashMap<String,Object> params = new HashMap<>();
                //params.put("name",String.valueOf(fname.getText()));
                params.put("username",String.valueOf(signinuname.getText()));
                params.put("password",String.valueOf(signinpwd.getText()));
                Log.d("XXXX",String.valueOf(signinuname.getText()));
                /*json = jParser.makeHttpRequest("http://10.42.0.1:3000/hello","GET",params);
                Log.d("json", String.valueOf(json));*/
                json = jParser.makeHttpRequest("http://10.42.0.1:3000/login","POST",params);
                Log.d("json", String.valueOf(json));
                if(String.valueOf(json).equals("success")) {
                    Intent i=new Intent(getApplicationContext(),BookTicketsActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("username", String.valueOf(signinuname.getText()));
                    i.putExtras(mBundle);
                    //Toast.makeText(getApplicationContext(),String.valueOf(signinuname.getText()),Toast.LENGTH_SHORT).show();
                    startActivity(i);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }



    class test extends AsyncTask<String,Void,Void> {

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
                    Log.d("json", String.valueOf(json));
                }
            });
        }

        @Override
        protected Void doInBackground(String... args) {

            try {
                //Toast.makeText(getApplicationContext(),"Hi",Toast.LENGTH_SHORT).show();
                Log.d("XXXX","YES");
                HashMap<String,Object> params = new HashMap<>();
                //params.put("name",String.valueOf(fname.getText()));
                params.put("username",String.valueOf(signinuname.getText()));
                params.put("password",String.valueOf(signinpwd.getText()));
                /*json = jParser.makeHttpRequest("http://10.42.0.1:3000/hello","GET",params);
                Log.d("json", String.valueOf(json));*/
                json = jParser.makeHttpRequest("http://10.42.0.1:3000/hello","GET",params);
                Log.d("json", String.valueOf(json));
                if(String.valueOf(json).equals("success")) {
                    Intent i=new Intent(getApplicationContext(),DetailsActivity.class);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }


}
