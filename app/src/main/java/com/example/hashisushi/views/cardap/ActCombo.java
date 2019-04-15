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

public class ActCombo extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton flotBntVoltarC;
    private FloatingActionButton flotBntEdtPersoC;
    private FloatingActionButton flotBntPontsC;
    private FloatingActionButton flotBntDrinksC;

    private TextView txtCardapC;
    private TextView txtLogoC;
    private TextView txtCombo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_combo);

        getSupportActionBar().hide();

        //Travæ rotaçãø da tela
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        flotBntVoltarC = findViewById(R.id.flotBntVoltarC);
        flotBntEdtPersoC = findViewById(R.id.flotBntEdtPersoC);
        flotBntPontsC = findViewById(R.id.flotBntPontsC);
        flotBntDrinksC = findViewById(R.id.flotBntDrinksC);

        txtCardapC = findViewById(R.id.txtCardapC);
        txtLogoC = findViewById(R.id.txtLogoC);
        txtCombo = findViewById(R.id.txtCombo);

        flotBntVoltarC.setOnClickListener(this);
        flotBntEdtPersoC.setOnClickListener(this);
        flotBntPontsC.setOnClickListener(this);
        flotBntDrinksC.setOnClickListener(this);

        fontLogo();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {

        if ( v.getId() == R.id.flotBntVoltarC ) {

            startVibrate(90);
            Intent it = new Intent(ActCombo.this, ActTemakis.class);
            it.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(it);

        }
        if ( v.getId() == R.id.flotBntPontsC ) {

            startVibrate(90);
            Intent it = new Intent( this, ActPoints.class );
            startActivity( it );

        }if(v.getId() == R.id.flotBntEdtPersoC) {
            startVibrate(90);
            Intent it = new Intent(this, ActSignup.class);
            startActivity(it);
        } if(v.getId() == R.id.flotBntDrinksC) {

            startVibrate(90);
            openDrinks();

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
        txtCardapC.setTypeface(font);
        txtCombo.setTypeface(font);
        txtLogoC.setTypeface(font);
    }

    private void openDrinks(){

        Intent intent = new Intent(ActCombo.this,ActDrinks.class);
        //Passa efeitos de transzição
        ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                R.anim.fade_in,R.anim.mover_direita);
        ActivityCompat.startActivity(ActCombo.this,intent,actcompat.toBundle());
        //startActivity(intent);

    }

    //oa clicar em voltar chama efeito de transição
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.mover_esquerda,R.anim.fade_out);
    }

}
