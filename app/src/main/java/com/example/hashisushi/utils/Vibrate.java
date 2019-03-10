package com.example.hashisushi.utils;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;

public class Vibrate extends AppCompatActivity {

    //Metudo que ativa vibração
    public void startVibrate(long time) {
        // cria um obj atvib que recebe seu valor de context
        Vibrator atvib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        atvib.vibrate(time);
    }
}
