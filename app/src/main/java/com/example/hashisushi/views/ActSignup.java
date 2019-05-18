package com.example.hashisushi.views;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hashisushi.R;
import com.example.hashisushi.dao.UserDao;
import com.example.hashisushi.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActSignup extends AppCompatActivity implements OnClickListener {

    private static int TIME_OUT = 1000; //Time to launch the another activity
    private EditText userName, userCPF, userBornDate;
    private EditText userAddressStreet, userAddressNeighborhood, userAddressNumber;
    private EditText userAddressCity, userAddressCEP, userAddressState;
    private EditText userEmail, userPhone, userPassword;
    private TextView txtCad;
    private TextView txtCadLogo;
    private Button btnSignUp;
    private ScrollView ActSignUp;
    private ConstraintLayout ActLogin;

    private User user;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.act_signup);

        this.auth = FirebaseAuth.getInstance();

        //Travæ rotaçãø da tela
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        findViewById();
        txtCad = findViewById(R.id.txtCad);
        txtCadLogo = findViewById(R.id.txtCadLogo);

        fontLogo();
        btnSignUp.setOnClickListener(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    //Altera fonte do txtLogo
    private void fontLogo() {
        Typeface font = Typeface.createFromAsset(getAssets(), "RagingRedLotusBB.ttf");
        txtCad.setTypeface(font);
        txtCadLogo.setTypeface(font);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_user_signup) {
            if (userName.getText().toString().equals("")) {
                ShowMSG();
                userName.setError(getString(R.string.your_name));
            } else if (userCPF.getText().toString().equals("")) {
                ShowMSG();
                userCPF.setError(getString(R.string.your_cpf));
            } else if (userEmail.getText().toString().equals("")) {
                ShowMSG();
                userEmail.setError(getString(R.string.your_email2));
            } else if (userPhone.getText().toString().equals("")) {
                ShowMSG();
                userPhone.setError(getString(R.string.your_phone));
            } else if (userPassword.getText().toString().equals("")) {
                ShowMSG();
                userPassword.setError(getString(R.string.your_password));
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addUser();
                    }
                }, TIME_OUT);
            }
        }
    }

    private void addUser() {
        try {

            if (userPassword.getText().length() < 6) {
                Snackbar.make(ActSignUp, "A senha deve conter no mínimo 6 caracteres", Snackbar.LENGTH_LONG).show();
            } else {
                user = new User();
                user.setIdUser(0);
                user.setName(userName.getText().toString());
                user.setBornDate(userBornDate.getText().toString());
                user.setAddress(userAddressStreet.getText().toString());
                user.setNumberHome(userAddressNumber.getText().toString());
                user.setCity(userAddressCity.getText().toString());
                user.setCep(userAddressCEP.getText().toString());
                user.setState(userAddressState.getText().toString());
                user.setPhone(userPhone.getText().toString());
                user.setEmail(userEmail.getText().toString());
                user.setPassword(userPassword.getText().toString());
                user.setPonts(0);

                UserDao userDao = new UserDao();

                //Cadastra os dados do Usuário
                userDao.addUser(user);

                //Cadastra Login e Senha do usuário
                addUserLogin(user.getEmail(), user.getPassword());

           //     Snackbar.make(ActSignUp, R.string.registration_completed, Snackbar.LENGTH_LONG).show();
                Intent it = new Intent(getApplicationContext(), ActLogin.class);
                startActivity(it);

               msgShort("Seu cadastro foi efetuado com sucesso " + user.getName());
            }

        } catch (Exception erro) {
            msgShort("Erro ao realizar o cadastro :( " + erro);
            Snackbar.make(ActSignUp, R.string.registration_error, Snackbar.LENGTH_LONG).show();
        }
    }

    //create user in firebase
    public void addUserLogin(String email, String senha) {

        auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(ActSignup.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Intent it = new Intent(getApplicationContext(), ActLogin.class);
                            startActivity(it);
                            Log.i("Sucesso", "Seu cadastro foi efetuado com sucesso!" + user.getName());
                        } else {
                            Log.i("Erro", "Infelizmente não foi possível concluir o cadastro :(");
                        }
                    }
                });
    }

    private void msgShort(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void ShowMSG() {
        Snackbar.make(ActSignUp, R.string.preencha_os_campos, Snackbar.LENGTH_LONG).show();
    }

    private void findViewById() {
        userName = findViewById(R.id.user_name);
        userCPF = findViewById(R.id.user_cpf);
        userBornDate = findViewById(R.id.user_born_date);
        userAddressStreet = findViewById(R.id.user_address_street);
        userAddressNumber = findViewById(R.id.user_address_number);
        userAddressNeighborhood = findViewById(R.id.user_address_neighborhood);
        userAddressCity = findViewById(R.id.user_address_city);
        userAddressCEP = findViewById(R.id.user_cep);
        userAddressState = findViewById(R.id.user_adress_state);
        userPassword = findViewById(R.id.user_password);
        userEmail = findViewById(R.id.user_email);
        userPhone = findViewById(R.id.user_phone);
        btnSignUp = findViewById(R.id.button_user_signup);
        ActSignUp = findViewById(R.id.ActSignUp);
        ActLogin = findViewById(R.id.ActLogin);
    }
}
