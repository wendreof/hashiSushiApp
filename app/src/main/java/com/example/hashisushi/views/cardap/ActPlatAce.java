package com.example.hashisushi.views.cardap;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hashisushi.R;
import com.example.hashisushi.views.ActPoints;
import com.example.hashisushi.views.ActSaleCardap;
import com.example.hashisushi.views.ActSignup;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActPlatAce extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton flotBntVoltarAce;
    private FloatingActionButton flotBntEdtPersoAce;
    private FloatingActionButton flotBntPontsAce;
    private FloatingActionButton flotBntTemakisAce;

    private TextView txtCardapA;
    private TextView txtLogoA;
    private TextView txtPlatAce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_plat_ace);

        getSupportActionBar().hide();

        //Travæ rotaçãø da tela
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        flotBntVoltarAce = findViewById(R.id.flotBntVoltarAce);
        flotBntEdtPersoAce = findViewById(R.id.flotBntEdtPersoAce);
        flotBntPontsAce = findViewById(R.id.flotBntPontsAce);
        flotBntTemakisAce = findViewById(R.id.flotBntTemakisAce);

        txtCardapA = findViewById(R.id.txtCardapA);
        txtLogoA = findViewById(R.id.txtLogoA);
        txtPlatAce = findViewById(R.id.txtPlatAce);

        flotBntVoltarAce.setOnClickListener(this);
        flotBntEdtPersoAce.setOnClickListener(this);
        flotBntPontsAce.setOnClickListener(this);
        flotBntTemakisAce.setOnClickListener(this);

        fontLogo();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {

        if ( v.getId() == R.id.flotBntVoltarAce ) {

            startVibrate(90);
            //Intent it = new Intent(ActPlatAce.this, ActPlatHot.class);
            //it.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            //startActivity(it);
            finish();

        }
        if ( v.getId() == R.id.flotBntPontsAce ) {

            startVibrate(90);
            Intent it = new Intent( this, ActPoints.class );
            startActivity( it );

        }if(v.getId() == R.id.flotBntEdtPersoAce){
            startVibrate(90);
            Intent it = new Intent(this, ActSignup.class);
            startActivity(it);
        } else if(v.getId() == R.id.flotBntTemakisAce) {

            startVibrate(90);
            openTemakis();

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
        txtCardapA.setTypeface(font);
        txtLogoA.setTypeface(font);
        txtPlatAce.setTypeface(font);
    }

    private void openTemakis(){

        Intent intent = new Intent(ActPlatAce.this,ActTemakis.class);
        //Passa efeitos de transzição
        ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                R.anim.fade_in,R.anim.mover_direita);
        ActivityCompat.startActivity(ActPlatAce.this,intent,actcompat.toBundle());

    }

    //oa clicar em voltar chama efeito de transição
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.mover_esquerda,R.anim.fade_out);
    }
}
