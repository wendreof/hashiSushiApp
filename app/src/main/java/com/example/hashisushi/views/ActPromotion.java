package com.example.hashisushi.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hashisushi.R;
import com.example.hashisushi.adapter.AdapterProduct;
import com.example.hashisushi.listener.RecyclerItemClickListener;
import com.example.hashisushi.model.Product;
import com.example.hashisushi.views.cardap.ActCombo;
import com.example.hashisushi.views.cardap.ActDrinks;
import com.example.hashisushi.views.cardap.ActPlatAce;
import com.example.hashisushi.views.cardap.ActPlatHot;
import com.example.hashisushi.views.cardap.ActTemakis;
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

public class ActPromotion extends AppCompatActivity implements View.OnClickListener
{
    private TextView txtTitle;
    private TextView txtStatus;
    private FloatingActionButton flotBntSalesCardap;
    private FloatingActionButton flotBntFinishProm;
    private FloatingActionButton flotBntExitP;
    private  FloatingActionButton flotBntEditPersonP;
    private System status;

    private DatabaseReference reference ;
    private List<Product> productsList = new ArrayList<Product>();
    private RecyclerView list_produsts;
    private AdapterProduct adapterProduct;
    private Product product;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_promotion);

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

        // getSupportActionBar().hide();
        //Travæ rotaçãø da tela
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        startComponet();
        initDB();
        initSearch();

        getStatus();
//        fontLogo();
        recyclerViewConfig();

        flotBntExitP.setOnClickListener(this);
        flotBntFinishProm.setOnClickListener(this);
        flotBntSalesCardap.setOnClickListener(this);
        flotBntEditPersonP.setOnClickListener(this);

        recycleOnclick();


    }//end oncreat

    private void recycleOnclick(){
        //Adiciona evento de clique no recyclerview
        list_produsts.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        list_produsts,
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
        list_produsts.setLayoutManager(new LinearLayoutManager(this));
        list_produsts.setHasFixedSize(true);
        adapterProduct = new AdapterProduct(productsList, this);
        list_produsts.setAdapter( adapterProduct );

    }

    private void startComponet(){
        txtStatus = findViewById( R.id.txtEstatus );
        txtTitle = findViewById( R.id.txtTitleReg);

        flotBntExitP = findViewById(R.id.flotBntExitP);
        flotBntFinishProm = findViewById(R.id.flotBntFinishProm);
        flotBntSalesCardap = findViewById( R.id.flotBntSalesCardap);
        flotBntEditPersonP = findViewById(R.id.flotBntEditPersonP);
        //RecyclerView---
        list_produsts = findViewById(R.id.list_produsts);
    }


    private void getStatus(){
       String stt = System.getProperty("STATUS_ENV");
       txtStatus.setText(stt);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    //Altera fonte do txtLogo
    private void fontLogo()
    {
        Typeface font = Typeface.createFromAsset( getAssets(), "RagingRedLotusBB.ttf" );
//        txtTitle.setTypeface( font );
    }

    @Override
    public void onClick(View v) {

        if ( v.getId() == R.id.flotBntSalesCardap) {

            startVibrate(90);
            openSaleCardap();

        }
        if ( v.getId() == R.id.flotBntFinishProm) {

            startVibrate(90);
            Intent it = new Intent( this, ActOrder.class );
            startActivity( it );

        }if(v.getId() == R.id.flotBntEditPersonP){

            startVibrate(90);
            //Intent it = new Intent(this, ActSignup.class);
            Intent it = new Intent(this, ActRegProd.class);
            startActivity(it);

        } else if(v.getId() == R.id.flotBntExitP) {

            startVibrate(90);
            //finaliza a activity atual e todas a baixo
            this.finishAffinity();
        }
    }

    //Metudo que ativa vibração
    public void startVibrate(long time) {
        // cria um obj atvib que recebe seu valor de context
        Vibrator atvib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        atvib.vibrate(time);
    }

    private void openSaleCardap(){

        Intent intent = new Intent(ActPromotion.this, ActSaleCardap.class);
        //Passa efeitos de transzição
        ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                R.anim.fade_in,R.anim.mover_direita);
        ActivityCompat.startActivity(ActPromotion.this,intent,actcompat.toBundle());
        //startActivity(intent);

    }

    public void initDB() {
        FirebaseApp.initializeApp(ActPromotion.this);
        this.reference = FirebaseDatabase.getInstance().getReference();
    }

    public void initSearch(){
        //retorna usuarios
        DatabaseReference productDB = reference.child("product");
        //retorna o no setado
        Query querySearch = productDB.orderByChild("promotion").equalTo(true);

        //cria um ouvinte
        querySearch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Product p = objSnapshot.getValue(Product.class);

                    productsList.add(p);
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

    //==> MENUS
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_promotion, menu);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //Sobre

        if(id == R.id.menu_enter){
            Intent it = new Intent(this, ActSaleCardap.class);
            startActivity(it);
            return  true;
        }

        if(id == R.id.menu_plat_hot){
            Intent it = new Intent(this, ActPlatHot.class);
            startActivity(it);
            return  true;
        }

        if(id == R.id.menu_plat_ace){
            Intent it = new Intent(this, ActPlatAce.class);
            startActivity(it);
            return  true;
        }

        if(id ==R.id.menu_combo){
            Intent it = new Intent(this, ActCombo.class);
            startActivity(it);
            return true;
        }

        if(id ==R.id.menu_drinks){
            Intent it = new Intent(this, ActDrinks.class);
            startActivity(it);
            return true;
        }
        if(id == R.id.menu_temakis){
            Intent it = new Intent(this, ActTemakis.class);
            startActivity(it);
            return true;
        }

        if(id ==R.id.menu_edit_cadastro){
            Intent it = new Intent(this, ActSignup.class);
            startActivity(it);
            return true;
        }

        if(id ==R.id.menu_cad_prod){
            Intent it = new Intent(this, ActRegProd.class);
            startActivity(it);
            return true;
        }
        if(id ==R.id.menu_points){
            Intent it = new Intent(this, ActPoints.class);
            startActivity(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //==>FIM MENUS

}
