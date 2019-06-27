package com.example.hashisushi.views;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;


import android.widget.ImageView;
import android.widget.TextView;

import com.example.hashisushi.R;

import com.google.firebase.auth.FirebaseAuth;

public class ActSplash extends AppCompatActivity {

    private TextView txtDelivery;
    private TextView txtwhats;
    private ImageView imgLogoS;
    private ImageView imgWhats;

    private FirebaseAuth auth;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_splash);

        getSupportActionBar().hide();

        //Travæ rotaçãø da tela
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initComponent();
        fontLogo();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               // startActivity(new Intent(getBaseContext(),ActLogin.class));
                testUserCurrent();
                finish();
            }
        },5000);

        this.auth = FirebaseAuth.getInstance();
        boolean sendNot = false;

        if (sendNot == true )
            setNotification("Oferta","Todos os combos a R$ 19,90");
    }
    //Altera fonte do txtLogo
    private void fontLogo()
    {

        Typeface font = Typeface.createFromAsset(getAssets(), "RagingRedLotusBB.ttf");
        txtDelivery.setTypeface(font);
        txtwhats.setTypeface(font);

        imgLogoS.setImageResource(R.drawable.lghashi);
        imgWhats.setImageResource(R.drawable.whats);

    }

    private void initComponent()
    {

        txtDelivery = findViewById(R.id.txtDelivery);
        txtwhats = findViewById(R.id.txtWhats);
        imgLogoS = findViewById(R.id.imgLogoS);
        imgWhats = findViewById(R.id.imgWhats);
    }

    //cria notificação
    public void setNotification(String title ,String body) {
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //PendingIntent p = PendingIntent.getActivity(this,0, new Intent(),0 );

        if (auth.getCurrentUser() != null)
        {
            pendingIntent = PendingIntent.getActivity(this,0, new Intent(this,ActPromotion.class),0 );

        }
        else {
            pendingIntent  = PendingIntent.getActivity(this,0, new Intent(this,ActLogin.class),0 );
        }

        //pendingIntent = PendingIntent.getActivity(this,0, new Intent(this,ActPromotion.class),0 );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setTicker(" Hash Sushi App ");
        builder.setContentTitle(title);

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources() ,R.mipmap.ic_launcher));
        builder.setContentIntent(pendingIntent);

        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        String[] descs = new String[]{body};

        for(int i = 0;i < descs.length; i++)
        {
            style.addLine(descs[i]);
        }

        builder.setStyle(style);

        Notification no = builder.build();
        no.vibrate = new long[]{150,300,150};
        no.flags = Notification.FLAG_AUTO_CANCEL;
        nm.notify(R.mipmap.ic_launcher,no);

        try {
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(this,som);
            toque.play();
        }catch (Exception e){

            System.out.println("Erro ao gerar toque notificção : "+e);
        }
    }
    //chama notificação
    public void startNotification(){
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(R.mipmap.ic_launcher);
    }

    //case user login  ok  actpromotion
    public void testUserCurrent(){

        if (auth.getCurrentUser() != null)
        {
            Intent it = new Intent(this, ActPromotion.class);
            startActivity(it);

        }
        else {

            Intent it = new Intent(this, ActLogin.class);
            startActivity(it);
        }

    }

}
