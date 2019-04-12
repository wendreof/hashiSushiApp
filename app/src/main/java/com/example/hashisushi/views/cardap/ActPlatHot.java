package com.example.hashisushi.views.cardap;

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
import com.example.hashisushi.views.ActPoints;
import com.example.hashisushi.views.ActSaleCardap;
import com.example.hashisushi.views.ActSignup;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActPlatHot extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton flotBntVoltarPh;
    private FloatingActionButton flotBntEdtPersoPh;
    private FloatingActionButton flotBntPontsPh;
    private FloatingActionButton flotBntPlanAcePh;

    private TextView txtCardapPh;
    private TextView txtLogoPh;
    private TextView txtPlatHot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_plat_hot);

        getSupportActionBar().hide();

        //Travæ rotaçãø da tela
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtCardapPh = findViewById(R.id.txtCardapPh);
        txtLogoPh = findViewById(R.id.txtLogoPh);
        txtPlatHot = findViewById(R.id.txtPlaHot);

        flotBntVoltarPh = findViewById(R.id.flotBntVoltarPh);
        flotBntEdtPersoPh = findViewById(R.id.flotBntEdtPersoPh);
        flotBntPontsPh = findViewById(R.id.flotBntPontsPh);
        flotBntPlanAcePh = findViewById(R.id.flotBntPlanAcePh);

        flotBntVoltarPh.setOnClickListener(this);
        flotBntEdtPersoPh.setOnClickListener(this);
        flotBntPontsPh.setOnClickListener(this);
        flotBntPlanAcePh.setOnClickListener(this);

        fontLogo();

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {

        if ( v.getId() == R.id.flotBntVoltarPh ) {

            startVibrate(90);
            Intent it = new Intent(ActPlatHot.this, ActSaleCardap.class);
            it.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(it);

        }
        if ( v.getId() == R.id.flotBntPontsE ) {

            startVibrate(90);
            Intent it = new Intent( this, ActPoints.class );
            startActivity( it );

        }if(v.getId() == R.id.flotBntEdtPersoPh){
            startVibrate(90);
            Intent it = new Intent(this, ActSignup.class);
            startActivity(it);
        } else if(v.getId() == R.id.flotBntPlanAcePh) {

            startVibrate(90);
            Intent it = new Intent(this, ActPlatAce.class);
            startActivity(it);

        }
    }


    //Metudo que ativa vibração
    public void startVibrate(long time) {
        // cria um obj atvib que recebe seu valor de context
        Vibrator atvib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        atvib.vibrate(time);
    }

    //Altera fonte do txtLogo
    private void fontLogo(){

        Typeface font = Typeface.createFromAsset(getAssets(), "RagingRedLotusBB.ttf");
        txtCardapPh.setTypeface(font);
        txtLogoPh.setTypeface(font);
        txtPlatHot.setTypeface(font);
    }

}
