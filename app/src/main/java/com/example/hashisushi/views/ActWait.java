package com.example.hashisushi.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.hashisushi.R;
import com.example.hashisushi.dao.FirebaseConfig;
import com.example.hashisushi.dao.UserFirebase;
import com.example.hashisushi.model.OrderItens;
import com.example.hashisushi.model.Orders;
import com.example.hashisushi.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActWait extends AppCompatActivity implements View.OnClickListener {
	
	DatabaseReference reference;
	private TextView txtTitle;
	private TextView nameEmail;
	private TextView dataPedido;
	private TextView  nomeCliente;
	private TextView  codPedido;
	private TextView  valorTotal;
	private TextView  meioPagamento;
	private TextView  enderecoSimplificado;
	private TextView  previsaoEntrega;
	private CheckBox done;
	private CheckBox doing;
	private CheckBox received;
	private String retornIdUser;
	private String emailUser;
	private AlertDialog dialog;
	private int qtdItensCar;
	private Orders orders;
	private TextView numberToCall;
	private List< OrderItens > itensCars = new ArrayList<> ( );
	private Double totalCar;
	
	private Orders ordersRecovery;
	private User user;
	private TextView txtPedido;
	
	@Override
	protected void onCreate ( Bundle savedInstanceState ) {
		super.onCreate ( savedInstanceState );
		setContentView ( R.layout.activity_act_wait );
		getSupportActionBar ( ).hide ( );
		
		findViewByIds ( );
		fontLogo ( );
		
		emailUser = UserFirebase.getUserCorrent ( ).getEmail ( );
		retornIdUser = UserFirebase.getIdUser ( );
		reference = FirebaseConfig.getFirebase ( );
		
		recoveryDataUser ( ); //recupera os dados do user
		
		done.setClickable ( false );
		doing.setClickable ( false );
		received.setClickable ( false );
	}
	
	@Override
	protected void attachBaseContext ( Context newBase ) {
		super.attachBaseContext ( CalligraphyContextWrapper.wrap ( newBase ) );
	}
	
	private void recoveryDataUser ( ) {
		dialog = new SpotsDialog.Builder ( )
				.setContext ( this )
				.setMessage ( "Carregando dados aguarde, por favor aguarde..." )
				.setCancelable ( false )
				.build ( );
		dialog.show ( );
		
		
		DatabaseReference usuariosDB = reference.child ( "users" ).child ( retornIdUser );
		
		usuariosDB.addListenerForSingleValueEvent ( new ValueEventListener ( ) {
			@Override
			public void onDataChange ( DataSnapshot dataSnapshot ) {
				if ( dataSnapshot.getValue ( ) != null ) {
					user = dataSnapshot.getValue ( User.class );
				}
				recoveryOrder ( );
			}
			
			@Override
			public void onCancelled ( DatabaseError databaseError ) {
			
			}
		} );
	}
	
	private void recoveryOrder ( ) {
		
		DatabaseReference pedidoRef = reference
				.child ( "orders_user" )
				.child ( retornIdUser );
		
		pedidoRef.addValueEventListener ( new ValueEventListener ( ) {
			@Override
			public void onDataChange ( DataSnapshot dataSnapshot ) {
				qtdItensCar = 0;
				totalCar = 0.0;
				
				if ( dataSnapshot.getValue ( ) != null ) {
					
					ordersRecovery = dataSnapshot.getValue ( Orders.class );
					assert ordersRecovery != null;
					
					//trata null pointer apos
					// remover untimo iten carrinho
					if ( ordersRecovery != null ) {
						itensCars = ordersRecovery.getOrderItens ( );
					} else {
						Orders orders = new Orders ( );
						orders.removerOrderItens ( retornIdUser );
					}
					//trata NullPointer
					
				} else {
					Orders orders = new Orders ( );
					orders.removerOrderItens ( retornIdUser );
				}
				
				//DecimalFormat df = new DecimalFormat ( "0.00" );
				
				//txtTotal.setText ( String.format ( "%s", df.format ( totalCar ).replace ( ".", "," ) ) );
				
				//Trata Nullpointer
				//if ( itensCars != null ) {
					
					//	adapter = new ArrayAdapter<> ( getApplicationContext ( ), android.R.layout.simple_list_item_1, itensCars );
					
					//	lstorder.setAdapter ( adapter );
				//}
				dialog.dismiss ( );
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
		//ids
		txtTitle = findViewById ( R.id.txtTitleReg );
		txtPedido = findViewById ( R.id.txtPedido );
		nomeCliente = findViewById ( R.id.nomeCliente );
		numberToCall = findViewById ( R.id.numberToCall );
		codPedido = findViewById ( R.id.codPedido );
		valorTotal = findViewById ( R.id.valorTotal );
		meioPagamento = findViewById ( R.id.meioPagamento );
		enderecoSimplificado = findViewById ( R.id.enderecoSimplificado );
		previsaoEntrega = findViewById ( R.id.previsaoEntrega );
		dataPedido = findViewById ( R.id.dataPedido );
		done = findViewById ( R.id.done );
		doing = findViewById ( R.id.doing );
		received = findViewById ( R.id.received );
		
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
}
