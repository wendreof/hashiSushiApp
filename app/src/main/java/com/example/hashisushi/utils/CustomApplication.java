/**
  * Classe usa api calligraphy para configurar
  * as fontes do projeto .
  * Usamos a fonte RobotoSlab-Regular como
  * fonte principal.
  * E Japonesa e samurai como fonts secundarias
  * */
package com.example.hashisushi.utils;

import android.app.Application;
import com.example.hashisushi.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class CustomApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        CalligraphyConfig.initDefault( new CalligraphyConfig.Builder()
                .setDefaultFontPath( "RobotoSlab-Regular.ttf" )
                //.setDefaultFontPath( "Japonesa.ttf" )
                //.setDefaultFontPath("RagingRedLotusBB.ttf")
                .setFontAttrId( R.attr.fontPath )
                .build());
    }
}