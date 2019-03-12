package com.example.hashisushi.views;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hashisushi.R;


import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActPoints extends AppCompatActivity
{
   private FloatingActionButton flotBtnScanPont;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.act_points);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }





}
