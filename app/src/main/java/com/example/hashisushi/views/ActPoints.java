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
import android.widget.TextView;
import android.widget.Toast;

import com.example.hashisushi.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActPoints extends AppCompatActivity
{
    private TextView txtLogo;
    private  TextView txtPonts;
    private FloatingActionButton flotBntScanQcodePont;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.act_points);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        final Activity actScanCod = this;

        txtPonts =findViewById(R.id.txtPonts);
        txtLogo = findViewById(R.id.txtLogo);
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
        txtPonts.setTypeface( font );
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

}
