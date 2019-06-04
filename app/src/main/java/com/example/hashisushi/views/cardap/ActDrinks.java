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
import com.example.hashisushi.views.ActOrder;

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

public class ActDrinks extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton flotBntVoltarD;
    private FloatingActionButton flotBntEdtPersoD;
    private FloatingActionButton flotBntFinishD;
    private FloatingActionButton flotBntOrderSaleD;

    private TextView txtCardapD;
    private TextView txtLogoD;
    private TextView txtDrinks;

    private DatabaseReference reference ;
    private List<Product> productsList = new ArrayList<Product>();
    private RecyclerView lstDrinks;
    private AdapterProduct adapterProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_drinks);

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
        lstDrinks.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        lstDrinks,
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
        lstDrinks.setLayoutManager(new LinearLayoutManager(this));
        lstDrinks.setHasFixedSize(true);
        adapterProduct = new AdapterProduct(productsList, this);
        lstDrinks.setAdapter( adapterProduct );

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {

        if ( v.getId() == R.id.flotBntVoltarD ) {

            startVibrate(90);
            //Intent it = new Intent(ActPlatAce.this, ActPlatHot.class);
            //it.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            //startActivity(it);
            finish();

        }
        if ( v.getId() == R.id.flotBntFinishD) {

            startVibrate(90);
            Intent it = new Intent( this, ActOrder.class );
            startActivity( it );

        }if(v.getId() == R.id.flotBntEdtPersoD){
            startVibrate(90);
            Intent it = new Intent(this, ActSignup.class);
            startActivity(it);
        } else if(v.getId() == R.id.flotBntOrderSaleD) {

            startVibrate(90);
            openOrder();

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
        txtLogoD.setTypeface(font);
        txtCardapD.setTypeface(font);
        txtDrinks.setTypeface(font);
    }

    private void openOrder(){

        Intent intent = new Intent(ActDrinks.this,ActOrder.class);
        //Passa efeitos de transzição
        ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                R.anim.fade_in,R.anim.mover_direita);
        ActivityCompat.startActivity(ActDrinks.this,intent,actcompat.toBundle());
        //startActivity(intent);


    }

    //oa clicar em voltar chama efeito de transição
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.mover_esquerda,R.anim.fade_out);
    }

    public void initDB() {
        FirebaseApp.initializeApp(ActDrinks.this);
        this.reference = FirebaseDatabase.getInstance().getReference();
    }

    private void initComponent(){
        flotBntVoltarD = findViewById(R.id.flotBntVoltarD);
        flotBntEdtPersoD = findViewById(R.id.flotBntEdtPersoD);
        flotBntFinishD = findViewById(R.id.flotBntFinishD);
        flotBntOrderSaleD = findViewById(R.id.flotBntOrderSaleD);

        flotBntVoltarD.setOnClickListener(this);
        flotBntEdtPersoD.setOnClickListener(this);
        flotBntFinishD.setOnClickListener(this);
        flotBntOrderSaleD.setOnClickListener(this);

        txtCardapD = findViewById(R.id.txtCardapD);
        txtLogoD = findViewById(R.id.txtLogoD);
        txtDrinks = findViewById(R.id.txtDrinks);

        lstDrinks = findViewById(R.id.LstDriks);

    }

    public void initSearch(){
        //retorna usuarios
        DatabaseReference productDB = reference.child("product");
        //retorna o no setado
        // DatabaseReference usersSearch = users.child("0001");
        Query querySearch = productDB.orderByChild("type").equalTo("Bebidas");

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
