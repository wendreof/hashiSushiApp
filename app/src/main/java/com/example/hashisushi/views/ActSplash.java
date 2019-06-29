package com.example.hashisushi.views;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;


import android.widget.ImageView;
import android.widget.TextView;

import com.example.hashisushi.R;

import com.google.firebase.auth.FirebaseAuth;

public class ActSplash extends AppCompatActivity {

    private TextView txtDelivery;
    private TextView txtwhats;
    private ImageView imgLogoS;
    private ImageView imgWhats;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_splash);

        getSupportActionBar().hide();

        //Travæ rotaçãø da tela
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initComponent();
        fontLogo();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               // startActivity(new Intent(getBaseContext(),ActLogin.class));
                testUserCurrent();
                finish();
            }
        },5000);

        this.auth = FirebaseAuth.getInstance();

     }
    //Altera fonte do txtLogo
    private void fontLogo()
    {

        Typeface font = Typeface.createFromAsset(getAssets(), "RagingRedLotusBB.ttf");
        txtDelivery.setTypeface(font);
        txtwhats.setTypeface(font);

        imgLogoS.setImageResource(R.drawable.lghashi);
        imgWhats.setImageResource(R.drawable.whats);

    }

    private void initComponent()
    {

        txtDelivery = findViewById(R.id.txtDelivery);
        txtwhats = findViewById(R.id.txtWhats);
        imgLogoS = findViewById(R.id.imgLogoS);
        imgWhats = findViewById(R.id.imgWhats);
    }

    //case user login  ok  actpromotion
    public void testUserCurrent(){

        if (auth.getCurrentUser() != null)
        {
            Intent it = new Intent(this, ActPromotion.class);
            startActivity(it);

        }
        else {

            Intent it = new Intent(this, ActLogin.class);
            startActivity(it);
        }

    }

}
