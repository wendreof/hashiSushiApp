package com.example.hashisushi.views;

import android.content.pm.ActivityInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;

import com.example.hashisushi.R;

public class ActSignup extends AppCompatActivity implements OnClickListener {

    private EditText userName;
    private EditText userCPF;
    private EditText userBornDate;
    private EditText userAddressStreet;
    private EditText userAddressNumber;
    private EditText userAddressNeighborhood;
    private EditText userAddressCity;
    private EditText userAddressCEP;
    private EditText userAddressState;
    private CheckBox userSexF;
    private CheckBox userSexM;
    private EditText userEmail;
    private EditText userPhone;
    private Button btnSignUp;
    private ScrollView ActSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.act_signup);

        //Travæ rotaçãø da tela
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        findViewById();

        btnSignUp.setOnClickListener(this);

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
        userSexF = findViewById(R.id.checkbox_user_sex_F);
        userSexM = findViewById(R.id.checkbox_user_sex_M);
        userEmail = findViewById(R.id.user_email);
        userPhone = findViewById(R.id.user_phone);
        btnSignUp = findViewById(R.id.button_user_signup);
        ActSignUp = findViewById(R.id.ActSignUp);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_user_signup) {

            if (userName.getText().toString().equals("")|| userCPF.getText().toString().equals("")
                 || userEmail.getText().toString().equals("") || userPhone.getText().toString().equals("")) {

                ShowMSG();
                userName.setError("Informe o seu nome");
                userCPF.setError("Informe o seu CPF");
                userEmail.setError("Informe o seu E-mail");
                userPhone.setError("Informe o seu Telefone");
            }

        }
    }

    private void ShowMSG() {
        Snackbar.make(ActSignUp, "Por favor preencha os campos solicitados", Snackbar.LENGTH_LONG).show();
    }
}
