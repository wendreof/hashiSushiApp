package com.example.hashisushi.views;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hashisushi.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActPromotion extends AppCompatActivity
{
    private TextView txtTitle;
    private FloatingActionButton flotBntSalesCardap;
    private FloatingActionButton flotBntScanQcodeProm;


    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.act_promotion );

        getSupportActionBar().hide();
        //Travæ rotaçãø da tela
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );



        txtTitle = findViewById( R.id.txtTitle );
        fontLogo();

        flotBntScanQcodeProm = findViewById(R.id.flotBntScanQcodeProm);
        flotBntSalesCardap = findViewById( R.id.flotBntSalesCadap );

        flotBntSalesCardap.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                iniActSaleCardp();
                startVibrate(90);
            }
        });
        flotBntScanQcodeProm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               iniActPoints();
                startVibrate(90);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    //Altera fonte do txtLogo
    private void fontLogo()
    {
        Typeface font = Typeface.createFromAsset( getAssets(), "RagingRedLotusBB.ttf" );
        txtTitle.setTypeface( font );
    }

    public void iniActSaleCardp()
    {
            Intent it = new Intent( this, ActSaleCardap.class );
            startActivity( it );
    }

    public void iniActPoints()
    {
        Intent it = new Intent( this, ActPoints.class );
        startActivity( it );
    }
    //Metudo que ativa vibração
    public void startVibrate(long time) {
        // cria um obj atvib que recebe seu valor de context
        Vibrator atvib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        atvib.vibrate(time);
    }

}
