package com.example.hashisushi;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ActPromotion extends AppCompatActivity {

    private TextView txtTitle;
    private FloatingActionButton flotBntSalesCardap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_promotion);

        getSupportActionBar().hide();
        //Travæ rotaçãø da tela
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtTitle = findViewById(R.id.txtTitle);
        fontLogo();

        flotBntSalesCardap = findViewById(R.id.flotBntSalesCadap);

        flotBntSalesCardap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniActSaleCardp();
            }
        });

    }

    //Altera fonte do txtLogo
    private void fontLogo(){

        Typeface font = Typeface.createFromAsset(getAssets(), "RagingRedLotusBB.ttf");
        txtTitle.setTypeface(font);
    }


    public void iniActSaleCardp(){

            Intent it = new Intent(this, ActSaleCardap.class);
            startActivity(it);


    }
}
