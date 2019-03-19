package com.example.hashisushi.views;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hashisushi.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActOrder extends AppCompatActivity {
    private TextView txtTitle;

    private String[] fillPay = { "Dinheiro","Credito MasterCard","Credito Visa","Credito Aura",
                                 "Credito Elo","Credito Diners Club","Credito Sorocred",
                                 "Credito Hipercard","Debito MasterCard","Debito Visa"};
    private Spinner spnFillPayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_order);
        getSupportActionBar().hide();

        spnFillPayment = findViewById(R.id.spnfillPayMent);
        txtTitle =findViewById(R.id.txtTitle);

        fontLogo();
        fillPayMent();
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    //Altera fonte do txtLogo
    private void fontLogo()
    {
        Typeface font = Typeface.createFromAsset( getAssets(), "RagingRedLotusBB.ttf" );
        txtTitle.setTypeface( font );
    }


    //Metudo que ativa vibração
    public void startVibrate(long time) {
        // cria um obj atvib que recebe seu valor de context
        Vibrator atvib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        atvib.vibrate(time);
    }

    private void fillPayMent(){
        try {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,fillPay);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnFillPayment.setAdapter(adapter);
        }catch (Exception ex){
            msgShort("Erro:-->"+ex.getMessage());
        }
    }
    private void msgShort(String msg) {

        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

}
