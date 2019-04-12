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
import com.example.hashisushi.views.ActOrder;
import com.example.hashisushi.views.ActPoints;
import com.example.hashisushi.views.ActSignup;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActDrinks extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton flotBntVoltarD;
    private FloatingActionButton flotBntEdtPersoD;
    private FloatingActionButton flotBntPontsD;
    private FloatingActionButton flotBntOrderSaleD;

    private TextView txtCardapD;
    private TextView txtLogoD;
    private TextView txtDrinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_drinks);

        getSupportActionBar().hide();

        //Travæ rotaçãø da tela
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        flotBntVoltarD = findViewById(R.id.flotBntVoltarD);
        flotBntEdtPersoD = findViewById(R.id.flotBntEdtPersoD);
        flotBntPontsD = findViewById(R.id.flotBntPontsD);
        flotBntOrderSaleD = findViewById(R.id.flotBntOrderSaleD);

        flotBntVoltarD.setOnClickListener(this);
        flotBntEdtPersoD.setOnClickListener(this);
        flotBntPontsD.setOnClickListener(this);
        flotBntOrderSaleD.setOnClickListener(this);

        txtCardapD = findViewById(R.id.txtCardapD);
        txtLogoD = findViewById(R.id.txtLogoD);
        txtDrinks = findViewById(R.id.txtDrinks);

        fontLogo();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {

        if ( v.getId() == R.id.flotBntVoltarD ) {

            startVibrate(90);
            Intent it = new Intent(ActDrinks.this, ActCombo.class);
            it.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(it);

        }
        if ( v.getId() == R.id.flotBntPontsD ) {

            startVibrate(90);
            Intent it = new Intent( this, ActPoints.class );
            startActivity( it );

        }if(v.getId() == R.id.flotBntEdtPersoD){
            startVibrate(90);
            Intent it = new Intent(this, ActSignup.class);
            startActivity(it);
        } else if(v.getId() == R.id.flotBntOrderSaleD) {

            startVibrate(90);
            Intent it = new Intent(this, ActOrder.class);
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
        txtLogoD.setTypeface(font);
        txtCardapD.setTypeface(font);
        txtDrinks.setTypeface(font);
    }

}
