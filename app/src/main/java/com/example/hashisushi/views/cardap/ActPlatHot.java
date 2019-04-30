package com.example.hashisushi.views.cardap;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hashisushi.R;
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

public class ActPlatHot extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton flotBntVoltarPh;
    private FloatingActionButton flotBntEdtPersoPh;
    private FloatingActionButton flotBntPontsPh;
    private FloatingActionButton flotBntPlanAcePh;

    private TextView txtCardapPh;
    private TextView txtLogoPh;
    private TextView txtPlatHot;

    private DatabaseReference reference ;
    private List<Product> productsList = new ArrayList<Product>();
    private ListView lstPlaHot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_plat_hot);

        getSupportActionBar().hide();

        //Hold  rotat screen
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initComponent();
        initDB();
        initSearch();

        flotBntVoltarPh.setOnClickListener(this);
        flotBntEdtPersoPh.setOnClickListener(this);
        flotBntPontsPh.setOnClickListener(this);
        flotBntPlanAcePh.setOnClickListener(this);

        fontLogo();

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {

        if ( v.getId() == R.id.flotBntVoltarPh ) {

            startVibrate(90);
            //Intent it = new Intent(ActPlatHot.this, ActSaleCardap.class);
            //it.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
           // startActivity(it);
            finish();
        }
        if ( v.getId() == R.id.flotBntPontsPh ) {

            startVibrate(90);
            Intent it = new Intent( this, ActPoints.class);
            startActivity( it );

        }if(v.getId() == R.id.flotBntEdtPersoPh){
            startVibrate(90);
            Intent it = new Intent(this, ActSignup.class);
            startActivity(it);
        } else if(v.getId() == R.id.flotBntPlanAcePh) {

            startVibrate(90);
            openPlatAce();

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
        txtCardapPh.setTypeface(font);
        txtLogoPh.setTypeface(font);
        txtPlatHot.setTypeface(font);
    }

    private void openPlatAce(){

        Intent intent = new Intent(ActPlatHot.this,ActPlatAce.class);
        //Passa efeitos de transzição
        ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                R.anim.fade_in,R.anim.mover_direita);
        ActivityCompat.startActivity(ActPlatHot.this,intent,actcompat.toBundle());

    }

    //oa clicar em voltar chama efeito de transição
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.mover_esquerda,R.anim.fade_out);
    }

    public void initDB() {
        FirebaseApp.initializeApp(ActPlatHot.this);
        this.reference = FirebaseDatabase.getInstance().getReference();
    }

    private void initComponent(){
        txtCardapPh = findViewById(R.id.txtCardapPh);
        txtLogoPh = findViewById(R.id.txtLogoPh);
        txtPlatHot = findViewById(R.id.txtPlaHot);

        flotBntVoltarPh = findViewById(R.id.flotBntVoltarPh);
        flotBntEdtPersoPh = findViewById(R.id.flotBntEdtPersoPh);
        flotBntPontsPh = findViewById(R.id.flotBntPontsPh);
        flotBntPlanAcePh = findViewById(R.id.flotBntPlanAcePh);

        lstPlaHot = findViewById(R.id.LstPlatHot);

    }

    public void initSearch(){
        //retorna usuarios
        DatabaseReference productDB = reference.child("product");
        //retorna o no setado
        // DatabaseReference usersSearch = users.child("0001");
        Query querySearch = productDB.orderByChild("type").equalTo("Pratos_Quentes");

        //cria um ouvinte
        querySearch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Product product = objSnapshot.getValue(Product.class);

                    productsList.add(product);
                }

                if (productsList.size() > 0) {
                    ProductListAdapter plsadp = new ProductListAdapter(
                            getApplicationContext(), productsList);

                    lstPlaHot.setAdapter(plsadp);
                    plsadp.notifyDataSetInvalidated();
                }else{
                    productsList = new ArrayList<>();
                    msgShort("Não há produtos para listar!");

                }
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
