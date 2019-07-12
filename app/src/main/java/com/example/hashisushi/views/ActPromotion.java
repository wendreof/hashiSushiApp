package com.example.hashisushi.views;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.NotificationCompat;
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
import com.example.hashisushi.views.cardap.ActCombo;
import com.example.hashisushi.views.cardap.ActDrinks;
import com.example.hashisushi.views.cardap.ActPlatAce;
import com.example.hashisushi.views.cardap.ActPlatHot;
import com.example.hashisushi.views.cardap.ActPortions;
import com.example.hashisushi.views.cardap.ActSaleCardap;
import com.example.hashisushi.views.cardap.ActTemakis;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
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
    public static String STATUS = null;
    private TextView txtQuantItens;
    private TextView txtTotalOrder;
    private TextView txtPromution;
    private TextView txtStatus;
    private FloatingActionButton flotBntSalesCardap;
    private FloatingActionButton flotBntFinishProm;
    private FloatingActionButton flotBntExitP;
    private FloatingActionButton flotBntEditPersonP;

    private DatabaseReference reference;
    private List<Product> productsList = new ArrayList<Product>();
    private List<OrderItens> itensCars = new ArrayList<>();
    private RecyclerView list_produsts;
    private AdapterProduct adapterProduct;
    private AlertDialog dialog;
    private String retornIdUser;
    private User user;
    private FirebaseAuth auth;
    private Orders ordersRecovery;

    private int qtdItensCar ;
    private Double totalCar ;
    private int pontos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_promotion);

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        bar.setTitle("");

        //Travæ rotaçãø da tela
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getDate();
        startComponet();
        initDB();
        retornIdUser = UserFirebase.getIdUser();
        getStatus();
        fontLogo();

        flotBntExitP.setOnClickListener(this);
        flotBntFinishProm.setOnClickListener(this);
        flotBntSalesCardap.setOnClickListener(this);
        flotBntEditPersonP.setOnClickListener(this);

        recyclerViewConfig();
        recycleOnclick();

        initSearch();
        recoveryDataUser();
        this.auth = FirebaseAuth.getInstance();

    }//end oncreat

    //Altera fonte do txtLogo
    private void fontLogo(){

        Typeface font = Typeface.createFromAsset(getAssets(), "RagingRedLotusBB.ttf");
        txtPromution.setTypeface(font);
        txtStatus.setTypeface(font);
    }
    private void recycleOnclick()
    {
        //Adiciona evento de clique no recyclerview
        list_produsts.addOnItemTouchListener(

                new RecyclerItemClickListener(
                        this,
                        list_produsts,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Product produtoSelecionado = productsList.get(position);
                                confirmItem(position,produtoSelecionado);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                //Product produtoSelecionado = productsList.get(position);
                               // msgShort("Produto :"+produtoSelecionado);
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }

                    }
                )
        );
    }

    //Configura recyclerview
    private void recyclerViewConfig()
    {

        list_produsts.setLayoutManager(new LinearLayoutManager(this));
        list_produsts.setHasFixedSize(true);
        adapterProduct = new AdapterProduct(productsList, this);
        list_produsts.setAdapter(adapterProduct);
    }

    private void startComponet()
    {
        txtStatus = findViewById(R.id.txtEstatus);
        txtPromution = findViewById(R.id.txtPromution);
        txtQuantItens = findViewById(R.id.txtQuantItens);
        txtTotalOrder = findViewById(R.id.txtTotalOrder);

        flotBntExitP = findViewById(R.id.flotBntExitP);
        flotBntFinishProm = findViewById(R.id.flotBntFinishProm);
        flotBntSalesCardap = findViewById(R.id.flotBntSalesCardap);
        flotBntEditPersonP = findViewById(R.id.flotBntEditPersonP);
        //RecyclerView---
        list_produsts = findViewById(R.id.list_produsts);
    }

    private void getStatus()
    {

        if (STATUS.equals(getString(R.string.we_are_open_now)))
        {
        txtStatus.setTextColor(Color.GREEN);
        }
        else
        {
            txtStatus.setTextColor(Color.RED);
        }
        txtStatus.setText(STATUS);
    }

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.flotBntSalesCardap)
        {
            startVibrate(90);
            openSaleCardap();
        }
        if (v.getId() == R.id.flotBntFinishProm)
        {
            startVibrate(90);
             Intent it = new Intent( this, ActOrder.class );
             startActivity( it );
        }
        if (v.getId() == R.id.flotBntEditPersonP)
        {
            startVibrate(90);
            Intent it = new Intent(this, ActSignup.class);
            startActivity(it);
        }
        else if (v.getId() == R.id.flotBntExitP)
        {
            startVibrate(90);
            //finaliza a activity atual e todas a baixo
            auth.signOut();
            this.finishAffinity();

        }
    }

    //Metudo que ativa vibração
    public void startVibrate(long time)
    {
        // cria um obj atvib que recebe seu valor de context
        Vibrator atvib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        atvib.vibrate(time);
    }

    private void openSaleCardap()
    {
        Intent intent = new Intent(ActPromotion.this, ActSaleCardap.class);
        //Passa efeitos de transzição
        ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                R.anim.fade_in, R.anim.mover_direita);
        ActivityCompat.startActivity(ActPromotion.this, intent, actcompat.toBundle());
        //startActivity(intent);
    }

    public void initDB()
    {
        FirebaseApp.initializeApp(ActPromotion.this);
        this.reference = FirebaseDatabase.getInstance().getReference();
    }

    public void initSearch()
    {
        //retorna produto
        DatabaseReference productDB = reference.child("product");
        //retorna tipo setado
        Query querySearch = productDB.orderByChild("promotion").equalTo(true);

        //cria um ouvinte
        querySearch.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren())
                {
                    Product p = objSnapshot.getValue(Product.class);
                    productsList.add(p);
                }
                adapterProduct.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                msgShort("Houve algum erro:" + databaseError);
            }
        });
    }

    private void msgShort(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    //comfirmar item com dialog
    private void confirmItem(final int position, Product produtoSelecionado )
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
            public void onClick(DialogInterface dialog, int which) {

                recoveryOrder();

                String quantity = edtQuant.getText().toString();
                if (validaQuantidade(quantity) == 0) {

                    Product productSelectd = productsList.get(position);
                    OrderItens itemOrder = new OrderItens();


                    itemOrder.setIdProduct(productSelectd.getIdProd());
                    itemOrder.setNameProduct(productSelectd.getName());
                    itemOrder.setItenSalePrice(productSelectd.getSalePrice());
                    itemOrder.setQuantity(Integer.parseInt(quantity));

                    itensCars.add(itemOrder);

                    if (ordersRecovery == null) {
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
                else{
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

    private int validaQuantidade(String valor) {//valida se o valor digitado é numérico
        String regexStr = "^[0-9]*$";
        if (!valor.trim().matches(regexStr))
        {
            msgShort("Por favor, informe um valor numérico!");
            return 1;
        }
        else return 0;
    }

    //recupera dados do usuario esta com
    private void recoveryDataUser()
    {
        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Carregando dados aguarde....")
                .setCancelable( true )
                .build();
        dialog.show();

        DatabaseReference usuariosDB = reference.child("users").child(retornIdUser);

        usuariosDB.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.getValue() != null)
                {
                    user = dataSnapshot.getValue(User.class);
                }

                // recupera pontos
                pontos = user.getPonts();

                //se ponto 15 notifica
                if (pontos == 15){
                    notificacaoPonto(user);
                }

                recoveryOrder();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    //recupera pedido
    private void recoveryOrder()
    {
        DatabaseReference pedidoRef = reference
                .child("orders_user")
                .child(retornIdUser);

        pedidoRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
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

                txtQuantItens.setText(String.valueOf(qtdItensCar));
                txtTotalOrder.setText(df.format(totalCar));

                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

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
            return true;
        }

        if (id == R.id.menu_plat_hot)
        {
            Intent it = new Intent(this, ActPlatHot.class);
            startActivity(it);
            return true;
        }

        if (id == R.id.menu_plat_ace)
        {
            Intent it = new Intent(this, ActPlatAce.class);
            startActivity(it);
            return true;
        }

        if (id == R.id.menu_combo)
        {
            Intent it = new Intent(this, ActCombo.class);
            startActivity(it);
            return true;
        }

        if (id == R.id.menu_drinks)
        {
            Intent it = new Intent(this, ActDrinks.class);
            startActivity(it);
            return true;
        }
        if (id == R.id.menu_temakis)
        {
            Intent it = new Intent(this, ActTemakis.class);
            startActivity(it);
            return true;
        }
        if (id == R.id.menu_edit_cadastro)
        {
            Intent it = new Intent(this, ActSignup.class);
            startActivity(it);
            return true;
        }
        if (id == R.id.menu_portions )
        {
            Intent it = new Intent(this, ActPortions.class);
            startActivity(it);
            return true;
        }
        if (id == R.id.menu_points)
        {
            Intent it = new Intent(this, ActPoints.class);
            startActivity(it);
            return true;
        }
        if (id == R.id.menu_satus)
        {
            Intent it = new Intent(this, ActWait.class);
            startActivity(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getDate()
    {
        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HHmm");

        Calendar cal = Calendar.getInstance();
        Date data_atual = cal.getTime();

        String hora_atual = dateFormat_hora.format(data_atual);
        Integer intHora = Integer.parseInt(hora_atual);

        if (intHora > 900 && intHora < 2200)
        {
            STATUS = getString(R.string.we_are_open_now);
        }
        else
        {
            STATUS = getString(R.string.we_are_not_open);
        }
    }

    //==>FIM MENUS
    private void notificacaoPonto( User user){


        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //PendingIntent p = PendingIntent.getActivity(this,0, new Intent(),0 );
        PendingIntent p = PendingIntent.getActivity(this,0, new Intent(this,ActPoints.class),0 );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setTicker("Status de pontos");
        builder.setContentTitle("Parabens você atingiu :"+user.getPonts());

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources() ,R.mipmap.ic_launcher));
        builder.setContentIntent(p);

        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        String[] descs = new String[]{"Faça o regate na proxima compra"};
        for(int i = 0;i < descs.length; i++){
            style.addLine(descs[i]);
        }
        builder.setStyle(style);

        Notification no = builder.build();
        no.vibrate = new long[]{150,300,150};
        no.flags = Notification.FLAG_AUTO_CANCEL;
        nm.notify(R.mipmap.ic_launcher,no);

        try {
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(this,som);
            toque.play();
        }catch (Exception e){

            System.out.println("Erro ao gerar toque notificação : "+e);
        }
    }

}
