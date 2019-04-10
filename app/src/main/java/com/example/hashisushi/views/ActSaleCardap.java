package com.example.hashisushi.views;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hashisushi.R;
import com.example.hashisushi.adapter.FragmantAdapter;


public class ActSaleCardap extends AppCompatActivity
{
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private TextView txtCardap;
    private TextView txtLogoCard;
    private FloatingActionButton flotBntHomeMenu;
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
        txtLogoCard = findViewById( R.id.txtLogoCombo);

        flotBntCarSalesMenu = findViewById(R.id.flotBntCarSalesMenu);
        flotBntPersonsMenu = findViewById(R.id.flotBntEdtPersoMenu);
        flotBntPontsMenu = findViewById(R.id.flotBntPontsMenu);

        paginador();


    }
    //Altera fonte do txtLogo
    private void fontLogo()
    {
        Typeface font = Typeface.createFromAsset(getAssets(), "RagingRedLotusBB.ttf");
        txtCardap.setTypeface(font);
        txtLogoCard.setTypeface( font );
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

    private void msgShort(String msg) {

        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
   //ativa paginaçao
    private void paginador(){

        mTabLayout = findViewById(R.id.TabCardap);
        mViewPager = findViewById(R.id.view_pager);

        mViewPager.setAdapter(new FragmantAdapter(getSupportFragmentManager(),
                getResources().getStringArray(R.array.titles_tab)));
        mTabLayout.setupWithViewPager(mViewPager);
    }

}
