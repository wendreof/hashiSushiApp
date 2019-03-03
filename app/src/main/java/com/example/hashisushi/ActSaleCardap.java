package com.example.hashisushi;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ActSaleCardap extends AppCompatActivity {

    private EditText edtSearchPlate;
    private TextView txtCardap;
    private ImageButton imgBtnSearchPlate;
    private FloatingActionButton flotBntHome;
    private FloatingActionButton flotBntScanQcode;
    private FloatingActionButton flotBntCarSales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sale_cardap);
        getSupportActionBar().hide();
        //Travæ rotaçãø da tela
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtCardap = findViewById(R.id.txtCardap);
        fontLogo();


    }
    //Altera fonte do txtLogo
    private void fontLogo(){

        Typeface font = Typeface.createFromAsset(getAssets(), "RagingRedLotusBB.ttf");
        txtCardap.setTypeface(font);
    }
}
