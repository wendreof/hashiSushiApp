/**
  * Classe usa api calligraphy para configurar
  * as fontes do projeto .
  * Usamos a fonte RobotoSlab-Regular como
  * fonte principal.
  * E Japonesa e samurai como fonts secundarias
  * */
package com.example.hashisushi;

import android.app.Application;


import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("RobotoSlab-Regular.ttf")
                //.setDefaultFontPath("Japonesa.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }


}