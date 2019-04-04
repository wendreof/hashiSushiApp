package com.example.hashisushi.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hashisushi.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActPoints extends AppCompatActivity
{

    private TextView txtLogo;
    private  TextView txtTitlePonts;
    private  TextView txtPonts;
    private FloatingActionButton flotBntScanQcodePont;
    private ImageView imgVw1,imgVw2,imgVw3,imgVw4,imgVw5,imgVw6,imgVw7,imgVw8;
    private ImageView imgVw9,imgVw10,imgVw11,imgVw12,imgVw13,imgVw14,imgVw15;
    private ImageView imgVw16,imgVw17,imgVw18,imgVw19,imgVw20,imgVw21,imgVw22;
    private ImageView imgVw23,imgVw24,imgVw25;

    private int pontos;


    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.act_points);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        final Activity actScanCod = this;

        txtTitlePonts = findViewById(R.id.txtTitlePonts);
        txtPonts = findViewById(R.id.txtPonts);
        txtLogo = findViewById(R.id.txtLogo);

        pontos = 10;
        imgView();
        controlImgView();
        controlPonts();

        fontLogo();

        flotBntScanQcodePont = findViewById(R.id.flotBntScanQcodePont);
        flotBntScanQcodePont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanerCode(actScanCod);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    //Altera fonte do txtLogo
    private void fontLogo(){

        Typeface font = Typeface.createFromAsset(getAssets(), "RagingRedLotusBB.ttf");
        txtLogo.setTypeface(font);
        txtTitlePonts.setTypeface( font );
    }

    //Implemention Scan
    public void scanerCode(Activity activity){
        IntentIntegrator integrator = new IntentIntegrator(activity);

        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scaneando codigo...");
        integrator.setCameraId(0);
        integrator.initiateScan();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode , Intent data ){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(result != null){
            if(result.getContents() != null){
                alerta("Codigo recebido :"+ result.getContents());
                String codigo = result.getContents();
                pontos++;

                String p = String.valueOf(pontos);
                txtPonts.setText(p);
                controlPonts();

                if(pontos >25){
                    pontos = 0;
                }
            }else {
                alerta("Scan cancelado!");
            }

        }else {
            super.onActivityResult(requestCode,resultCode,data);
        }

    }



    public void alerta(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    public void imgView() {

        imgVw1 = findViewById(R.id.imgVw1);
        imgVw2 = findViewById(R.id.imgVw2);
        imgVw3 = findViewById(R.id.imgVw3);
        imgVw4 = findViewById(R.id.imgVw4);
        imgVw5 = findViewById(R.id.imgVw5);
        imgVw6 = findViewById(R.id.imgVw6);
        imgVw7 = findViewById(R.id.imgVw7);
        imgVw8 = findViewById(R.id.imgVw8);
        imgVw9 = findViewById(R.id.imgVw9);
        imgVw10 = findViewById(R.id.imgVw10);
        imgVw11 = findViewById(R.id.imgVw11);
        imgVw12 = findViewById(R.id.imgVw12);
        imgVw13 = findViewById(R.id.imgVw13);
        imgVw14 = findViewById(R.id.imgVw14);
        imgVw15 = findViewById(R.id.imgVw15);
        imgVw16 = findViewById(R.id.imgVw16);
        imgVw17 = findViewById(R.id.imgVw17);
        imgVw18 = findViewById(R.id.imgVw18);
        imgVw19 = findViewById(R.id.imgVw19);
        imgVw20 = findViewById(R.id.imgVw20);
        imgVw21 = findViewById(R.id.imgVw21);
        imgVw22 = findViewById(R.id.imgVw22);
        imgVw23 = findViewById(R.id.imgVw23);
        imgVw24 = findViewById(R.id.imgVw24);
        imgVw25 = findViewById(R.id.imgVw25);
    }

    public void controlImgView(){

        if( pontos <= 0){

            imgVw1.setVisibility(View.INVISIBLE);
            imgVw2.setVisibility(View.INVISIBLE);
            imgVw3.setVisibility(View.INVISIBLE);
            imgVw4.setVisibility(View.INVISIBLE);
            imgVw5.setVisibility(View.INVISIBLE);
            imgVw6.setVisibility(View.INVISIBLE);
            imgVw7.setVisibility(View.INVISIBLE);
            imgVw8.setVisibility(View.INVISIBLE);
            imgVw9.setVisibility(View.INVISIBLE);
            imgVw10.setVisibility(View.INVISIBLE);
            imgVw11.setVisibility(View.INVISIBLE);
            imgVw12.setVisibility(View.INVISIBLE);
            imgVw13.setVisibility(View.INVISIBLE);
            imgVw14.setVisibility(View.INVISIBLE);
            imgVw15.setVisibility(View.INVISIBLE);
            imgVw16.setVisibility(View.INVISIBLE);
            imgVw17.setVisibility(View.INVISIBLE);
            imgVw18.setVisibility(View.INVISIBLE);
            imgVw19.setVisibility(View.INVISIBLE);
            imgVw20.setVisibility(View.INVISIBLE);
            imgVw21.setVisibility(View.INVISIBLE);
            imgVw22.setVisibility(View.INVISIBLE);
            imgVw23.setVisibility(View.INVISIBLE);
            imgVw24.setVisibility(View.INVISIBLE);
            imgVw25.setVisibility(View.INVISIBLE);
        }


    }

    private void controlPonts(){

        if (pontos == 1 ) {
            imgVw1.setVisibility(View.VISIBLE);

            imgVw2.setVisibility(View.INVISIBLE);
            imgVw3.setVisibility(View.INVISIBLE);
            imgVw4.setVisibility(View.INVISIBLE);
            imgVw5.setVisibility(View.INVISIBLE);
            imgVw6.setVisibility(View.INVISIBLE);
            imgVw7.setVisibility(View.INVISIBLE);
            imgVw8.setVisibility(View.INVISIBLE);
            imgVw9.setVisibility(View.INVISIBLE);
            imgVw10.setVisibility(View.INVISIBLE);
            imgVw11.setVisibility(View.INVISIBLE);
            imgVw12.setVisibility(View.INVISIBLE);
            imgVw13.setVisibility(View.INVISIBLE);
            imgVw14.setVisibility(View.INVISIBLE);
            imgVw15.setVisibility(View.INVISIBLE);
            imgVw16.setVisibility(View.INVISIBLE);
            imgVw17.setVisibility(View.INVISIBLE);
            imgVw18.setVisibility(View.INVISIBLE);
            imgVw19.setVisibility(View.INVISIBLE);
            imgVw20.setVisibility(View.INVISIBLE);
            imgVw21.setVisibility(View.INVISIBLE);
            imgVw22.setVisibility(View.INVISIBLE);
            imgVw23.setVisibility(View.INVISIBLE);
            imgVw24.setVisibility(View.INVISIBLE);
            imgVw25.setVisibility(View.INVISIBLE);

        }if(pontos == 2) {
            imgVw2.setVisibility(View.VISIBLE);
            imgVw1.setVisibility(View.VISIBLE);

            imgVw3.setVisibility(View.INVISIBLE);
            imgVw4.setVisibility(View.INVISIBLE);
            imgVw5.setVisibility(View.INVISIBLE);
            imgVw6.setVisibility(View.INVISIBLE);
            imgVw7.setVisibility(View.INVISIBLE);
            imgVw8.setVisibility(View.INVISIBLE);
            imgVw9.setVisibility(View.INVISIBLE);
            imgVw10.setVisibility(View.INVISIBLE);
            imgVw11.setVisibility(View.INVISIBLE);
            imgVw12.setVisibility(View.INVISIBLE);
            imgVw13.setVisibility(View.INVISIBLE);
            imgVw14.setVisibility(View.INVISIBLE);
            imgVw15.setVisibility(View.INVISIBLE);
            imgVw16.setVisibility(View.INVISIBLE);
            imgVw17.setVisibility(View.INVISIBLE);
            imgVw18.setVisibility(View.INVISIBLE);
            imgVw19.setVisibility(View.INVISIBLE);
            imgVw20.setVisibility(View.INVISIBLE);
            imgVw21.setVisibility(View.INVISIBLE);
            imgVw22.setVisibility(View.INVISIBLE);
            imgVw23.setVisibility(View.INVISIBLE);
            imgVw24.setVisibility(View.INVISIBLE);
            imgVw25.setVisibility(View.INVISIBLE);

        }if(pontos == 3) {
            imgVw3.setVisibility(View.VISIBLE);

            imgVw1.setVisibility(View.VISIBLE);
            imgVw2.setVisibility(View.VISIBLE);

            imgVw4.setVisibility(View.INVISIBLE);
            imgVw5.setVisibility(View.INVISIBLE);
            imgVw6.setVisibility(View.INVISIBLE);
            imgVw7.setVisibility(View.INVISIBLE);
            imgVw8.setVisibility(View.INVISIBLE);
            imgVw9.setVisibility(View.INVISIBLE);
            imgVw10.setVisibility(View.INVISIBLE);
            imgVw11.setVisibility(View.INVISIBLE);
            imgVw12.setVisibility(View.INVISIBLE);
            imgVw13.setVisibility(View.INVISIBLE);
            imgVw14.setVisibility(View.INVISIBLE);
            imgVw15.setVisibility(View.INVISIBLE);
            imgVw16.setVisibility(View.INVISIBLE);
            imgVw17.setVisibility(View.INVISIBLE);
            imgVw18.setVisibility(View.INVISIBLE);
            imgVw19.setVisibility(View.INVISIBLE);
            imgVw20.setVisibility(View.INVISIBLE);
            imgVw21.setVisibility(View.INVISIBLE);
            imgVw22.setVisibility(View.INVISIBLE);
            imgVw23.setVisibility(View.INVISIBLE);
            imgVw24.setVisibility(View.INVISIBLE);
            imgVw25.setVisibility(View.INVISIBLE);

        }if(pontos == 4) {
            imgVw4.setVisibility(View.VISIBLE);

            imgVw1.setVisibility(View.VISIBLE);
            imgVw2.setVisibility(View.VISIBLE);
            imgVw3.setVisibility(View.VISIBLE);

            imgVw5.setVisibility(View.INVISIBLE);
            imgVw6.setVisibility(View.INVISIBLE);
            imgVw7.setVisibility(View.INVISIBLE);
            imgVw8.setVisibility(View.INVISIBLE);
            imgVw9.setVisibility(View.INVISIBLE);
            imgVw10.setVisibility(View.INVISIBLE);
            imgVw11.setVisibility(View.INVISIBLE);
            imgVw12.setVisibility(View.INVISIBLE);
            imgVw13.setVisibility(View.INVISIBLE);
            imgVw14.setVisibility(View.INVISIBLE);
            imgVw15.setVisibility(View.INVISIBLE);
            imgVw16.setVisibility(View.INVISIBLE);
            imgVw17.setVisibility(View.INVISIBLE);
            imgVw18.setVisibility(View.INVISIBLE);
            imgVw19.setVisibility(View.INVISIBLE);
            imgVw20.setVisibility(View.INVISIBLE);
            imgVw21.setVisibility(View.INVISIBLE);
            imgVw22.setVisibility(View.INVISIBLE);
            imgVw23.setVisibility(View.INVISIBLE);
            imgVw24.setVisibility(View.INVISIBLE);
            imgVw25.setVisibility(View.INVISIBLE);

        }if(pontos == 5) {
            imgVw5.setVisibility(View.VISIBLE);

            imgVw1.setVisibility(View.VISIBLE);
            imgVw2.setVisibility(View.VISIBLE);
            imgVw3.setVisibility(View.VISIBLE);
            imgVw4.setVisibility(View.VISIBLE);

            imgVw6.setVisibility(View.INVISIBLE);
            imgVw7.setVisibility(View.INVISIBLE);
            imgVw8.setVisibility(View.INVISIBLE);
            imgVw9.setVisibility(View.INVISIBLE);
            imgVw10.setVisibility(View.INVISIBLE);
            imgVw11.setVisibility(View.INVISIBLE);
            imgVw12.setVisibility(View.INVISIBLE);
            imgVw13.setVisibility(View.INVISIBLE);
            imgVw14.setVisibility(View.INVISIBLE);
            imgVw15.setVisibility(View.INVISIBLE);
            imgVw16.setVisibility(View.INVISIBLE);
            imgVw17.setVisibility(View.INVISIBLE);
            imgVw18.setVisibility(View.INVISIBLE);
            imgVw19.setVisibility(View.INVISIBLE);
            imgVw20.setVisibility(View.INVISIBLE);
            imgVw21.setVisibility(View.INVISIBLE);
            imgVw22.setVisibility(View.INVISIBLE);
            imgVw23.setVisibility(View.INVISIBLE);
            imgVw24.setVisibility(View.INVISIBLE);
            imgVw25.setVisibility(View.INVISIBLE);

        }if(pontos == 6) {
            imgVw6.setVisibility(View.VISIBLE);

            imgVw1.setVisibility(View.VISIBLE);
            imgVw2.setVisibility(View.VISIBLE);
            imgVw3.setVisibility(View.VISIBLE);
            imgVw4.setVisibility(View.VISIBLE);
            imgVw5.setVisibility(View.VISIBLE);

            imgVw7.setVisibility(View.INVISIBLE);
            imgVw8.setVisibility(View.INVISIBLE);
            imgVw9.setVisibility(View.INVISIBLE);
            imgVw10.setVisibility(View.INVISIBLE);
            imgVw11.setVisibility(View.INVISIBLE);
            imgVw12.setVisibility(View.INVISIBLE);
            imgVw13.setVisibility(View.INVISIBLE);
            imgVw14.setVisibility(View.INVISIBLE);
            imgVw15.setVisibility(View.INVISIBLE);
            imgVw16.setVisibility(View.INVISIBLE);
            imgVw17.setVisibility(View.INVISIBLE);
            imgVw18.setVisibility(View.INVISIBLE);
            imgVw19.setVisibility(View.INVISIBLE);
            imgVw20.setVisibility(View.INVISIBLE);
            imgVw21.setVisibility(View.INVISIBLE);
            imgVw22.setVisibility(View.INVISIBLE);
            imgVw23.setVisibility(View.INVISIBLE);
            imgVw24.setVisibility(View.INVISIBLE);
            imgVw25.setVisibility(View.INVISIBLE);
        }if(pontos == 7) {
            imgVw7.setVisibility(View.VISIBLE);

            imgVw1.setVisibility(View.VISIBLE);
            imgVw2.setVisibility(View.VISIBLE);
            imgVw3.setVisibility(View.VISIBLE);
            imgVw4.setVisibility(View.VISIBLE);
            imgVw5.setVisibility(View.VISIBLE);
            imgVw6.setVisibility(View.VISIBLE);

            imgVw8.setVisibility(View.INVISIBLE);
            imgVw9.setVisibility(View.INVISIBLE);
            imgVw10.setVisibility(View.INVISIBLE);
            imgVw11.setVisibility(View.INVISIBLE);
            imgVw12.setVisibility(View.INVISIBLE);
            imgVw13.setVisibility(View.INVISIBLE);
            imgVw14.setVisibility(View.INVISIBLE);
            imgVw15.setVisibility(View.INVISIBLE);
            imgVw16.setVisibility(View.INVISIBLE);
            imgVw17.setVisibility(View.INVISIBLE);
            imgVw18.setVisibility(View.INVISIBLE);
            imgVw19.setVisibility(View.INVISIBLE);
            imgVw20.setVisibility(View.INVISIBLE);
            imgVw21.setVisibility(View.INVISIBLE);
            imgVw22.setVisibility(View.INVISIBLE);
            imgVw23.setVisibility(View.INVISIBLE);
            imgVw24.setVisibility(View.INVISIBLE);
            imgVw25.setVisibility(View.INVISIBLE);

        }if(pontos == 8) {
            imgVw8.setVisibility(View.VISIBLE);

            imgVw1.setVisibility(View.VISIBLE);
            imgVw2.setVisibility(View.VISIBLE);
            imgVw3.setVisibility(View.VISIBLE);
            imgVw4.setVisibility(View.VISIBLE);
            imgVw5.setVisibility(View.VISIBLE);
            imgVw6.setVisibility(View.VISIBLE);
            imgVw7.setVisibility(View.VISIBLE);

            imgVw9.setVisibility(View.INVISIBLE);
            imgVw10.setVisibility(View.INVISIBLE);
            imgVw11.setVisibility(View.INVISIBLE);
            imgVw12.setVisibility(View.INVISIBLE);
            imgVw13.setVisibility(View.INVISIBLE);
            imgVw14.setVisibility(View.INVISIBLE);
            imgVw15.setVisibility(View.INVISIBLE);
            imgVw16.setVisibility(View.INVISIBLE);
            imgVw17.setVisibility(View.INVISIBLE);
            imgVw18.setVisibility(View.INVISIBLE);
            imgVw19.setVisibility(View.INVISIBLE);
            imgVw20.setVisibility(View.INVISIBLE);
            imgVw21.setVisibility(View.INVISIBLE);
            imgVw22.setVisibility(View.INVISIBLE);
            imgVw23.setVisibility(View.INVISIBLE);
            imgVw24.setVisibility(View.INVISIBLE);
            imgVw25.setVisibility(View.INVISIBLE);

        }if (pontos == 9 ) {
            imgVw9.setVisibility(View.VISIBLE);

            imgVw1.setVisibility(View.VISIBLE);
            imgVw2.setVisibility(View.VISIBLE);
            imgVw3.setVisibility(View.VISIBLE);
            imgVw4.setVisibility(View.VISIBLE);
            imgVw5.setVisibility(View.VISIBLE);
            imgVw6.setVisibility(View.VISIBLE);
            imgVw7.setVisibility(View.VISIBLE);
            imgVw8.setVisibility(View.VISIBLE);

            imgVw10.setVisibility(View.INVISIBLE);
            imgVw11.setVisibility(View.INVISIBLE);
            imgVw12.setVisibility(View.INVISIBLE);
            imgVw13.setVisibility(View.INVISIBLE);
            imgVw14.setVisibility(View.INVISIBLE);
            imgVw15.setVisibility(View.INVISIBLE);
            imgVw16.setVisibility(View.INVISIBLE);
            imgVw17.setVisibility(View.INVISIBLE);
            imgVw18.setVisibility(View.INVISIBLE);
            imgVw19.setVisibility(View.INVISIBLE);
            imgVw20.setVisibility(View.INVISIBLE);
            imgVw21.setVisibility(View.INVISIBLE);
            imgVw22.setVisibility(View.INVISIBLE);
            imgVw23.setVisibility(View.INVISIBLE);
            imgVw24.setVisibility(View.INVISIBLE);
            imgVw25.setVisibility(View.INVISIBLE);

        }if(pontos == 10) {
            imgVw10.setVisibility(View.VISIBLE);

            imgVw1.setVisibility(View.VISIBLE);
            imgVw2.setVisibility(View.VISIBLE);
            imgVw3.setVisibility(View.VISIBLE);
            imgVw4.setVisibility(View.VISIBLE);
            imgVw5.setVisibility(View.VISIBLE);
            imgVw6.setVisibility(View.VISIBLE);
            imgVw7.setVisibility(View.VISIBLE);
            imgVw8.setVisibility(View.VISIBLE);
            imgVw9.setVisibility(View.VISIBLE);

            imgVw11.setVisibility(View.INVISIBLE);
            imgVw12.setVisibility(View.INVISIBLE);
            imgVw13.setVisibility(View.INVISIBLE);
            imgVw14.setVisibility(View.INVISIBLE);
            imgVw15.setVisibility(View.INVISIBLE);
            imgVw16.setVisibility(View.INVISIBLE);
            imgVw17.setVisibility(View.INVISIBLE);
            imgVw18.setVisibility(View.INVISIBLE);
            imgVw19.setVisibility(View.INVISIBLE);
            imgVw20.setVisibility(View.INVISIBLE);
            imgVw21.setVisibility(View.INVISIBLE);
            imgVw22.setVisibility(View.INVISIBLE);
            imgVw23.setVisibility(View.INVISIBLE);
            imgVw24.setVisibility(View.INVISIBLE);
            imgVw25.setVisibility(View.INVISIBLE);

        }if(pontos == 11) {
            imgVw11.setVisibility(View.VISIBLE);

            imgVw1.setVisibility(View.VISIBLE);
            imgVw2.setVisibility(View.VISIBLE);
            imgVw3.setVisibility(View.VISIBLE);
            imgVw4.setVisibility(View.VISIBLE);
            imgVw5.setVisibility(View.VISIBLE);
            imgVw6.setVisibility(View.VISIBLE);
            imgVw7.setVisibility(View.VISIBLE);
            imgVw8.setVisibility(View.VISIBLE);
            imgVw9.setVisibility(View.VISIBLE);
            imgVw10.setVisibility(View.VISIBLE);

            imgVw12.setVisibility(View.INVISIBLE);
            imgVw13.setVisibility(View.INVISIBLE);
            imgVw14.setVisibility(View.INVISIBLE);
            imgVw15.setVisibility(View.INVISIBLE);
            imgVw16.setVisibility(View.INVISIBLE);
            imgVw17.setVisibility(View.INVISIBLE);
            imgVw18.setVisibility(View.INVISIBLE);
            imgVw19.setVisibility(View.INVISIBLE);
            imgVw20.setVisibility(View.INVISIBLE);
            imgVw21.setVisibility(View.INVISIBLE);
            imgVw22.setVisibility(View.INVISIBLE);
            imgVw23.setVisibility(View.INVISIBLE);
            imgVw24.setVisibility(View.INVISIBLE);
            imgVw25.setVisibility(View.INVISIBLE);

        }if(pontos == 12) {
            imgVw12.setVisibility(View.VISIBLE);

            imgVw1.setVisibility(View.VISIBLE);
            imgVw2.setVisibility(View.VISIBLE);
            imgVw3.setVisibility(View.VISIBLE);
            imgVw4.setVisibility(View.VISIBLE);
            imgVw5.setVisibility(View.VISIBLE);
            imgVw6.setVisibility(View.VISIBLE);
            imgVw7.setVisibility(View.VISIBLE);
            imgVw8.setVisibility(View.VISIBLE);
            imgVw9.setVisibility(View.VISIBLE);
            imgVw10.setVisibility(View.VISIBLE);
            imgVw11.setVisibility(View.VISIBLE);

            imgVw13.setVisibility(View.INVISIBLE);
            imgVw14.setVisibility(View.INVISIBLE);
            imgVw15.setVisibility(View.INVISIBLE);
            imgVw16.setVisibility(View.INVISIBLE);
            imgVw17.setVisibility(View.INVISIBLE);
            imgVw18.setVisibility(View.INVISIBLE);
            imgVw19.setVisibility(View.INVISIBLE);
            imgVw20.setVisibility(View.INVISIBLE);
            imgVw21.setVisibility(View.INVISIBLE);
            imgVw22.setVisibility(View.INVISIBLE);
            imgVw23.setVisibility(View.INVISIBLE);
            imgVw24.setVisibility(View.INVISIBLE);
            imgVw25.setVisibility(View.INVISIBLE);

        }if (pontos == 13 ) {
            imgVw13.setVisibility(View.VISIBLE);

            imgVw1.setVisibility(View.VISIBLE);
            imgVw2.setVisibility(View.VISIBLE);
            imgVw3.setVisibility(View.VISIBLE);
            imgVw4.setVisibility(View.VISIBLE);
            imgVw5.setVisibility(View.VISIBLE);
            imgVw6.setVisibility(View.VISIBLE);
            imgVw7.setVisibility(View.VISIBLE);
            imgVw8.setVisibility(View.VISIBLE);
            imgVw9.setVisibility(View.VISIBLE);
            imgVw10.setVisibility(View.VISIBLE);
            imgVw11.setVisibility(View.VISIBLE);
            imgVw12.setVisibility(View.VISIBLE);

            imgVw14.setVisibility(View.INVISIBLE);
            imgVw15.setVisibility(View.INVISIBLE);
            imgVw16.setVisibility(View.INVISIBLE);
            imgVw17.setVisibility(View.INVISIBLE);
            imgVw18.setVisibility(View.INVISIBLE);
            imgVw19.setVisibility(View.INVISIBLE);
            imgVw20.setVisibility(View.INVISIBLE);
            imgVw21.setVisibility(View.INVISIBLE);
            imgVw22.setVisibility(View.INVISIBLE);
            imgVw23.setVisibility(View.INVISIBLE);
            imgVw24.setVisibility(View.INVISIBLE);
            imgVw25.setVisibility(View.INVISIBLE);

        }if(pontos == 14) {
            imgVw14.setVisibility(View.VISIBLE);

            imgVw1.setVisibility(View.VISIBLE);
            imgVw2.setVisibility(View.VISIBLE);
            imgVw3.setVisibility(View.VISIBLE);
            imgVw4.setVisibility(View.VISIBLE);
            imgVw5.setVisibility(View.VISIBLE);
            imgVw6.setVisibility(View.VISIBLE);
            imgVw7.setVisibility(View.VISIBLE);
            imgVw8.setVisibility(View.VISIBLE);
            imgVw9.setVisibility(View.VISIBLE);
            imgVw10.setVisibility(View.VISIBLE);
            imgVw11.setVisibility(View.VISIBLE);
            imgVw12.setVisibility(View.VISIBLE);
            imgVw13.setVisibility(View.VISIBLE);

            imgVw15.setVisibility(View.INVISIBLE);
            imgVw16.setVisibility(View.INVISIBLE);
            imgVw17.setVisibility(View.INVISIBLE);
            imgVw18.setVisibility(View.INVISIBLE);
            imgVw19.setVisibility(View.INVISIBLE);
            imgVw20.setVisibility(View.INVISIBLE);
            imgVw21.setVisibility(View.INVISIBLE);
            imgVw22.setVisibility(View.INVISIBLE);
            imgVw23.setVisibility(View.INVISIBLE);
            imgVw24.setVisibility(View.INVISIBLE);
            imgVw25.setVisibility(View.INVISIBLE);

        }if(pontos == 15) {
            imgVw15.setVisibility(View.VISIBLE);

            imgVw1.setVisibility(View.VISIBLE);
            imgVw2.setVisibility(View.VISIBLE);
            imgVw3.setVisibility(View.VISIBLE);
            imgVw4.setVisibility(View.VISIBLE);
            imgVw5.setVisibility(View.VISIBLE);
            imgVw6.setVisibility(View.VISIBLE);
            imgVw7.setVisibility(View.VISIBLE);
            imgVw8.setVisibility(View.VISIBLE);
            imgVw9.setVisibility(View.VISIBLE);
            imgVw10.setVisibility(View.VISIBLE);
            imgVw11.setVisibility(View.VISIBLE);
            imgVw12.setVisibility(View.VISIBLE);
            imgVw13.setVisibility(View.VISIBLE);
            imgVw14.setVisibility(View.VISIBLE);

            imgVw16.setVisibility(View.INVISIBLE);
            imgVw17.setVisibility(View.INVISIBLE);
            imgVw18.setVisibility(View.INVISIBLE);
            imgVw19.setVisibility(View.INVISIBLE);
            imgVw20.setVisibility(View.INVISIBLE);
            imgVw21.setVisibility(View.INVISIBLE);
            imgVw22.setVisibility(View.INVISIBLE);
            imgVw23.setVisibility(View.INVISIBLE);
            imgVw24.setVisibility(View.INVISIBLE);
            imgVw25.setVisibility(View.INVISIBLE);

        }if(pontos == 16) {
            imgVw16.setVisibility(View.VISIBLE);

            imgVw1.setVisibility(View.VISIBLE);
            imgVw2.setVisibility(View.VISIBLE);
            imgVw3.setVisibility(View.VISIBLE);
            imgVw4.setVisibility(View.VISIBLE);
            imgVw5.setVisibility(View.VISIBLE);
            imgVw6.setVisibility(View.VISIBLE);
            imgVw7.setVisibility(View.VISIBLE);
            imgVw8.setVisibility(View.VISIBLE);
            imgVw9.setVisibility(View.VISIBLE);
            imgVw10.setVisibility(View.VISIBLE);
            imgVw11.setVisibility(View.VISIBLE);
            imgVw12.setVisibility(View.VISIBLE);
            imgVw13.setVisibility(View.VISIBLE);
            imgVw14.setVisibility(View.VISIBLE);
            imgVw15.setVisibility(View.VISIBLE);

            imgVw17.setVisibility(View.INVISIBLE);
            imgVw18.setVisibility(View.INVISIBLE);
            imgVw19.setVisibility(View.INVISIBLE);
            imgVw20.setVisibility(View.INVISIBLE);
            imgVw21.setVisibility(View.INVISIBLE);
            imgVw22.setVisibility(View.INVISIBLE);
            imgVw23.setVisibility(View.INVISIBLE);
            imgVw24.setVisibility(View.INVISIBLE);
            imgVw25.setVisibility(View.INVISIBLE);

        }if (pontos == 17 ) {
            imgVw17.setVisibility(View.VISIBLE);

            imgVw1.setVisibility(View.VISIBLE);
            imgVw2.setVisibility(View.VISIBLE);
            imgVw3.setVisibility(View.VISIBLE);
            imgVw4.setVisibility(View.VISIBLE);
            imgVw5.setVisibility(View.VISIBLE);
            imgVw6.setVisibility(View.VISIBLE);
            imgVw7.setVisibility(View.VISIBLE);
            imgVw8.setVisibility(View.VISIBLE);
            imgVw9.setVisibility(View.VISIBLE);
            imgVw10.setVisibility(View.VISIBLE);
            imgVw11.setVisibility(View.VISIBLE);
            imgVw12.setVisibility(View.VISIBLE);
            imgVw13.setVisibility(View.VISIBLE);
            imgVw14.setVisibility(View.VISIBLE);
            imgVw15.setVisibility(View.VISIBLE);
            imgVw16.setVisibility(View.VISIBLE);

            imgVw18.setVisibility(View.INVISIBLE);
            imgVw19.setVisibility(View.INVISIBLE);
            imgVw20.setVisibility(View.INVISIBLE);
            imgVw21.setVisibility(View.INVISIBLE);
            imgVw22.setVisibility(View.INVISIBLE);
            imgVw23.setVisibility(View.INVISIBLE);
            imgVw24.setVisibility(View.INVISIBLE);
            imgVw25.setVisibility(View.INVISIBLE);

        }if(pontos == 18) {
            imgVw18.setVisibility(View.VISIBLE);

            imgVw1.setVisibility(View.VISIBLE);
            imgVw2.setVisibility(View.VISIBLE);
            imgVw3.setVisibility(View.VISIBLE);
            imgVw4.setVisibility(View.VISIBLE);
            imgVw5.setVisibility(View.VISIBLE);
            imgVw6.setVisibility(View.VISIBLE);
            imgVw7.setVisibility(View.VISIBLE);
            imgVw8.setVisibility(View.VISIBLE);
            imgVw9.setVisibility(View.VISIBLE);
            imgVw10.setVisibility(View.VISIBLE);
            imgVw11.setVisibility(View.VISIBLE);
            imgVw12.setVisibility(View.VISIBLE);
            imgVw13.setVisibility(View.VISIBLE);
            imgVw14.setVisibility(View.VISIBLE);
            imgVw15.setVisibility(View.VISIBLE);
            imgVw16.setVisibility(View.VISIBLE);
            imgVw17.setVisibility(View.VISIBLE);

            imgVw18.setVisibility(View.INVISIBLE);
            imgVw19.setVisibility(View.INVISIBLE);
            imgVw20.setVisibility(View.INVISIBLE);
            imgVw21.setVisibility(View.INVISIBLE);
            imgVw22.setVisibility(View.INVISIBLE);
            imgVw23.setVisibility(View.INVISIBLE);
            imgVw24.setVisibility(View.INVISIBLE);
            imgVw25.setVisibility(View.INVISIBLE);

        }if(pontos == 19) {
            imgVw19.setVisibility(View.VISIBLE);

            imgVw1.setVisibility(View.VISIBLE);
            imgVw2.setVisibility(View.VISIBLE);
            imgVw3.setVisibility(View.VISIBLE);
            imgVw4.setVisibility(View.VISIBLE);
            imgVw5.setVisibility(View.VISIBLE);
            imgVw6.setVisibility(View.VISIBLE);
            imgVw7.setVisibility(View.VISIBLE);
            imgVw8.setVisibility(View.VISIBLE);
            imgVw9.setVisibility(View.VISIBLE);
            imgVw10.setVisibility(View.VISIBLE);
            imgVw11.setVisibility(View.VISIBLE);
            imgVw12.setVisibility(View.VISIBLE);
            imgVw13.setVisibility(View.VISIBLE);
            imgVw14.setVisibility(View.VISIBLE);
            imgVw15.setVisibility(View.VISIBLE);
            imgVw16.setVisibility(View.VISIBLE);
            imgVw17.setVisibility(View.VISIBLE);
            imgVw18.setVisibility(View.VISIBLE);

            imgVw20.setVisibility(View.INVISIBLE);
            imgVw21.setVisibility(View.INVISIBLE);
            imgVw22.setVisibility(View.INVISIBLE);
            imgVw23.setVisibility(View.INVISIBLE);
            imgVw24.setVisibility(View.INVISIBLE);
            imgVw25.setVisibility(View.INVISIBLE);

        }if(pontos == 20) {
            imgVw20.setVisibility(View.VISIBLE);

            imgVw1.setVisibility(View.VISIBLE);
            imgVw2.setVisibility(View.VISIBLE);
            imgVw3.setVisibility(View.VISIBLE);
            imgVw4.setVisibility(View.VISIBLE);
            imgVw5.setVisibility(View.VISIBLE);
            imgVw6.setVisibility(View.VISIBLE);
            imgVw7.setVisibility(View.VISIBLE);
            imgVw8.setVisibility(View.VISIBLE);
            imgVw9.setVisibility(View.VISIBLE);
            imgVw10.setVisibility(View.VISIBLE);
            imgVw11.setVisibility(View.VISIBLE);
            imgVw12.setVisibility(View.VISIBLE);
            imgVw13.setVisibility(View.VISIBLE);
            imgVw14.setVisibility(View.VISIBLE);
            imgVw15.setVisibility(View.VISIBLE);
            imgVw16.setVisibility(View.VISIBLE);
            imgVw17.setVisibility(View.VISIBLE);
            imgVw18.setVisibility(View.VISIBLE);
            imgVw19.setVisibility(View.VISIBLE);

            imgVw21.setVisibility(View.INVISIBLE);
            imgVw22.setVisibility(View.INVISIBLE);
            imgVw23.setVisibility(View.INVISIBLE);
            imgVw24.setVisibility(View.INVISIBLE);
            imgVw25.setVisibility(View.INVISIBLE);

        }if (pontos == 21 ) {
            imgVw21.setVisibility(View.VISIBLE);

            imgVw1.setVisibility(View.VISIBLE);
            imgVw2.setVisibility(View.VISIBLE);
            imgVw3.setVisibility(View.VISIBLE);
            imgVw4.setVisibility(View.VISIBLE);
            imgVw5.setVisibility(View.VISIBLE);
            imgVw6.setVisibility(View.VISIBLE);
            imgVw7.setVisibility(View.VISIBLE);
            imgVw8.setVisibility(View.VISIBLE);
            imgVw9.setVisibility(View.VISIBLE);
            imgVw10.setVisibility(View.VISIBLE);
            imgVw11.setVisibility(View.VISIBLE);
            imgVw12.setVisibility(View.VISIBLE);
            imgVw13.setVisibility(View.VISIBLE);
            imgVw14.setVisibility(View.VISIBLE);
            imgVw15.setVisibility(View.VISIBLE);
            imgVw16.setVisibility(View.VISIBLE);
            imgVw17.setVisibility(View.VISIBLE);
            imgVw18.setVisibility(View.VISIBLE);
            imgVw19.setVisibility(View.VISIBLE);
            imgVw20.setVisibility(View.VISIBLE);

            imgVw22.setVisibility(View.INVISIBLE);
            imgVw23.setVisibility(View.INVISIBLE);
            imgVw24.setVisibility(View.INVISIBLE);
            imgVw25.setVisibility(View.INVISIBLE);

        }if(pontos == 22) {
            imgVw22.setVisibility(View.VISIBLE);

            imgVw1.setVisibility(View.VISIBLE);
            imgVw2.setVisibility(View.VISIBLE);
            imgVw3.setVisibility(View.VISIBLE);
            imgVw4.setVisibility(View.VISIBLE);
            imgVw5.setVisibility(View.VISIBLE);
            imgVw6.setVisibility(View.VISIBLE);
            imgVw7.setVisibility(View.VISIBLE);
            imgVw8.setVisibility(View.VISIBLE);
            imgVw9.setVisibility(View.VISIBLE);
            imgVw10.setVisibility(View.VISIBLE);
            imgVw11.setVisibility(View.VISIBLE);
            imgVw12.setVisibility(View.VISIBLE);
            imgVw13.setVisibility(View.VISIBLE);
            imgVw14.setVisibility(View.VISIBLE);
            imgVw15.setVisibility(View.VISIBLE);
            imgVw16.setVisibility(View.VISIBLE);
            imgVw17.setVisibility(View.VISIBLE);
            imgVw18.setVisibility(View.VISIBLE);
            imgVw19.setVisibility(View.VISIBLE);
            imgVw20.setVisibility(View.VISIBLE);
            imgVw21.setVisibility(View.VISIBLE);

            imgVw23.setVisibility(View.INVISIBLE);
            imgVw24.setVisibility(View.INVISIBLE);
            imgVw25.setVisibility(View.INVISIBLE);

        }if(pontos == 23) {
            imgVw23.setVisibility(View.VISIBLE);

            imgVw1.setVisibility(View.VISIBLE);
            imgVw2.setVisibility(View.VISIBLE);
            imgVw3.setVisibility(View.VISIBLE);
            imgVw4.setVisibility(View.VISIBLE);
            imgVw5.setVisibility(View.VISIBLE);
            imgVw6.setVisibility(View.VISIBLE);
            imgVw7.setVisibility(View.VISIBLE);
            imgVw8.setVisibility(View.VISIBLE);
            imgVw9.setVisibility(View.VISIBLE);
            imgVw10.setVisibility(View.VISIBLE);
            imgVw11.setVisibility(View.VISIBLE);
            imgVw12.setVisibility(View.VISIBLE);
            imgVw13.setVisibility(View.VISIBLE);
            imgVw14.setVisibility(View.VISIBLE);
            imgVw15.setVisibility(View.VISIBLE);
            imgVw16.setVisibility(View.VISIBLE);
            imgVw17.setVisibility(View.VISIBLE);
            imgVw18.setVisibility(View.VISIBLE);
            imgVw19.setVisibility(View.VISIBLE);
            imgVw20.setVisibility(View.VISIBLE);
            imgVw21.setVisibility(View.VISIBLE);
            imgVw22.setVisibility(View.VISIBLE);

            imgVw24.setVisibility(View.INVISIBLE);
            imgVw25.setVisibility(View.INVISIBLE);


        }if(pontos == 24) {
            imgVw24.setVisibility(View.VISIBLE);

            imgVw1.setVisibility(View.VISIBLE);
            imgVw2.setVisibility(View.VISIBLE);
            imgVw3.setVisibility(View.VISIBLE);
            imgVw4.setVisibility(View.VISIBLE);
            imgVw5.setVisibility(View.VISIBLE);
            imgVw6.setVisibility(View.VISIBLE);
            imgVw7.setVisibility(View.VISIBLE);
            imgVw8.setVisibility(View.VISIBLE);
            imgVw9.setVisibility(View.VISIBLE);
            imgVw10.setVisibility(View.VISIBLE);
            imgVw11.setVisibility(View.VISIBLE);
            imgVw12.setVisibility(View.VISIBLE);
            imgVw13.setVisibility(View.VISIBLE);
            imgVw14.setVisibility(View.VISIBLE);
            imgVw15.setVisibility(View.VISIBLE);
            imgVw16.setVisibility(View.VISIBLE);
            imgVw17.setVisibility(View.VISIBLE);
            imgVw18.setVisibility(View.VISIBLE);
            imgVw19.setVisibility(View.VISIBLE);
            imgVw20.setVisibility(View.VISIBLE);
            imgVw21.setVisibility(View.VISIBLE);
            imgVw22.setVisibility(View.VISIBLE);
            imgVw23.setVisibility(View.VISIBLE);

            imgVw25.setVisibility(View.INVISIBLE);

        }if(pontos == 25) {
            imgVw25.setVisibility(View.VISIBLE);

            imgVw1.setVisibility(View.VISIBLE);
            imgVw2.setVisibility(View.VISIBLE);
            imgVw3.setVisibility(View.VISIBLE);
            imgVw4.setVisibility(View.VISIBLE);
            imgVw5.setVisibility(View.VISIBLE);
            imgVw6.setVisibility(View.VISIBLE);
            imgVw7.setVisibility(View.VISIBLE);
            imgVw8.setVisibility(View.VISIBLE);
            imgVw9.setVisibility(View.VISIBLE);
            imgVw10.setVisibility(View.VISIBLE);
            imgVw11.setVisibility(View.VISIBLE);
            imgVw12.setVisibility(View.VISIBLE);
            imgVw13.setVisibility(View.VISIBLE);
            imgVw14.setVisibility(View.VISIBLE);
            imgVw15.setVisibility(View.VISIBLE);
            imgVw16.setVisibility(View.VISIBLE);
            imgVw17.setVisibility(View.VISIBLE);
            imgVw18.setVisibility(View.VISIBLE);
            imgVw19.setVisibility(View.VISIBLE);
            imgVw20.setVisibility(View.VISIBLE);
            imgVw21.setVisibility(View.VISIBLE);
            imgVw22.setVisibility(View.VISIBLE);
            imgVw23.setVisibility(View.VISIBLE);
            imgVw24.setVisibility(View.VISIBLE);

        }

    }
}
