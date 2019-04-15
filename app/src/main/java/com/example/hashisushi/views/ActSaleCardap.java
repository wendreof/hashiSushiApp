package com.example.hashisushi.views;

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
import android.widget.Toast;

import com.example.hashisushi.R;
import com.example.hashisushi.views.cardap.ActPlatHot;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActSaleCardap extends AppCompatActivity implements View.OnClickListener
{
    private TextView txtCardapScardp;
    private TextView txtLogoScardp;
    private TextView txtSalesCardap;

    private FloatingActionButton flotBntVoltarPromoE;
    private FloatingActionButton flotBntEdtPersoE;
    private FloatingActionButton flotBntPontsE;
    private FloatingActionButton flotBtnPlatHotE;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState);
        setContentView( R.layout.act_sale_cardap );
        getSupportActionBar().hide();

        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        txtCardapScardp = findViewById(R.id.txtCardapScardp);
        txtLogoScardp = findViewById(R.id.txtLogoScardp);
        txtSalesCardap = findViewById(R.id.txtSalesCardap);

        flotBntVoltarPromoE = findViewById(R.id.flotBntVoltarPromoE);
        flotBntEdtPersoE = findViewById(R.id.flotBntEdtPersoE);
        flotBntPontsE = findViewById(R.id.flotBntPontsE);
        flotBtnPlatHotE = findViewById(R.id.flotBtnPlatHotE);

        fontLogo();

        flotBntVoltarPromoE.setOnClickListener(this);
        flotBntEdtPersoE.setOnClickListener(this);
        flotBntPontsE.setOnClickListener(this);
        flotBtnPlatHotE.setOnClickListener(this);



    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {

        if ( v.getId() == R.id.flotBntVoltarPromoE) {

            startVibrate(90);

            Intent it = new Intent(ActSaleCardap.this, ActPromotion.class);
            it.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(it);

        }
        if ( v.getId() == R.id.flotBntPontsE ) {

            startVibrate(90);
            Intent it = new Intent( this, ActPoints.class );
            startActivity( it );

        }if(v.getId() == R.id.flotBntEdtPersoE){
            startVibrate(90);
            Intent it = new Intent(this, ActSignup.class);
            startActivity(it);
        } if(v.getId() == R.id.flotBtnPlatHotE) {

            startVibrate(90);
            openPlatHot();

        }
    }

    //Altera fonte do txtLogo
    private void fontLogo(){

        Typeface font = Typeface.createFromAsset(getAssets(), "RagingRedLotusBB.ttf");
        txtCardapScardp.setTypeface(font);
        txtLogoScardp.setTypeface(font);
        txtSalesCardap.setTypeface(font);
    }


    //Metudo que ativa vibração
    public void startVibrate(long time) {
        // cria um obj atvib que recebe seu valor de context
        Vibrator atvib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        atvib.vibrate(time);
    }

    private void msgShort(String msg) {

        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

    //Altera fonte do txtLogo
    public Typeface setFont(){

        Typeface font = Typeface.createFromAsset(getAssets(), "RagingRedLotusBB.ttf");

        return font;
    }


    private void openPlatHot(){

        Intent intent = new Intent(ActSaleCardap.this,ActPlatHot.class);
        //Passa efeitos de transzição
        ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                R.anim.fade_in,R.anim.mover_direita);
        ActivityCompat.startActivity(ActSaleCardap.this,intent,actcompat.toBundle());
        //startActivity(intent);


    }

    //oa clicar em voltar chama efeito de transição
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.mover_esquerda,R.anim.fade_out);
    }
}
