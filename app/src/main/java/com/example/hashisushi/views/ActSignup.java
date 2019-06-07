package com.example.hashisushi.views;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hashisushi.R;
import com.example.hashisushi.dao.FirebaseConfig;
import com.example.hashisushi.dao.UserFirebase;
import com.example.hashisushi.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActSignup extends AppCompatActivity implements OnClickListener
{
    private EditText userName, userCPF, userBornDate;
    private EditText userAddressStreet, userAddressNeighborhood, userAddressNumber;
    private EditText userAddressCity, userAddressCEP, userAddressState;
    private EditText userEmail, userPhone, userPassword, userPasswordRetype, userReferencePoint;
    private TextView txtCad, txtCadLogo;
    private Button btnSignUp;
    private ScrollView ActSignUp;
    private User user;
    private FirebaseAuth auth;
    private String retornIdUser;
    DatabaseReference reference;
    private String retornEmailUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.act_signup);

        this.auth = FirebaseAuth.getInstance();

        //reference db and recover value
        retornEmailUser = UserFirebase.getUserCorrent().getEmail();
        retornIdUser = UserFirebase.getIdUser();
        reference = FirebaseConfig.getFirebase();
        //Travæ rotaçãø da tela
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        findViewById();

        fontLogo();
        btnSignUp.setOnClickListener(this);

        userEmail.setText(retornEmailUser);
    }

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    //Altera fonte do txtLogo
    private void fontLogo()
    {
        Typeface font = Typeface.createFromAsset(getAssets(), "RagingRedLotusBB.ttf");
        txtCad.setTypeface(font);
        txtCadLogo.setTypeface(font);
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.button_user_signup)
        {
            if (userName.getText().toString().equals(""))
            {
                ShowMSG();
                userName.setError(getString(R.string.your_name));
            }
            else if (userCPF.getText().toString().equals(""))
            {
                ShowMSG();
                userCPF.setError(getString(R.string.your_cpf));
            }
            else if (userEmail.getText().toString().equals(""))
            {
                ShowMSG();
                userEmail.setError(getString(R.string.your_email2));
            }
            else if (userPhone.getText().toString().equals(""))
            {
                ShowMSG();
                userPhone.setError(getString(R.string.your_phone));
            }
            else if (userPassword.getText().toString().equals(""))
            {
                ShowMSG();
                userPassword.setError(getString(R.string.your_password));
            }
            else if (userPassword.getText().length() < 6)
            {
                Snackbar.make(ActSignUp, R.string.wrong_pass, Snackbar.LENGTH_LONG).show();
                userPassword.setError(getString(R.string.try_another_pass));
            }
            else if (!userPasswordRetype.getText().toString().equals(userPassword.getText().toString()))
            {
                Snackbar.make(ActSignUp, R.string.pass_not_equals2, Snackbar.LENGTH_LONG).show();
                userPasswordRetype.setError(getString(R.string.pass_not_equals));
            }
            else
             {
                 addUser();
             }
        }
    }

    private void addUser()
    {
        try
        {
                user = new User();
                user.setIdUser(retornIdUser);
                user.setName(userName.getText().toString());
                user.setBornDate(userBornDate.getText().toString());
                if(userReferencePoint.getText().toString().equals(""))
                {
                    user.setAddress(userAddressStreet.getText().toString());
                }
                else
                {
                    user.setAddress(userAddressStreet.getText().toString() + " - Ponto de referência: " + userReferencePoint.getText().toString());
                }
                user.setNeigthborhood(userAddressNeighborhood.getText().toString());
                user.setNumberHome(userAddressNumber.getText().toString());
                user.setCity(userAddressCity.getText().toString());
                user.setCep(userAddressCEP.getText().toString());
                user.setState(userAddressState.getText().toString());
                user.setPhone(userPhone.getText().toString());
                user.setEmail(userEmail.getText().toString());
                user.setIsAdmin(false); //----verificar com Wendreo
                user.setPassword(userPassword.getText().toString());
                user.setCpf(userCPF.getText().toString());

                user.setPonts(0);
                //novo metudo para salvar user
                user.salvarUser();

                if (auth.getCurrentUser() != null)
                {
                    Intent it = new Intent(getApplicationContext(), ActPromotion.class);
                    startActivity(it);
                    msgShort("Cadastro finalizado com sucesso!");
                }
                else {
                    Intent it = new Intent(getApplicationContext(), ActLogin.class);
                    startActivity(it);
                    msgShort("Novo login é necessario.");
                }

        }
        catch (Exception erro)
        {

            Snackbar.make(ActSignUp, R.string.registration_error, Snackbar.LENGTH_LONG).show();
        }
    }

    private void ShowMSG()
    {
        Snackbar.make(ActSignUp, R.string.preencha_os_campos, Snackbar.LENGTH_LONG).show();
    }

    private void findViewById()
    {
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
        userPasswordRetype = findViewById(R.id.user_password_RETYPE);
        userEmail = findViewById(R.id.user_email);
        userPhone = findViewById(R.id.user_phone);
        btnSignUp = findViewById(R.id.button_user_signup);
        ActSignUp = findViewById(R.id.ActSignUp);
        userReferencePoint = findViewById(R.id.user_reference_point);
        txtCad = findViewById(R.id.txtCad);
        txtCadLogo = findViewById(R.id.txtCadLogo);
    }

    private void msgShort(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
