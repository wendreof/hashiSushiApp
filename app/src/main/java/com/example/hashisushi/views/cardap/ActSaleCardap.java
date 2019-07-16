package com.example.hashisushi.views.cardap;


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
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import com.example.hashisushi.views.ActOrder;
import com.example.hashisushi.views.ActPoints;
import com.example.hashisushi.views.ActSignup;
import com.example.hashisushi.views.ActWait;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActSaleCardap extends AppCompatActivity implements View.OnClickListener
{
    Orders orders;
    private TextView txtQuantItensE;
    private TextView  txtTotalOrderE;
    private TextView txtCardapScardp;

    private TextView txtSalesCardap;
    private FloatingActionButton flotBntVoltarPromoE;
    private FloatingActionButton flotBntFinishE;
    private FloatingActionButton flotBtnPlatHotE;

    private DatabaseReference reference ;
	private List<Product> productsList = new ArrayList<>();
    private RecyclerView lstEntrada;
    private AdapterProduct adapterProduct;

    private List<OrderItens> itensCars = new ArrayList<>();

    private AlertDialog dialog;
    private String retornIdUser;
    private User user;

    private Orders ordersRecovery;
    private int qtdItensCar ;
    private Double totalCar ;
    
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState);
        setContentView( R.layout.act_sale_cardap );

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        bar.setTitle("");

        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        initComponent();
        initDB();
        initSearch();
        fontLogo();
        recyclerViewConfig();
        retornIdUser = UserFirebase.getIdUser();

        flotBntVoltarPromoE.setOnClickListener(this);
        flotBntFinishE.setOnClickListener(this);
        flotBtnPlatHotE.setOnClickListener(this);

        recoveryDataUser();
        recycleOnclick();

    }

    private void recycleOnclick(){
        //Adiciona evento de clique no recyclerview
        lstEntrada.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        lstEntrada,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Product produtoSelecionado = productsList.get(position);
                                confirmItem(position,produtoSelecionado);
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

    }//recycle

    private void recyclerViewConfig(){

        //Configura recyclerview
        lstEntrada.setLayoutManager(new LinearLayoutManager(this));
        lstEntrada.setHasFixedSize(true);
        adapterProduct = new AdapterProduct(productsList, this);
        lstEntrada.setAdapter( adapterProduct );

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {

        if ( v.getId() == R.id.flotBntVoltarPromoE) {

            startVibrate(90);
            finish();
        }
        if ( v.getId() == R.id.flotBntFinishE ) {

            startVibrate(90);
            Intent it = new Intent( this, ActOrder.class );
            startActivity( it );

        } if(v.getId() == R.id.flotBtnPlatHotE) {

            startVibrate(90);
            openPlatHot();

        }
    }

    //Altera fonte do txtLogo
    private void fontLogo(){

        Typeface font = Typeface.createFromAsset(getAssets(), "RagingRedLotusBB.ttf");
        txtCardapScardp.setTypeface(font);
        txtSalesCardap.setTypeface(font);
    }


    //Metudo que ativa vibração
    public void startVibrate(long time) {
        // cria um obj atvib que recebe seu valor de context
        Vibrator atvib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        atvib.vibrate(time);
    }

    private void msgShort(String msg) {

        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

    private void openPlatHot(){

        Intent intent = new Intent(ActSaleCardap.this,ActPlatHot.class);
        //Passa efeitos de transzição
        ActivityOptionsCompat actcompat = ActivityOptionsCompat
                .makeCustomAnimation(getApplicationContext(),
                R.anim.fade_in,R.anim.mover_direita);
        ActivityCompat.startActivity(ActSaleCardap.this,intent,actcompat.toBundle());
    }

    //oa clicar em voltar chama efeito de transição
    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.mover_esquerda,R.anim.fade_out);
    }

    public void initDB()
    {
        FirebaseApp.initializeApp(ActSaleCardap.this);
        this.reference = FirebaseDatabase.getInstance().getReference();
    }

    private void initComponent()
    {
        txtQuantItensE = findViewById( R.id.txtQuantItensE);
        txtTotalOrderE = findViewById( R.id.txtTotalOrderE);
        txtCardapScardp = findViewById(R.id.txtCardapScardp);
        txtSalesCardap = findViewById(R.id.txtSalesCardap);

        flotBntVoltarPromoE = findViewById(R.id.flotBntVoltarPromoE);
        flotBntFinishE = findViewById(R.id.flotBntFinishE);
        flotBtnPlatHotE = findViewById(R.id.flotBtnPlatHotE);

        lstEntrada = findViewById(R.id.lstEntrada);

    }

    public void initSearch(){

        //retorna usuarios
       final DatabaseReference productDB = reference.child("product");

        Query querySearch = productDB.orderByChild("type").equalTo("Entrada");

            //cria um ouvinte
        querySearch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 productsList.clear();

                for (DataSnapshot ds:dataSnapshot.getChildren()) {

                    productsList.add(ds.getValue(Product.class));
                }

                adapterProduct.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                msgShort("Houve algum erro :" + databaseError);
            }
        });

    }

    //comfirmar item com dialog
    private void confirmItem(final int position, Product produtoSelecionado)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(produtoSelecionado.getName());
        alert.setMessage("\nInforme a quantiade desejada: ");

        final EditText edtQuant = new EditText(this);
        edtQuant.setText("1");

        alert.setView(edtQuant);
        alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String quantity = edtQuant.getText().toString();

                //trata erro de qt vazia
                if (quantity.equals("")){
                    quantity = "1";
                    msgShort("Você não definiu Quantidade !");
                    msgShort("um item foi adicionado automaticamente.");
                }

                if (validaQuantidade(quantity) == 0) {

                    Product productSelectd = productsList.get(position);
                    OrderItens itemOrder = new OrderItens();

                    itemOrder.setIdProduct(productSelectd.getIdProd());
                    itemOrder.setNameProduct(productSelectd.getName());
                    itemOrder.setItenSalePrice(productSelectd.getSalePrice());
                    itemOrder.setQuantity(Integer.parseInt(quantity));

                    itensCars.add(itemOrder);
                    msgShort("Produto adicionado ao seu carrinho!");

                    // msgShort(itensCars.toString());

                    if (ordersRecovery == null)
                    {
                        ordersRecovery = new Orders(retornIdUser);
                    }
                    ordersRecovery.setName(user.getName());
                    ordersRecovery.setAddress(user.getAddress());
                    ordersRecovery.setNeigthborhood(user.getNeigthborhood());
                    ordersRecovery.setNumberHome(user.getNumberHome());
                    ordersRecovery.setCellphone(user.getPhone());

                    ordersRecovery.setOrderItens(itensCars);

                    ordersRecovery.salvar();

                }
                else
                {
                    edtQuant.setText("1");
                }
            }
        });

        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    private int validaQuantidade(String valor) //valida se o valor digitado é numérico
    {
        String regexStr = "^[0-9]*$";
        if (!valor.trim().matches(regexStr))
        {
            msgShort("Por favor, informe um valor numérico!");
            return 1;
        }
        else return 0;
    }

    //recupera dados do usuario esta com
    // proplema para recuperar user
    private void recoveryDataUser() {

      dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Carregando dados aguarde....")
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


                if (dataSnapshot.getValue() != null)
                {
                    ordersRecovery = dataSnapshot.getValue(Orders.class);

                    //trata null pointer apos
                    // remover untimo iten carrinho
                    if (ordersRecovery != null)
                    {

                        itensCars = ordersRecovery.getOrderItens();

                    }else {
                        Orders orders = new Orders();
                        orders.removerOrderItens(retornIdUser);
                    }
                    //trata NullPointer
                    if (itensCars != null ) {

                        for (OrderItens orderItens : itensCars) {
                            int qtde = orderItens.getQuantity();

                            double preco = Double.parseDouble(orderItens.getItenSalePrice());

                            totalCar += (qtde * preco);
                            qtdItensCar += qtde;
                        }
                    }else{

                        Orders orders = new Orders();
                        orders.removerOrderItens(retornIdUser);
                    }
                }


                DecimalFormat df = new DecimalFormat("0.00");

                txtQuantItensE.setText( String.valueOf(qtdItensCar) );
                txtTotalOrderE.setText(df.format( totalCar ) );

                dialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //==> MENUS
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_promotion, menu);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.menu_enter)
        {
            Intent it = new Intent(this, ActSaleCardap.class);
            startActivity(it);
            finish();
            return true;
        }

        if (id == R.id.menu_plat_hot)
        {
            Intent it = new Intent(this, ActPlatHot.class);
            startActivity(it);
            finish();
            return true;
        }

        if (id == R.id.menu_plat_ace)
        {
            Intent it = new Intent(this, ActPlatAce.class);
            startActivity(it);
            finish();
            return true;
        }

        if (id == R.id.menu_combo)
        {
            Intent it = new Intent(this, ActCombo.class);
            startActivity(it);
            finish();
            return true;
        }

        if (id == R.id.menu_drinks)
        {
            Intent it = new Intent(this, ActDrinks.class);
            startActivity(it);
            finish();
            return true;
        }
        if (id == R.id.menu_temakis)
        {
            Intent it = new Intent(this, ActTemakis.class);
            startActivity(it);
            finish();
            return true;
        }
        if (id == R.id.menu_edit_cadastro)
        {
            Intent it = new Intent(this, ActSignup.class);
            startActivity(it);
            finish();
            return true;
        }
        if (id == R.id.menu_points)
        {
            Intent it = new Intent(this, ActPoints.class);
            startActivity(it);
            finish();
            return true;
        }
        if (id == R.id.menu_satus)
        {
            Intent it = new Intent(this, ActWait.class);
            startActivity(it);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}