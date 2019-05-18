package com.example.hashisushi.views;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hashisushi.R;
import com.example.hashisushi.views.navigation.Navigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActLogin extends AppCompatActivity implements View.OnClickListener
{
    public static String STATUS = null;
    private Button btnEntrar;
    private Button btnCadastrar;
    private TextView txtLogo;
    private EditText edtEmail;
    private EditText edtSenha;
    private String senha;
    private String email;
    private int cont;
    private char controlBtn;
    private ConstraintLayout ActLogin;
    private FirebaseAuth userAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        getSupportActionBar().hide();

        //Travæ rotaçãø da tela
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnEntrar = findViewById(R.id.btnEntrar);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        txtLogo = findViewById(R.id.txtLogoC);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        ActLogin = findViewById(R.id.ActLogin);
        //Chama metudo que altera fonte logo
        fontLogo();

        this.userAuth = FirebaseAuth.getInstance();
        //userAuth.signOut();

        btnCadastrar.setOnClickListener(this);
        btnEntrar.setOnClickListener(this);
        getDate();
    }

    /*
     * Para que a nova fonte seja exibida na tela,
     * precisamos chamar um método na Activity que sobrescreva
     * o contexto base com um Wrapper da biblioteca.
     */
    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    //Altera fonte do txtLogo
    private void fontLogo()
    {
        Typeface font = Typeface.createFromAsset(getAssets(), "RagingRedLotusBB.ttf");
        txtLogo.setTypeface(font);
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.btnEntrar)
        {
            controlBtn = 'E';
            startVibrate(90);
            validateFields();

        }
        else if (v.getId() == R.id.btnCadastrar)
        {
            controlBtn = 'C';
            startVibrate(90);
            //validateFields();
            Intent it = new Intent(this, ActSignup.class);
            startActivity(it);
        }
    }

    //login user in firebase
    public void login(String email, String senha)
    {
        userAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(ActLogin.this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            msgShort("Bem vindo! ;)");
                            initPromotion();
                        }
                        else
                        {
                            msgShort("Falha no acessar o app! :(");
                        }
                    }
                });
    }

    public void yesUserAuth()
    {
        if (userAuth.getCurrentUser() != null)
        {
            msgShort("Usuário Logado!");

        }
        else
        {
            msgShort("Usuário não esta Logado !");
        }
    }

    private void initPromotion()
    {
        //Intent it = new Intent(this, ActPromotion.class);
        Intent it = new Intent(this, Navigation.class);
        startActivity(it);
    }

    //Metudo que ativa vibração
    public void startVibrate(long time)
    {
        // cria um obj atvib que recebe seu valor de context
        Vibrator atvib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        atvib.vibrate(time);
    }

    public void validateFields()
    {
        //Declara variavel Recupra valores
        // Usando oque foi definido e referenciado
        email = edtEmail.getText().toString();
        senha = edtSenha.getText().toString();
        if (cont <= 3)
        {
            //desisão para tratar campos em brando Se campos em branco

            if (email.trim().isEmpty() || senha.trim().isEmpty())
            {
                cont++;
              //  msgShort("Por favor, digite seu e-mail e senha para entrar ou cadastre-se");
                ShowMSG("Por favor, digite e-mail e senha para entrar ou cadastre-se");
            }
            else
            {
                // mensagem(user+" logado !");
                //msgShort("Seja Bem Vindo !");

                if (controlBtn == 'E')
                {
                    login(email, senha);
                }
                else if (controlBtn == 'C')
                {
                   // addUserLogin(email, senha);
                }
                System.setProperty("STATUS_ENV", STATUS);
                clearFields();
                cont = 0;
            }//if campos
        }
        else
        {
            finaliza();
        }//if cont
    }

    private void finaliza()
    {
        msgShort("Hashi Sushi Finalizado !");
        // System.clearProperty("codigoUser");
        finish();
    }

    private void msgShort(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void clearFields()
    {
        edtEmail.setText("");
        edtSenha.setText("");
    }

    private void getDate()
    {
        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HHmm");

        Calendar cal = Calendar.getInstance();
        Date data_atual = cal.getTime();

        String hora_atual = dateFormat_hora.format(data_atual);
        Integer intHora = Integer.parseInt(hora_atual);

        if (intHora > 900 && intHora < 2200)
        {
            STATUS = "Estamos atendendo!";
        }
        else
        {
            STATUS = "Não estamos atendendo agora!";
        }
    }

    private void ShowMSG(String msg)
    {
        Snackbar.make(ActLogin, msg, Snackbar.LENGTH_LONG).show();
    }
}
