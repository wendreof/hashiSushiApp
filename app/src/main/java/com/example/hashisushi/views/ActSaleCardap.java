package com.example.hashisushi.views;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hashisushi.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ActSaleCardap extends AppCompatActivity
{
    private EditText edtSearchPlate;
    private TextView txtCardap;
    private FloatingActionButton flotBntSearch;
    private FloatingActionButton flotBntHomeMenu;
    private FloatingActionButton flotBntScanQcodeMenu;
    private FloatingActionButton flotBntCarSalesMenu;
    private FloatingActionButton flotBntPersonsMenu;
    private FloatingActionButton flotBntPontsMenu;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState);
        setContentView( R.layout.act_sale_cardap );
        getSupportActionBar().hide();

        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        txtCardap = findViewById( R.id.txtCardap );
        fontLogo();
        final Activity actScanCod = this;
        flotBntSearch = findViewById(R.id.flotBntSearch);
        flotBntCarSalesMenu = findViewById(R.id.flotBntCarSalesMenu);
        flotBntScanQcodeMenu = findViewById(R.id.flotBntScanQcode);
        flotBntPersonsMenu = findViewById(R.id.flotBntEdtPersoMenu);
        flotBntPontsMenu = findViewById(R.id.flotBntPonts);

        flotBntCarSalesMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVibrate(90);
                iniActOrder();
            }
        });
        flotBntScanQcodeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVibrate(90);
                scanerCode(actScanCod);
            }
        });
    }
    //Altera fonte do txtLogo
    private void fontLogo()
    {
        Typeface font = Typeface.createFromAsset(getAssets(), "RagingRedLotusBB.ttf");
        txtCardap.setTypeface(font);
    }

    public void iniActOrder()
    {
        Intent it = new Intent( this, ActOrder.class );
        startActivity( it );
    }

    //Metudo que ativa vibração
    public void startVibrate(long time) {
        // cria um obj atvib que recebe seu valor de context
        Vibrator atvib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        atvib.vibrate(time);
    }

    public void iniActPoints()
    {
        Intent it = new Intent( this, ActPoints.class );
        startActivity( it );
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
