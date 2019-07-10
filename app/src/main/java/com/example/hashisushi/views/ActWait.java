package com.example.hashisushi.views;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.hashisushi.R;
import com.example.hashisushi.adapter.AdapterStatusOrders;
import com.example.hashisushi.dao.FirebaseConfig;
import com.example.hashisushi.dao.UserFirebase;
import com.example.hashisushi.model.Orders;
import com.example.hashisushi.model.Product;
import com.example.hashisushi.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActWait extends AppCompatActivity implements View.OnClickListener {
	
	DatabaseReference reference;
	private TextView txtTitle;
	private TextView dataPedido;
	private TextView  previsaoEntrega;

	private String retornIdUser;
	private Orders orders;
	private TextView numberToCall;

	private AdapterStatusOrders adapterStatusOrders;

	private RecyclerView list_statusOrders;

	private List<Orders> ordersList = new ArrayList<>();

	private User user;
	private TextView txtPedido;
	
	@Override
	protected void onCreate ( Bundle savedInstanceState ) {
		super.onCreate ( savedInstanceState );
		setContentView ( R.layout.act_wait);
		getSupportActionBar ( ).hide ( );
		
		findViewByIds ( );
		fontLogo ( );

		retornIdUser = UserFirebase.getIdUser ( );
		reference = FirebaseConfig.getFirebase ( );
		
		recoveryDataUser ( ); //recupera os dados do user
		//listesnerEventPedidos(retornIdUser);
		getOrder(retornIdUser);
		recyclerViewConfig();


	}

	//Configura recyclerview
	private void recyclerViewConfig()
	{

		list_statusOrders.setLayoutManager(new LinearLayoutManager(this));
		list_statusOrders.setHasFixedSize(true);
		adapterStatusOrders = new AdapterStatusOrders(ordersList, this);
		list_statusOrders.setAdapter(adapterStatusOrders);
	}
	
	@Override
	protected void attachBaseContext ( Context newBase ) {
		super.attachBaseContext ( CalligraphyContextWrapper.wrap ( newBase ) );
	}
	
	private void recoveryDataUser ( ) {

		
		
		DatabaseReference usuariosDB = reference.child ( "users" ).child ( retornIdUser );
		
		usuariosDB.addListenerForSingleValueEvent ( new ValueEventListener ( ) {
			@Override
			public void onDataChange ( DataSnapshot dataSnapshot ) {
				if ( dataSnapshot.getValue ( ) != null ) {
					user = dataSnapshot.getValue ( User.class );
				}
			}
			
			@Override
			public void onCancelled ( DatabaseError databaseError ) {
			
			}
		} );
	}

	
	//Altera fonte do txtLogo
	private void fontLogo ( ) {
		Typeface font = Typeface.createFromAsset ( getAssets ( ), "RagingRedLotusBB.ttf" );
		txtTitle.setTypeface ( font );
		txtPedido.setTypeface ( font );
	}
	
	private void findViewByIds ( ) {

		txtTitle = findViewById ( R.id.txtTitleReg );
		txtPedido = findViewById ( R.id.txtPedido );
		numberToCall = findViewById ( R.id.numberToCall );
		previsaoEntrega = findViewById ( R.id.previsaoEntrega );

		//RecyclerView---
		list_statusOrders = findViewById(R.id.list_statusOrders);

		//listeners
		numberToCall.setOnClickListener ( this );


	}
	
	@Override
	public void onClick ( View v ) {
		if(v.getId ( ) == R.id.numberToCall){
			Uri uri = Uri.parse("tel:"+numberToCall.getText ().toString ());
			Intent intent = new Intent(Intent.ACTION_DIAL,uri);
			startActivity(intent);
		}
	}


	public void listesnerEventPedidos(String idUser) {

		//retorna pedido
		DatabaseReference pedidosDB = reference.child("orders");

		final Query querySearch = pedidosDB.orderByChild("idUser").equalTo(idUser);


		querySearch.addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
				/*
				Orders orders = dataSnapshot.getValue(Orders.class);

				ordersList.add(orders);

				adapterStatusOrders.notifyDataSetChanged();
				*/
			}

			@Override
			public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

				Orders orders = dataSnapshot.getValue(Orders.class);
				System.out.println("PEDIDO MODOU Status-------  "+orders.getStatus());

				if (orders.getStatus().equals("entregue")){

				}

					notificacao(orders);

			}

			@Override
			public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
				Orders orders = dataSnapshot.getValue(Orders.class);
				System.out.println("PEDIDO REMOVIDO-------  "+orders.getStatus());

			}

			@Override
			public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {

			}
		});

	}

	public void getOrder(final String idUser)
	{
		//retorna pedido
		DatabaseReference pedidosDB = reference.child("orders");

		final Query querySearch = pedidosDB.orderByChild("idUser").equalTo(idUser);

		//cria um ouvinte
		querySearch.addValueEventListener(new ValueEventListener()
		{
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot)
			{

				for (DataSnapshot objSnapshot : dataSnapshot.getChildren())
				{
					Orders orders = objSnapshot.getValue(Orders.class);
					ordersList.add(orders);
				}

				adapterStatusOrders.notifyDataSetChanged();

				listesnerEventPedidos(idUser);
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) { }
		});
	}


	private void notificacao(Orders orders ){


		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		//PendingIntent p = PendingIntent.getActivity(this,0, new Intent(),0 );
		PendingIntent p = PendingIntent.getActivity(this,0, new Intent(this,ActWait.class),0 );

		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setTicker("Status de Pedido");
		builder.setContentTitle("Status atual :"+orders.getStatus());

		builder.setSmallIcon(R.mipmap.ic_launcher);
		builder.setLargeIcon(BitmapFactory.decodeResource(getResources() ,R.mipmap.ic_launcher));
		builder.setContentIntent(p);

		NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
		String[] descs = new String[]{"O estatus de seu pedido modou :"};
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


	private void tempEntrega()
	{
		SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HHmm");

		Calendar cal = Calendar.getInstance();
		Date data_atual = cal.getTime();

		String hora_atual = dateFormat_hora.format(data_atual);
		Integer intHora = Integer.parseInt(hora_atual);


		String numero = "sd657dsf765sdaf756";
		numero = numero.replaceAll("[^0-9]*", "");

	}
}
