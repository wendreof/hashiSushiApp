package com.example.hashisushi.views;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hashisushi.R;
import com.example.hashisushi.adapter.AdapterProduct;
import com.example.hashisushi.dao.UserFirebase;
import com.example.hashisushi.listener.RecyclerItemClickListener;
import com.example.hashisushi.model.OrderItens;
import com.example.hashisushi.model.Orders;
import com.example.hashisushi.model.Product;
import com.example.hashisushi.model.User;
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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import dmax.dialog.SpotsDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActPromotion extends AppCompatActivity implements View.OnClickListener
{
    private TextView txtQuantItens;
    private TextView  txtTotalOrder;
    private TextView txtTitle;
    private TextView txtStatus;
    private FloatingActionButton flotBntSalesCardap;
    private FloatingActionButton flotBntFinishProm;
    private FloatingActionButton flotBntExitP;
    private  FloatingActionButton flotBntEditPersonP;
    private System status;

    private DatabaseReference reference ;
    private List<Product> productsList = new ArrayList<Product>();
    private List<OrderItens> itensCars = new ArrayList<>();
    private RecyclerView list_produsts;
    private AdapterProduct adapterProduct;
    private AlertDialog dialog;
    private int metodoPagamento;
    private String retornIdUser;
    private User user;

    private Orders ordersRecovery;
    private int qtdItensCar ;
    private Double totalCar ;
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
        retornIdUser = UserFirebase.getIdUser();
        getStatus();
//        fontLogo();

        flotBntExitP.setOnClickListener(this);
        flotBntFinishProm.setOnClickListener(this);
        flotBntSalesCardap.setOnClickListener(this);
        flotBntEditPersonP.setOnClickListener(this);

        recyclerViewConfig();
        recycleOnclick();

        initSearch();
       recoveryDataUser();
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
                                confirmItem(position);
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
        txtQuantItens = findViewById( R.id.txtQuantItens);
        txtTotalOrder = findViewById( R.id.txtTotalOrder);

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
            confirmarPedido();
           // Intent it = new Intent( this, ActOrder.class );
            //startActivity( it );

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

//comfirmar item com dialog
    private void confirmItem(final int position){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Quantidade");
        alert.setMessage("Digite a quantidade");

        final EditText edtQuant = new EditText(this);
        edtQuant.setText("1");

        alert.setView(edtQuant);
        alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String quantity = edtQuant.getText().toString();

                Product productSelectd = productsList.get(position);
                OrderItens itemOrder = new OrderItens();

                itemOrder.setIdProduct( productSelectd.getIdProd() );
                itemOrder.setNameProduct(productSelectd.getName() );
                itemOrder.setItenSalePrice( productSelectd.getSalePrice());
                itemOrder.setQuantity( Integer.parseInt(quantity) );

                itensCars.add( itemOrder );

               // msgShort(itensCars.toString());

                if( ordersRecovery == null ){
                    ordersRecovery = new Orders(retornIdUser);
                }
                ordersRecovery.setName( user.getName() );
                ordersRecovery.setAddress( user.getAddress() );
                ordersRecovery.setNeigthborhood(user.getNeigthborhood());
                ordersRecovery.setNumberHome(user.getNumberHome());
                ordersRecovery.setCellphone(user.getPhone());
                ordersRecovery.setOrderItens( itensCars );
                ordersRecovery.salvar();


            }
        });

        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();

    }
    //recupera dados do usuario esta com
    // proplema para recuperar user
    private void recoveryDataUser() {

        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Carregando dados....")
                .setCancelable( false )
                .build();
        dialog.show();

        DatabaseReference usuariosDB = reference.child("users").child(retornIdUser);

        usuariosDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if( dataSnapshot.getValue() != null ){

                    user = dataSnapshot.getValue(User.class);
                }
                recoveryOrder();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

//confimar pedido  --  Este metodo provavel  mente saira
    private void confirmarPedido() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecione um método de pagamento");

        CharSequence[] itens = new CharSequence[]{
                "Dinheiro", "Máquina cartão"
        };
        builder.setSingleChoiceItems(itens, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                metodoPagamento = which;
            }
        });

        final EditText editObservacao = new EditText(this);
        editObservacao.setHint("Digite uma observação");
        builder.setView( editObservacao );

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String observacao = editObservacao.getText().toString();

                SimpleDateFormat dateFormat_data = new SimpleDateFormat("ddMMyyyy");
                SimpleDateFormat horaFormat_hora = new SimpleDateFormat("HHmm");
                Calendar cal = Calendar.getInstance();

                Date data_atual = cal.getTime();

                String hora = horaFormat_hora.format(data_atual);
                String dataAtual = dateFormat_data.format(data_atual);

                ordersRecovery.setDateOrder(Integer.parseInt(dataAtual));
                ordersRecovery.setHour(Integer.parseInt(hora));
                ordersRecovery.setObservation(observacao);
                ordersRecovery.setQuantProd(qtdItensCar);
                String total = String.valueOf(totalCar);
                ordersRecovery.setTotalPrince(total);
                ordersRecovery.setStatus("confirmado");
                ordersRecovery.confimar();
                ordersRecovery.remover();
                ordersRecovery = null;

                msgShort("Pedido Confirmado !");
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                msgShort("Pedido não confirmado");

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
//recupera pedido
    private void recoveryOrder(){

        DatabaseReference pedidoRef = reference
                .child("orders_user")
                .child( retornIdUser );

        pedidoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                qtdItensCar = 0;
                totalCar = 0.0;
                itensCars = new ArrayList<>();

                if(dataSnapshot.getValue() != null){

                    ordersRecovery = dataSnapshot.getValue(Orders.class);
                    itensCars = ordersRecovery.getOrderItens();


                    for(OrderItens orderItens: itensCars){

                        int qtde = orderItens.getQuantity();

                        String strPreco = orderItens.getItenSalePrice();
                        double preco = Double.parseDouble( strPreco );
                        System.out.println(preco);

                        totalCar += (qtde * preco);
                        qtdItensCar += qtde;

                    }

                }

                DecimalFormat df = new DecimalFormat("0.00");

                txtQuantItens.setText( String.valueOf(qtdItensCar) );
                txtTotalOrder.setText(df.format( totalCar ) );

                dialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
