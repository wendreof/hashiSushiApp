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
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hashisushi.R;
import com.example.hashisushi.adapter.AdapterProduct;
import com.example.hashisushi.listener.RecyclerItemClickListener;
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

public class ActTemakis extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton flotBntVoltarT;
    private FloatingActionButton flotBntEdtPersoT;
    private FloatingActionButton flotBntPontsT;
    private FloatingActionButton flotBntComboT;

    private TextView txtCardapT;
    private TextView txtLogoT;
    private TextView txtTemakis;

    private DatabaseReference reference ;
    private List<Product> productsList = new ArrayList<Product>();
    private RecyclerView lstTemakis;
    private AdapterProduct adapterProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_temakis);
        getSupportActionBar().hide();

        //Travæ rotaçãø da tela
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initComponent();
        initDB();
        initSearch();
        fontLogo();
        recyclerViewConfig();
        recycleOnclick();

    }

    private void recycleOnclick(){
        //Adiciona evento de clique no recyclerview
        lstTemakis.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        lstTemakis,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                msgShort("Posiçao :"+position);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                Product produtoSelecionado = productsList.get(position);
                                msgShort("Produto :"+produtoSelecionado);
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

    }

    private void recyclerViewConfig(){

        //Configura recyclerview
        lstTemakis.setLayoutManager(new LinearLayoutManager(this));
        lstTemakis.setHasFixedSize(true);
        adapterProduct = new AdapterProduct(productsList, this);
        lstTemakis.setAdapter( adapterProduct );

    }

    @Override
    public void onClick(View v) {

        if ( v.getId() == R.id.flotBntVoltarT ) {

            startVibrate(90);
            //Intent it = new Intent(ActPlatAce.this, ActPlatHot.class);
            //it.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            //startActivity(it);
            finish();

        }
        if ( v.getId() == R.id.flotBntPontsT ) {

            startVibrate(90);
            Intent it = new Intent( this, ActPoints.class );
            startActivity( it );

        }if(v.getId() == R.id.flotBntEdtPersoT){
            startVibrate(90);
            Intent it = new Intent(this, ActSignup.class);
            startActivity(it);
        } else if(v.getId() == R.id.flotBntComboT) {

            startVibrate(90);
            openCombo();

        }
    }
    //Metudo que ativa vibração
    public void startVibrate(long time) {
        // cria um obj atvib que recebe seu valor de context
        Vibrator atvib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        atvib.vibrate(time);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    //Altera fonte do txtLogo
    private void fontLogo(){

        Typeface font = Typeface.createFromAsset(getAssets(), "RagingRedLotusBB.ttf");
        txtCardapT.setTypeface(font);
        txtLogoT.setTypeface(font);
        txtTemakis.setTypeface(font);
    }

    private void openCombo(){

        Intent intent = new Intent(ActTemakis.this,ActCombo.class);
        //Passa efeitos de transzição
        ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                R.anim.fade_in,R.anim.mover_direita);
        ActivityCompat.startActivity(ActTemakis.this,intent,actcompat.toBundle());
        //startActivity(intent);


    }

    //oa clicar em voltar chama efeito de transição
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.mover_esquerda,R.anim.fade_out);
    }
    public void initDB() {
        FirebaseApp.initializeApp(ActTemakis.this);
        this.reference = FirebaseDatabase.getInstance().getReference();
    }

    private void initComponent(){

        flotBntVoltarT = findViewById(R.id.flotBntVoltarT);
        flotBntEdtPersoT = findViewById(R.id.flotBntEdtPersoT);
        flotBntPontsT = findViewById(R.id.flotBntPontsT);
        flotBntComboT = findViewById(R.id.flotBntComboT);

        flotBntVoltarT.setOnClickListener(this);
        flotBntEdtPersoT.setOnClickListener(this);
        flotBntPontsT.setOnClickListener(this);
        flotBntComboT.setOnClickListener(this);

        txtCardapT = findViewById(R.id.txtCardapT);
        txtLogoT = findViewById(R.id.txtLogoT);
        txtTemakis = findViewById(R.id.txtTemakis);

        lstTemakis = findViewById(R.id.LstTemakis);

    }

    public void initSearch(){
        //retorna usuarios
        DatabaseReference productDB = reference.child("product");
        //retorna o no setado
        // DatabaseReference usersSearch = users.child("0001");
        Query querySearch = productDB.orderByChild("type").equalTo("Temakis");

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
