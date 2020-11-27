package design.wendreo.hashisushi.views;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Icon;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import design.wendreo.hashisushi.R;
import design.wendreo.hashisushi.adapter.AdapterStatusOrders;
import design.wendreo.hashisushi.dao.FirebaseConfig;
import design.wendreo.hashisushi.dao.UserFirebase;
import design.wendreo.hashisushi.model.Orders;
import design.wendreo.hashisushi.model.User;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActWait extends AppCompatActivity implements View.OnClickListener {

	private DatabaseReference reference;
	private TextView txtTitle;
	private TextView dataPedido;
	private TextView previsaoEntrega;
	private String retornIdUser;
	private TextView numberToCall;
	
	private AdapterStatusOrders adapterStatusOrders;
	private RecyclerView list_statusOrders;
	private List< Orders > ordersList = new ArrayList<> ( );
	private TextView txtPedido;
	private User user;


	@Override
	protected void onCreate ( Bundle savedInstanceState ) {
		super.onCreate ( savedInstanceState );
		setContentView ( R.layout.act_wait );
		getSupportActionBar ( ).hide ( );
		
		findViewByIds ( );
		fontLogo ( );
		
		retornIdUser = UserFirebase.getIdUser ( );
		reference = FirebaseConfig.getFirebase ( );
		
		listesnerEventPedidos ( retornIdUser );
		recyclerViewConfig ( );
		recoveryDataUser ( );
	}
	
	@Override
	public void onBackPressed ( ) {
		finish ( );
	}
	
	//Configura recyclerview
	private void recyclerViewConfig ( ) {
		
		list_statusOrders.setLayoutManager ( new LinearLayoutManager ( this ) );
		list_statusOrders.setHasFixedSize ( true );
		adapterStatusOrders = new AdapterStatusOrders ( ordersList, this );
		list_statusOrders.setAdapter ( adapterStatusOrders );
	}
	
	@Override
	protected void attachBaseContext ( Context newBase ) {
		super.attachBaseContext ( CalligraphyContextWrapper.wrap ( newBase ) );
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
		list_statusOrders = findViewById ( R.id.list_statusOrders );
		
		//listeners
		numberToCall.setOnClickListener ( this );

	}
	
	@Override
	public void onClick ( View v ) {
		if ( v.getId ( ) == R.id.numberToCall ) {
			Uri uri = Uri.parse ( "tel:" + numberToCall.getText ( ).toString ( ) );
			Intent intent = new Intent ( Intent.ACTION_DIAL, uri );
			startActivity ( intent );
		}
	}
	
	
	public void listesnerEventPedidos ( String idUser ) {
		
		//retorna pedido
		DatabaseReference pedidosDB = reference.child ( "orders" );
		//recupara pedidos do user limitando  por id
		Query querySearch = pedidosDB.orderByChild ( "idUser" ).equalTo ( idUser );
		
		
		querySearch.addChildEventListener ( new ChildEventListener ( ) {
			@Override
			public void onChildAdded (  DataSnapshot dataSnapshot,  String s ) {
				
				//pode ser usado para carregar lista
				Orders orders = dataSnapshot.getValue ( Orders.class );
				ordersList.add ( orders );
				
				adapterStatusOrders.notifyDataSetChanged ( );
			}
			
			@Override
			public void onChildChanged ( DataSnapshot dataSnapshot,  String s ) {
				// qualquer mudança de status sera alertada
				Orders orders = dataSnapshot.getValue ( Orders.class );
				ordersList.add ( orders );
				adapterStatusOrders.notifyDataSetChanged ();
				//notification( orders,"Status do Pedido","Status do Pedido" + orders.getStatus ( ),"O status de seu pedido mudou:" );
				sendNotification("Status do Pedido","Status atual " + orders.getStatus ( ),"O status de seu pedido mudou:");
				//createNotification("Status Pedido " ,st);
				String status =  orders.getStatus();
				if(status.equals("entregue")){
					calcPonts( orders );
				}
			}
			@Override
			public void onChildRemoved (  DataSnapshot dataSnapshot ) {}
			@Override
			public void onChildMoved (  DataSnapshot dataSnapshot,  String s ) {}
			@Override
			public void onCancelled ( DatabaseError databaseError ) {
				//msgShort ( "Status Erro " + databaseError );
				System.out.println("@@@@@@@@ onCancelled  ----- " + " Status Erro " + databaseError );
			}
		} );
		
	}
	

	private void msgShort ( String msg ) {
		Toast.makeText ( getApplicationContext ( ), msg, Toast.LENGTH_SHORT ).show ( );
	}


	//recupera dados do usuario
	private void recoveryDataUser ( ) {

		DatabaseReference usuariosDB = reference.child ( "users" ).child ( retornIdUser );

		usuariosDB.addListenerForSingleValueEvent ( new ValueEventListener( ) {
			@Override
			public void onDataChange ( DataSnapshot dataSnapshot ) {
				if ( dataSnapshot.getValue ( ) != null ) {
					user = dataSnapshot.getValue ( User.class );
				}
			}
			@Override
			public void onCancelled ( DatabaseError databaseError ) { }
		} );
	}

	//calcula valor de compra e gera ponto se
	// status igual a entregue
	private void calcPonts(Orders orders){
		int ponto = user.getPonts();
		//troca vigula por ponto
		String strTaxaEntrega =  orders.getDeliveryCost();
		double taxaEntrega = Double.parseDouble (
				strTaxaEntrega.replace ( ",", "." ) );
		double totalCompra = orders.getTotalPrince() ;
		double totalFinal = totalCompra - taxaEntrega;
		//gera ponto compra maior 30
		if ( totalFinal > 30.00 && ponto < 15 ) {
			ponto++;
			user.uploadPonts ( ponto );
			sendNotification("Pontuação","Você ganhou um ponto.", "Faça o resgate quando atingir 15 pontos" );
		}
	}

	private void sendNotification( String ticker, String title, String msg){
		int notification_id = (int) System.currentTimeMillis();
		NotificationManager notificationManager = null;
		NotificationCompat.Builder mBuilder;

		String body = ticker;
		String type = "Status";
		String CHANNEL_DESCRIPTION = msg;
		String CHANNEL_NAME = title;
		//Set pending intent to builder
		Intent intent = new Intent(getApplicationContext(), ActWait.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);

		//Notification builder
		if (notificationManager == null){
			notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		}


		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
			int importance = NotificationManager.IMPORTANCE_HIGH;
			NotificationChannel mChannel = notificationManager.getNotificationChannel(ticker);
			if (mChannel == null){
				mChannel = new NotificationChannel(ticker, CHANNEL_NAME, importance);
				mChannel.setDescription(CHANNEL_DESCRIPTION);
				mChannel.enableVibration(true);
				mChannel.setLightColor(Color.GREEN);
				mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
				notificationManager.createNotificationChannel(mChannel);
			}

			mBuilder = new NotificationCompat.Builder(this, ticker);
			mBuilder.setContentTitle(title)
					.setSmallIcon(R.drawable.iconstrave)
					.setContentText(body) //show icon on status bar
					.setContentIntent(pendingIntent)
					.setAutoCancel(true)
					.setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
					.setDefaults(Notification.DEFAULT_ALL);
		}else {
			mBuilder = new NotificationCompat.Builder(this);
			mBuilder.setContentTitle(title)
					.setSmallIcon(R.drawable.iconstrave)
					.setContentText(body)
					.setPriority(Notification.PRIORITY_HIGH)
					.setContentIntent(pendingIntent)
					.setAutoCancel(true)
					.setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
					.setDefaults(Notification.DEFAULT_VIBRATE);
		}

		notificationManager.notify(1002, mBuilder.build());
	}

}


