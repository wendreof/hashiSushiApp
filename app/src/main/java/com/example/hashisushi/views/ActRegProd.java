package com.example.hashisushi.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hashisushi.R;
import com.example.hashisushi.dao.ProductDao;
import com.example.hashisushi.dao.UserDao;
import com.example.hashisushi.model.Product;
import com.example.hashisushi.model.User;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActRegProd extends AppCompatActivity implements View.OnClickListener{

    private String[] type = { "Entrada","Pratos_Quentes","Pratos_Frios",
            "Temakis","Bebidas","Combo"};
    private String[] isPromotion = { "Não","Sim"};
    private Spinner spnType;
    private Spinner spnIsPrmotion;

    private EditText edtDiscriptionProd;
    private EditText edtNumberPro;
    private EditText edtNameProd;
    private EditText edtValProd;

    private FloatingActionButton flotBntHomeReg;
    private FloatingActionButton flotBntSaveReg;
    private FloatingActionButton flotBntNewReg;
    private FloatingActionButton flotBntExitReg;

    private TextView txtTitleReg;
    private TextView txtRegisterProd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_reg_prod);
        getSupportActionBar().hide();

        startCompnent();

        isPromotionSpn();
        typeSpn();
        fontLogo();

        flotBntHomeReg.setOnClickListener(this);
        flotBntSaveReg.setOnClickListener(this);
        flotBntNewReg.setOnClickListener(this);
        flotBntExitReg.setOnClickListener(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    //Altera fonte do txtLogo
    private void fontLogo(){

        Typeface font = Typeface.createFromAsset(getAssets(), "RagingRedLotusBB.ttf");
        txtRegisterProd.setTypeface(font);
        txtTitleReg.setTypeface(font);

    }

    private void startCompnent(){

        spnType = findViewById(R.id.spnType);
        spnIsPrmotion = findViewById(R.id.spnIsPrmotion);

        edtDiscriptionProd = findViewById(R.id.edtDiscriptionProd);
        edtNumberPro = findViewById(R.id.edtNumberPro);
        edtNameProd = findViewById(R.id.edtNameProd);
        edtValProd = findViewById(R.id.edtValProd);

        txtTitleReg = findViewById(R.id.txtTitleReg);
        txtRegisterProd = findViewById(R.id.txtRegisterProd);

        flotBntHomeReg = findViewById(R.id.flotBntHomeReg);
        flotBntSaveReg = findViewById(R.id.flotBntSaveReg);
        flotBntNewReg = findViewById(R.id.flotBntNewReg);
        flotBntExitReg = findViewById(R.id.flotBntExitReg);
    }

    private void isPromotionSpn(){
        try {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,isPromotion);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnIsPrmotion.setAdapter(adapter);
        }catch (Exception ex){
            msgShort("Erro:-->"+ex.getMessage());
        }
    }

    private void typeSpn(){
        try {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,type);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnType.setAdapter(adapter);
        }catch (Exception ex){
            msgShort("Erro:-->"+ex.getMessage());
        }
    }

    private void msgShort(String msg) {

        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

    //Metudo que ativa vibração
    public void startVibrate(long time) {
        // cria um obj atvib que recebe seu valor de context
        Vibrator atvib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        atvib.vibrate(time);
    }

    @Override
    public void onClick(View v) {


        if ( v.getId() == R.id.flotBntHomeReg) {

            startVibrate(90);
            Intent it = new Intent( this, ActPromotion.class );
            startActivity( it );
        }
        if ( v.getId() == R.id.flotBntNewReg ) {

            startVibrate(90);
            Intent it = new Intent( this, ActPoints.class );
            startActivity( it );

        }if(v.getId() == R.id.flotBntSaveReg){

            startVibrate(90);
            addProd();
        } if(v.getId() == R.id.flotBntExitReg) {

            startVibrate(90);
            finish();

        }
    }

    private void addProd() {

          String descricao =  edtDiscriptionProd.getText().toString();
          String numberProd =  edtNumberPro.getText().toString();
          String name = edtNameProd.getText().toString();
          String valProd  = edtValProd.getText().toString();

        if(descricao.equals("") && numberProd.equals("") && name.equals("") && valProd.equals("")){
            msgShort("Há campos sem valor;preencha todos os campos !");
        }else {
            try {
                Product p = new Product();

                p.setName(edtNameProd.getText().toString());
                p.setDescription(edtDiscriptionProd.getText().toString());
                p.setSalePrice(edtValProd.getText().toString());
                p.setIdProd(edtNumberPro.getText().toString());

                String strProme = spnIsPrmotion.getSelectedItem().toString();

                boolean bolProme  = false;
                if(strProme.equals("Sim")){
                    bolProme = true;
                }else {
                    bolProme = false;
                }
                p.setPromotion(bolProme);

                String strType = spnType.getSelectedItem().toString();
                p.setType(strType);

                ProductDao productDao = new ProductDao();
                productDao.addProduct(p);
                msgShort("Produto salvo com sucesso!..");

                //Snackbar.make(ActRegProd, R.string.registration_completed, Snackbar.LENGTH_LONG).show();
            } catch (Exception erro) {
                msgShort("Erro na gravação de produto ERRO : " + erro);
                // Snackbar.make(ActRegProd, R.string.registration_error , Snackbar.LENGTH_LONG).show();

            }
        }
    }
}
