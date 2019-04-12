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
import com.example.hashisushi.views.ActSignup;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActTemakis extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton flotBntVoltarT;
    private FloatingActionButton flotBntEdtPersoT;
    private FloatingActionButton flotBntPontsT;
    private FloatingActionButton flotBntComboT;

    private TextView txtCardapT;
    private TextView txtLogoT;
    private TextView txtTemakis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_temakis);
        getSupportActionBar().hide();

        //Travæ rotaçãø da tela
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        flotBntVoltarT = findViewById(R.id.flotBntVoltarT);
        flotBntEdtPersoT = findViewById(R.id.flotBntEdtPersoT);
        flotBntPontsT = findViewById(R.id.flotBntPontsT);
        flotBntComboT = findViewById(R.id.flotBntComboT);

        flotBntVoltarT.setOnClickListener(this);
        flotBntEdtPersoT.setOnClickListener(this);
        flotBntPontsT.setOnClickListener(this);
        flotBntComboT.setOnClickListener(this);

        txtCardapT = findViewById(R.id.txtCardapT);
        txtLogoT = findViewById(R.id.txtLogoT);
        txtTemakis = findViewById(R.id.txtTemakis);

        fontLogo();
    }

    @Override
    public void onClick(View v) {

        if ( v.getId() == R.id.flotBntVoltarT ) {

            startVibrate(90);
            Intent it = new Intent(ActTemakis.this, ActPlatAce.class);
            it.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(it);

        }
        if ( v.getId() == R.id.flotBntPontsT ) {

            startVibrate(90);
            Intent it = new Intent( this, ActPoints.class );
            startActivity( it );

        }if(v.getId() == R.id.flotBntEdtPersoT){
            startVibrate(90);
            Intent it = new Intent(this, ActSignup.class);
            startActivity(it);
        } else if(v.getId() == R.id.flotBntComboT) {

            startVibrate(90);
            Intent it = new Intent(this, ActCombo.class);
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
        txtCardapT.setTypeface(font);
        txtLogoT.setTypeface(font);
        txtTemakis.setTypeface(font);
    }

}
