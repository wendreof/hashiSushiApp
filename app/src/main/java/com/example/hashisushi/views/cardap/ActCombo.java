package com.example.hashisushi.views.cardap;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hashisushi.R;
import com.example.hashisushi.adapter.AdapterProduct;
import com.example.hashisushi.adapter.ProductListAdapter;
import com.example.hashisushi.model.Product;

import com.example.hashisushi.views.ActPoints;
import com.example.hashisushi.views.ActSignup;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActCombo extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton flotBntVoltarC;
    private FloatingActionButton flotBntEdtPersoC;
    private FloatingActionButton flotBntPontsC;
    private FloatingActionButton flotBntDrinksC;

    private TextView txtCardapC;
    private TextView txtLogoC;
    private TextView txtCombo;

    private DatabaseReference reference ;
    private List<Product> productsList = new ArrayList<Product>();
    private RecyclerView lstCombo;
    private AdapterProduct adapterProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_combo);

        getSupportActionBar().hide();

        //Travæ rotaçãø da tela
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initComponent();
        initDB();
        initSearch();
        fontLogo();
        recyclerViewConfig();
    }

    private void recyclerViewConfig(){

        //Configura recyclerview
        lstCombo.setLayoutManager(new LinearLayoutManager(this));
        lstCombo.setHasFixedSize(true);
        adapterProduct = new AdapterProduct(productsList, this);
        lstCombo.setAdapter( adapterProduct );

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {

        if ( v.getId() == R.id.flotBntVoltarC ) {

            startVibrate(90);
            //Intent it = new Intent(ActPlatAce.this, ActPlatHot.class);
            //it.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            //startActivity(it);
            finish();

        }
        if ( v.getId() == R.id.flotBntPontsC ) {

            startVibrate(90);
            Intent it = new Intent( this, ActPoints.class );
            startActivity( it );

        }if(v.getId() == R.id.flotBntEdtPersoC) {
            startVibrate(90);
            Intent it = new Intent(this, ActSignup.class);
            startActivity(it);
        } if(v.getId() == R.id.flotBntDrinksC) {

            startVibrate(90);
            openDrinks();

        }
    }
    //Metudo que ativa vibração
    public void startVibrate(long time) {
        // cria um obj atvib que recebe seu valor de context
        Vibrator atvib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        atvib.vibrate(time);
    }

    //Altera fonte do txtLogo
    private void fontLogo(){

        Typeface font = Typeface.createFromAsset(getAssets(), "RagingRedLotusBB.ttf");
        txtCardapC.setTypeface(font);
        txtCombo.setTypeface(font);
        txtLogoC.setTypeface(font);
    }

    private void openDrinks(){

        Intent intent = new Intent(ActCombo.this,ActDrinks.class);
        //Passa efeitos de transzição
        ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                R.anim.fade_in,R.anim.mover_direita);
        ActivityCompat.startActivity(ActCombo.this,intent,actcompat.toBundle());
        //startActivity(intent);

    }

    //oa clicar em voltar chama efeito de transição
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.mover_esquerda,R.anim.fade_out);
    }

    public void initDB() {
        FirebaseApp.initializeApp(ActCombo.this);
        this.reference = FirebaseDatabase.getInstance().getReference();
    }

    private void initComponent(){

        flotBntVoltarC = findViewById(R.id.flotBntVoltarC);
        flotBntEdtPersoC = findViewById(R.id.flotBntEdtPersoC);
        flotBntPontsC = findViewById(R.id.flotBntPontsC);
        flotBntDrinksC = findViewById(R.id.flotBntDrinksC);

        txtCardapC = findViewById(R.id.txtCardapC);
        txtLogoC = findViewById(R.id.txtLogoC);
        txtCombo = findViewById(R.id.txtCombo);

        flotBntVoltarC.setOnClickListener(this);
        flotBntEdtPersoC.setOnClickListener(this);
        flotBntPontsC.setOnClickListener(this);
        flotBntDrinksC.setOnClickListener(this);

        lstCombo = findViewById(R.id.lstCombo);

    }

    public void initSearch(){
        //retorna usuarios
        DatabaseReference productDB = reference.child("product");
        //retorna o no setado
        // DatabaseReference usersSearch = users.child("0001");
        Query querySearch = productDB.orderByChild("type").equalTo("Combo");

        productsList.clear();
        //cria um ouvinte
        querySearch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Product product = objSnapshot.getValue(Product.class);
                    productsList.add(product);
                }

                adapterProduct.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                msgShort("Houve algum erro :" + databaseError);
            }
        });
    }
    private void msgShort(String msg) {

        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

}
