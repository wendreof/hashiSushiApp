package com.example.hashisushi.views;

import android.app.AlertDialog;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hashisushi.R;
import com.example.hashisushi.utils.data.SecurityPreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dmax.dialog.SpotsDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActLogin extends AppCompatActivity implements View.OnClickListener {
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
    private SecurityPreferences shared;
    private Switch chkBxRememberPasswd;
    private String emailUser;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);

        this.shared = new SecurityPreferences(this);
        getSupportActionBar().hide();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  //Trava a rotaçãø da tela

        findViewByIds();

        fontLogo();  //Chama método que altera fonte logo

        this.userAuth = FirebaseAuth.getInstance();
        //userAuth.signOut();
        getDate();

        emailUser = this.shared.getStoredString("EmailUserSaved");

        setEmailUser();

        if (!edtEmail.getText().toString().equals(""))
        {
            edtSenha.requestFocus();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void fontLogo() //Altera fonte do txtLogo
    {
        Typeface font = Typeface.createFromAsset(getAssets(), "RagingRedLotusBB.ttf");
        txtLogo.setTypeface(font);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        this.setEmailUser();  //Carrega o e-mail de SharedPreferences
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
            startVibrate(90);
            Intent it = new Intent(this, ActSignup.class);
            startActivity(it);
        }
        else if (v.getId() == R.id.chkBxRememberPasswd)
        {
            if (chkBxRememberPasswd.isChecked())
            {
                this.shared.storeString("EmailUserSaved", edtEmail.getText().toString());
                //msgShort(emailUser);
                ShowMSG(getString(R.string.email_rememberd));
            }
            else
            {
                this.shared.storeString("EmailUserSaved", "");
                ShowMSG(getString(R.string.email_not_rememberd));
            }
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

                            msgShort(getString(R.string.welcome));
                            initPromotion();
                        }
                        else
                        {
                            msgShort(getString(R.string.error_to_access));
                        }
                    }
                });
    }

    public void testUserAuth()
    {
        if (userAuth.getCurrentUser() != null)
        {
            msgShort(getString(R.string.user_loged));

        }
        else
        {
            msgShort(getString(R.string.user_not_loged));
        }
    }

    private void initPromotion()
    {
        Intent it = new Intent(this, ActPromotion.class);
        startActivity(it);
    }

    //Método que ativa vibração
    public void startVibrate(long time)
    {
        // cria um obj atvib que recebe seu valor de context
        Vibrator atvib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        atvib.vibrate(time);
    }

    public void validateFields()
    {
        email = edtEmail.getText().toString();
        senha = edtSenha.getText().toString();

        if (cont <= 3)
        {
            if (email.trim().isEmpty() || senha.trim().isEmpty())
            {
                cont++;
                ShowMSG(getString(R.string.type_email_and_pass));
            }
            else
            {
                if (controlBtn == 'E')
                {
                    setDialog();
                    login(email, senha);

                }

                System.setProperty("STATUS_ENV", STATUS);
                clearFields();
                cont = 0;
            }
        }
        else
        {
            finaliza();
        }
    }

    private void setDialog(){

        dialog =  new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Carregando dados....")
                .setCancelable(false)
                .build();
        dialog.show();
    }

    private void finaliza()
    {
        msgShort(getString(R.string.app_finished));
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
            STATUS = getString(R.string.we_are_open_now);
        }
        else
        {
            STATUS = getString(R.string.we_are_not_open);
        }
    }

    private void ShowMSG(String msg)
    {
        Snackbar.make(ActLogin, msg, Snackbar.LENGTH_LONG).show();
    }

    private void setEmailUser()
    {
        if (!emailUser.equals(""))
        {
            edtEmail.setText(emailUser);
            chkBxRememberPasswd.setChecked(true);
        }
        else
        {
            edtEmail.setText(emailUser);
        }
    }

    private void findViewByIds()
    {
        btnEntrar = findViewById(R.id.btnEntrar);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        txtLogo = findViewById(R.id.txtLogoC);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        ActLogin = findViewById(R.id.ActLogin);
        chkBxRememberPasswd = findViewById(R.id.chkBxRememberPasswd);

        btnCadastrar.setOnClickListener(this);
        btnEntrar.setOnClickListener(this);
        chkBxRememberPasswd.setOnClickListener(this);
    }
}
