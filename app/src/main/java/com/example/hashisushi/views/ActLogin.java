package com.example.hashisushi.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hashisushi.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActLogin extends AppCompatActivity implements View.OnClickListener {

    private Button btnEntrar;
    private Button btnCadastrar;
    private TextView txtLogo;
    private EditText edtEmail;
    private EditText edtSenha;

    private String senha;
    private String email;
    private int cont;

    public static  String STATUS = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        getSupportActionBar().hide();

        //Travæ rotaçãø da tela
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnEntrar = findViewById(R.id.btnEntrar);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        txtLogo = findViewById(R.id.txtLogoCombo);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        //Chama metudo que altera fonte logo
        fontLogo();

        btnCadastrar.setOnClickListener( this );
        btnEntrar.setOnClickListener( this );
        getDate();
    }

    /*
     * Para que a nova fonte seja exibida na tela,
     * precisamos chamar um método na Activity que sobrescreva
     * o contexto base com um Wrapper da biblioteca.
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    //Altera fonte do txtLogo
    private void fontLogo(){

        Typeface font = Typeface.createFromAsset(getAssets(), "RagingRedLotusBB.ttf");
        txtLogo.setTypeface(font);
    }

    @Override
    public void onClick(View v) {

        if ( v.getId() == R.id.btnEntrar )
        {
            startVibrate(90);
            validateFields();

        }
        else if ( v.getId() == R.id.btnCadastrar )
        {
           startVibrate(90);
            Intent it = new Intent(this, ActSignup.class);
            startActivity(it);
        }
    }

    //Metudo que ativa vibração
    public void startVibrate(long time) {
        // cria um obj atvib que recebe seu valor de context
        Vibrator atvib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        atvib.vibrate(time);
    }

    public void validateFields(){

        //Declara variavel Recupra valores
        // Usando oque foi definido e referenciado
        email = edtEmail.getText().toString();
        senha = edtSenha.getText().toString();
        if(cont <= 2) {
            //desisão para tratar campos em brando Se campos em branco

            if (email.trim().isEmpty() || senha.trim().isEmpty()) {
                cont++;
                //Cria o alerta ao clicar no botão se ouver campos em braco
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setIcon(android.R.drawable.ic_dialog_alert);
                dlg.setTitle("Há campos em branco !");
                dlg.setMessage("Digite E-mail e senha para logar ou crie um caso não tenha," +
                        "clique em cadastrar e após cadastro logue se.");
                dlg.setNegativeButton("OK", null);
                dlg.show();
            } else {
                // mensagem(user+" logado !");
                msgShort("Seja Bem Vindo !");

                System.setProperty("STATUS_ENV",STATUS);

                Intent it = new Intent(this, ActPromotion.class);
                startActivity(it);

                clearFields();

                cont = 0;
            }//if campos
        }else {
            finaliza();
        }//if cont
    }

    private void finaliza(){
        msgShort("O R Notas foi finalisado");
       // System.clearProperty("codigoUser");
        finish();
    }

    private void msgShort(String msg) {

        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

    private void clearFields(){
        edtEmail.setText("");
        edtSenha.setText("");
    }

    private void getDate(){

        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HHmm");

        Calendar  cal = Calendar.getInstance();
        Date data_atual = cal.getTime();

        String hora_atual = dateFormat_hora.format(data_atual);

        Integer intHora = Integer.parseInt(hora_atual);

        if( intHora > 900  && intHora < 2200){
            STATUS = "Estamos atendendo !";
        }else{
            STATUS = "Não estamos atendendo agora !";
        }

    }
}
