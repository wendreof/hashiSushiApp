package com.example.hashisushi.views;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hashisushi.R;
import com.example.hashisushi.utils.MockPaymentMethods;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActOrder extends AppCompatActivity implements View.OnClickListener {

    private TextView txtTitle;
    private TextView txtPedido;
    private TextView txtTotal;
    private Spinner spnFillPayment;
    private RadioButton chkBxRetirar;
    private RadioButton chkBxEntrega;
    private EditText editObservation;
    private Button btnFinishOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_order);
        getSupportActionBar().hide();

        //   FloatingActionButton flotBntVoltarO = findViewById(R.id.flotBntVoltarO);
        //    FloatingActionButton flotBntEdtPersoO = findViewById(R.id.flotBntEditPersonO);
        //     FloatingActionButton flotBntPontsO = findViewById(R.id.flotBntPontsO);
        //      FloatingActionButton flotBntAboutO = findViewById(R.id.flotBntAboutO);

        findViewByIds();

        //      flotBntVoltarO.setOnClickListener(this);
        //     flotBntEdtPersoO.setOnClickListener(this);
        //     flotBntPontsO.setOnClickListener(this);
        //     flotBntAboutO.setOnClickListener(this);

        fontLogo();
        fillPayMent();

        btnFinishOrder.setOnClickListener(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    //Altera fonte do txtLogo
    private void fontLogo() {
        Typeface font = Typeface.createFromAsset(getAssets(), "RagingRedLotusBB.ttf");
        txtTitle.setTypeface(font);
        txtPedido.setTypeface(font);
    }

    //Metudo que ativa vibração
    public void startVibrate(long time) {
        // cria um obj atvib que recebe seu valor de context
        Vibrator atvib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        atvib.vibrate(time);
    }

    private void fillPayMent() {
        try {
            List<String> list = MockPaymentMethods.INSTANCE.getPaymentMethods();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnFillPayment.setAdapter(adapter);
        } catch (Exception ex) {
            msgShort("Erro:" + ex.getMessage());
        }
    }

    private void msgShort(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

       /* if (v.getId() == R.id.flotBntVoltarO) {

            startVibrate(90);
            //Intent it = new Intent(ActPlatAce.this, ActPlatHot.class);
            //it.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            //startActivity(it);
            finish();

        }
        else if (v.getId() == R.id.flotBntPontsO) {

            startVibrate(90);
            Intent it = new Intent(this, ActPoints.class);
            startActivity(it);

        }
        else if  (v.getId() == R.id.flotBntEditPersonO) {
            startVibrate(90);
            Intent it = new Intent(this, ActSignup.class);
            startActivity(it);
        }
        else if  (v.getId() == R.id.flotBntAboutO) {
            startVibrate(90);
        }

       */

        if (v.getId() == R.id.btnFinishOrder) {
            startVibrate(190);
            valueTest();
        }

      /*  if (v.getId() == R.id.spnfillPayMent) {
            spnFillPayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   // if (!spnFillPayment.getSelectedItem().toString().equals("Dinheiro")) {
                        txtTroco.setEnabled(false);
                        editValueTroco.setEnabled(false);
                        msgShort(spnFillPayment.getSelectedItem().toString());
                   // }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } */
    }

    //oa clicar em voltar chama efeito de transição
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.mover_esquerda, R.anim.fade_out);
    }

    private void valueTest() {
        String value = txtTotal.getText().toString();

        if (value.equals("00,00")) {
            msgShort("Não há itens para finalizar o pedido! =x");
        }
    }

    private void findViewByIds() {
        spnFillPayment = findViewById(R.id.spnfillPayMent);
        txtTitle = findViewById(R.id.txtTitleReg);
        txtPedido = findViewById(R.id.txtPedido);
        txtTotal = findViewById(R.id.txtTotal);
        chkBxRetirar = findViewById(R.id.chkBxRetirar);
        chkBxEntrega = findViewById(R.id.chkBxEntrega);
        btnFinishOrder = findViewById(R.id.btnFinishOrder);
        editObservation = findViewById(R.id.editObservation);
    }
}
