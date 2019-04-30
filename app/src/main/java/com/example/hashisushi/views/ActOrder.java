package com.example.hashisushi.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hashisushi.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActOrder extends AppCompatActivity implements View.OnClickListener{

    private TextView txtTitle;
    private TextView txtPedido;
    private TextView txtTotal;

    private String[] fillPay = { "Dinheiro","MasterCard Credito","Visa Credito","Aura Credito",
                                 "Elo Credito","Diners Club Credito","Sorocred Credito",
                                 "Hipercard Credito","MaestroCard Debito","Visa Eletrônic Debito"};
    private Spinner spnFillPayment;

    private CheckBox chkBxRetirar;
    private CheckBox chkBxEntrega;
    private  boolean ischkRetirar ;
    private  boolean ischkEntregar;

    private FloatingActionButton flotBntVoltarO;
    private FloatingActionButton flotBntEdtPersoO;
    private FloatingActionButton flotBntPontsO;
    private FloatingActionButton flotBntAboutO;
    private Button btnFinishOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_order);
        getSupportActionBar().hide();

        flotBntVoltarO = findViewById(R.id.flotBntVoltarO);
        flotBntEdtPersoO = findViewById(R.id.flotBntEditPersonO);
        flotBntPontsO = findViewById(R.id.flotBntPontsO);
        flotBntAboutO = findViewById(R.id.flotBntAboutO);
        btnFinishOrder = findViewById(R.id.btnFinishOrder);

        spnFillPayment = findViewById(R.id.spnfillPayMent);
        txtTitle = findViewById(R.id.txtTitle);
        txtPedido = findViewById(R.id.txtPedido);
        txtTotal = findViewById(R.id.txtTotal);

        chkBxRetirar = findViewById(R.id.chkBxRetirar);
        chkBxEntrega = findViewById(R.id.chkBxEntrega);

        flotBntVoltarO.setOnClickListener(this);
        flotBntEdtPersoO.setOnClickListener(this);
        flotBntPontsO.setOnClickListener(this);
        flotBntAboutO.setOnClickListener(this);

        fontLogo();
        fillPayMent();

        chkBxEntrega.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    ischkRetirar = false;
                    ischkEntregar = isChecked;

                    chkBxRetirar.setChecked(ischkRetirar);
                    chkBxEntrega.setChecked(ischkEntregar);
                }
            }
        });

        chkBxRetirar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    ischkRetirar = isChecked;
                    ischkEntregar = false;

                    chkBxRetirar.setChecked(ischkRetirar);
                    chkBxEntrega.setChecked(ischkEntregar);
                }
            }
        });

        btnFinishOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVibrate(190);
                valueTest();
            }
        });
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
        txtPedido.setTypeface(font);
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


    @Override
    public void onClick(View v) {

        if ( v.getId() == R.id.flotBntVoltarO ) {

            startVibrate(90);
            //Intent it = new Intent(ActPlatAce.this, ActPlatHot.class);
            //it.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            //startActivity(it);
            finish();

        } if ( v.getId() == R.id.flotBntPontsO ) {

            startVibrate(90);
            Intent it = new Intent( this, ActPoints.class );
            startActivity( it );

        } if(v.getId() == R.id.flotBntEditPersonO){

            startVibrate(90);
            Intent it = new Intent(this, ActSignup.class);
            startActivity(it);

        }if(v.getId() == R.id.flotBntAboutO) {

            startVibrate(90);
        }
    }

    //oa clicar em voltar chama efeito de transição
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.mover_esquerda,R.anim.fade_out);
    }

    private void valueTest(){
        String value = txtTotal.getText().toString();

        if(value.equals( "00,00" )){
            msgShort("Não há itens para finalizar o pedido !");
        }
    }

}
