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

public class ActPromotion extends AppCompatActivity implements View.OnClickListener
{
    private TextView txtTitle;
    private TextView txtStatus;
    private FloatingActionButton flotBntSalesCardap;
    private FloatingActionButton flotBntPontsProm;
    private FloatingActionButton flotBntExitP;
    private  FloatingActionButton flotBntEditPersonP;
    private System status;


    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.act_promotion );

        getSupportActionBar().hide();
        //Travæ rotaçãø da tela
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );


        txtStatus = findViewById( R.id.txtEstatus );
        txtTitle = findViewById( R.id.txtTitle );
        getStatus();
        fontLogo();
        flotBntExitP = findViewById(R.id.flotBntExitP);
        flotBntPontsProm = findViewById(R.id.flotBntPontsProm);
        flotBntSalesCardap = findViewById( R.id.flotBntSaleCardapP);
        flotBntEditPersonP = findViewById(R.id.flotBntEditPersonP);

        flotBntExitP.setOnClickListener(this);
        flotBntPontsProm.setOnClickListener(this);
        flotBntSalesCardap.setOnClickListener(this);
        flotBntEditPersonP.setOnClickListener( this );

    }

    private void getStatus(){
       String stt = System.getProperty("STATUS_ENV");
       txtStatus.setText(stt);
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


    @Override
    public void onClick(View v) {

        if ( v.getId() == R.id.flotBntSaleCardapP ) {

            startVibrate(90);
            Intent it = new Intent( this, ActSaleCardap.class );
            startActivity( it );

        }
        if ( v.getId() == R.id.flotBntPontsProm ) {

            startVibrate(90);
            Intent it = new Intent( this, ActPoints.class );
            startActivity( it );

        }if(v.getId() == R.id.flotBntEditPersonP){
            startVibrate(90);
            Intent it = new Intent(this, ActSignup.class);
            startActivity(it);
        } else if(v.getId() == R.id.flotBntExitP) {

            startVibrate(90);
            //finaliza a activity atual e todas a baixo
            this.finishAffinity();
        }
    }


    //Metudo que ativa vibração
    public void startVibrate(long time) {
        // cria um obj atvib que recebe seu valor de context
        Vibrator atvib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        atvib.vibrate(time);
    }



}
