package com.example.hashisushi.views;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hashisushi.R;

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

        flotBntSearch = findViewById(R.id.flotBntSearch);
        flotBntCarSalesMenu = findViewById(R.id.flotBntCarSalesMenu);
        flotBntScanQcodeMenu = findViewById(R.id.flotBntScanQcode);
        flotBntPersonsMenu = findViewById(R.id.flotBntEdtPersoMenu);
        flotBntPontsMenu = findViewById(R.id.flotBntPonts);

        flotBntCarSalesMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniActOrder();
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
}
