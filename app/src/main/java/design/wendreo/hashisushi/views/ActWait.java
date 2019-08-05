package design.wendreo.hashisushi.views;

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
import android.widget.Toast;

import design.wendreo.hashisushi.R;
import design.wendreo.hashisushi.adapter.AdapterStatusOrders;
import design.wendreo.hashisushi.dao.FirebaseConfig;
import design.wendreo.hashisushi.dao.UserFirebase;
import design.wendreo.hashisushi.model.OrderItens;
import design.wendreo.hashisushi.model.Orders;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import design.wendreo.hashisushi.model.Product;
import design.wendreo.hashisushi.model.User;
import dmax.dialog.SpotsDialog;
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
			public void onChildAdded ( @NonNull DataSnapshot dataSnapshot, @Nullable String s ) {
				
				//pode ser usado para carregar lista
				Orders orders = dataSnapshot.getValue ( Orders.class );
				ordersList.add ( orders );
				
				adapterStatusOrders.notifyDataSetChanged ( );
			}
			
			@Override
			public void onChildChanged ( @NonNull DataSnapshot dataSnapshot, @Nullable String s ) {
				// qualquer mudança de status sera alertada
				Orders orders = dataSnapshot.getValue ( Orders.class );

				notificacao ( orders,"Status do Pedido","Status atual:" + orders.getStatus ( ),"O status de seu pedido mudou:" );

				String status =  orders.getStatus();

				if(status.equals("entregue")){

					calcPonts( orders );
				}


			}
			
			@Override
			public void onChildRemoved ( @NonNull DataSnapshot dataSnapshot ) {
			}
			
			@Override
			public void onChildMoved ( @NonNull DataSnapshot dataSnapshot, @Nullable String s ) {
			}
			
			@Override
			public void onCancelled ( @NonNull DatabaseError databaseError ) {
				msgShort ( "Status Erro " + databaseError );
			}
		} );
		
	}
	
	
	private void notificacao ( Orders orders,String ticker,String title,String msg) {

		if(ticker.equals("Pontuação")){
			PendingIntent p = PendingIntent.getActivity ( this, 0
					, new Intent ( this, ActPoints.class ), 0 );
			NotificationManager nm = ( NotificationManager ) getSystemService ( NOTIFICATION_SERVICE );


			NotificationCompat.Builder builder = new NotificationCompat.Builder ( this );
			builder.setTicker ( ticker );
			builder.setContentTitle ( title );

			builder.setSmallIcon ( R.mipmap.ic_launcher );
			builder.setLargeIcon ( BitmapFactory.decodeResource ( getResources ( ), R.mipmap.ic_launcher ) );
			builder.setContentIntent ( p );

			NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle ( );
			String[] descs = new String[] { msg };
			for ( String desc : descs ) {
				style.addLine ( desc );
			}
			builder.setStyle ( style );

			Notification no = builder.build ( );
			no.vibrate = new long[] { 150, 300, 150 };
			no.flags = Notification.FLAG_AUTO_CANCEL;
			nm.notify ( R.mipmap.ic_launcher, no );

		}else if(ticker.equals("Status do Pedido")){
			PendingIntent p = PendingIntent.getActivity ( this, 0
					, new Intent ( this, ActWait.class ), 0 );
			NotificationManager nm = ( NotificationManager ) getSystemService ( NOTIFICATION_SERVICE );


			NotificationCompat.Builder builder = new NotificationCompat.Builder ( this );
			builder.setTicker ( ticker );
			builder.setContentTitle ( title );

			builder.setSmallIcon ( R.mipmap.ic_launcher );
			builder.setLargeIcon ( BitmapFactory.decodeResource ( getResources ( ), R.mipmap.ic_launcher ) );
			builder.setContentIntent ( p );

			NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle ( );
			String[] descs = new String[] { msg };
			for ( String desc : descs ) {
				style.addLine ( desc );
			}
			builder.setStyle ( style );

			Notification no = builder.build ( );
			no.vibrate = new long[] { 150, 300, 150 };
			no.flags = Notification.FLAG_AUTO_CANCEL;
			nm.notify ( R.mipmap.ic_launcher, no );
		}
		

		
		try {
			Uri som = RingtoneManager.getDefaultUri ( RingtoneManager.TYPE_NOTIFICATION );
			Ringtone toque = RingtoneManager.getRingtone ( this, som );
			toque.play ( );
		} catch ( Exception e ) {
			
			System.out.println ( "Erro ao gerar toque notificação: " + e );
		}
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
			public void onCancelled ( DatabaseError databaseError ) {

			}
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
			notificacao(orders,"Pontuação","Você ganhou um ponto.", "Faça o resgate quando atingir 15 pontos" );
		}
	}

}
