package com.example.hashisushi.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hashisushi.R;
import com.example.hashisushi.dao.FirebaseConfig;
import com.example.hashisushi.dao.UserFirebase;
import com.example.hashisushi.model.OrderItens;
import com.example.hashisushi.model.Orders;
import com.example.hashisushi.model.Product;
import com.example.hashisushi.model.User;
import com.example.hashisushi.utils.MockPaymentMethods;
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

import dmax.dialog.SpotsDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActOrder extends AppCompatActivity implements View.OnClickListener {
	
	public String EntregaRetira = "";
	DatabaseReference reference;
	Activity activity;
	ArrayAdapter< OrderItens > adapter;
	private TextView txtTitle;
	private TextView txtPedido;
	private TextView txtTotal;
	private Spinner spnFillPayment;
	private RadioButton chkBxRetirar;
	private RadioButton chkBxEntrega;
	private EditText editObservation;
	private EditText edtStreetDelivery;
	private EditText edtNumberDelivery;
	private EditText edtNeighborhoodDelivery;
	private Button btnFinishOrder;
	private String emailUser;
	private ListView lstorder;
	private Context context;
	private AlertDialog dialog;
	private String retornIdUser;
	private User user;
	private Orders ordersRecovery;
	private ScrollView ActOrder;
	private List< OrderItens > itensCars = new ArrayList<> ( );
	private List< Product > productsList = new ArrayList<> ( );
	private List<Orders>ordersList = new ArrayList<>();
	private int qtdItensCar;
	private Double totalCar;
	private Orders orders;
	private int metodoEntrega;
	
	@Override
	protected void onCreate ( Bundle savedInstanceState ) {
		super.onCreate ( savedInstanceState );
		setContentView ( R.layout.act_order );
		getSupportActionBar ( ).hide ( );
		
		findViewByIds ( );
		fontLogo ( );
		fillPayMent ( );
		orders = new Orders ( );
		btnFinishOrder.setOnClickListener ( this );
		//reference db and recover value
		emailUser = UserFirebase.getUserCorrent ( ).getEmail ( );
		retornIdUser = UserFirebase.getIdUser ( );
		reference = FirebaseConfig.getFirebase ( );
		
		recoveryDataUser ( ); //recupera os dados do user
		lstorderClick ( ); //listener do listview

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
	
	//Metudo que ativa vibração
	public void startVibrate ( long time ) {
		// cria um obj atvib que recebe seu valor de context
		Vibrator atvib = ( Vibrator ) getSystemService ( Context.VIBRATOR_SERVICE );
		atvib.vibrate ( time );
	}
	
	//carrega os métodos de pagamento
	private void fillPayMent ( ) {
		try {
			List< String > list = MockPaymentMethods.INSTANCE.getPaymentMethods ( );
			ArrayAdapter< String > adapter = new ArrayAdapter<> ( this,
					android.R.layout.simple_list_item_1, list );
			adapter.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
			spnFillPayment.setAdapter ( adapter );
		} catch ( Exception ex ) {
			msgShort ( "Erro:" + ex.getMessage ( ) );
		}
	}
	
	//exibe um ToastView
	private void msgShort ( String msg ) {
		Toast.makeText ( getApplicationContext ( ), msg, Toast.LENGTH_SHORT ).show ( );
	}
	
	//captura os clicks  -BNT-
	@Override
	public void onClick ( View v ) {
		if ( v.getId ( ) == R.id.btnFinishOrder ) {
			startVibrate ( 190 );
			valueTest ( );
		} else if ( v.getId ( ) == R.id.chkBxRetirar ) {
			msgShort ( "Retirar" );
			edtStreetDelivery.setText ( "Rua São Pedro" );
			edtNeighborhoodDelivery.setText ( "Centro" );
			edtNumberDelivery.setText ( "661" );
		} else if ( v.getId ( ) == R.id.chkBxEntrega ) {
			msgShort ( "Entregar" );
			
			//trata null pointer quaso orders_user
			// não exita ainda para o usuario


			if(ordersRecovery != null)
			{
				edtStreetDelivery.setText(ordersRecovery.getAddress());
				edtNeighborhoodDelivery.setText(ordersRecovery.getNeigthborhood());
				edtNumberDelivery.setText(ordersRecovery.getNumberHome());
			}else {
				msgShort("Voçê não tem itens no carrinho.");
				msgShort("Primeiro adicione itens ao carrinho.");
				msgShort("Só depois podera definir o endereço.");
			}
			
		}
	}
	
	//ao clicar em volta e chama efeito de transição
	@Override
	public void finish ( ) {
		super.finish ( );
		overridePendingTransition ( R.anim.mover_esquerda, R.anim.fade_out );
	}
	
	// Verifica se o valor total é 0
	private void valueTest ( ) {
		
		Double value = Double.parseDouble ( txtTotal.getText ( ).toString ( ).replace ( "R$ ", "" ).replace ( ",", "." ) );
		
		if ( value <= 0 ) {
			msgShort ( "Não há itens para finalizar o pedido! =x" );
		} else {
			msgShort ( "OK!!!!" + value + 	ordersRecovery.getAddress () );
			confirmOrder();
		}
	}
	
	//recupera dados do usuario
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

	//confimar pedido  --
	private void confirmOrder ( ) {
		if ( chkBxEntrega.isChecked ( ) ) {
			EntregaRetira = "A ser entregue em: ";
		} else
			EntregaRetira = "A ser retirado em: ";
		
		AlertDialog.Builder builder = new AlertDialog.Builder ( this );
		builder.setTitle ( "Finalizar Pedido" );
		
		builder.setMessage ( "\nDeseja confirmar o pedido de:\n" +
				"R$: " + txtTotal.getText ( ) + " - " + spnFillPayment.getSelectedItem ( ) +
				"\n\n" + EntregaRetira + "\n" +
				edtStreetDelivery.getText ( ) + ", nº " +
				edtNumberDelivery.getText ( ) + " - " +
				edtNeighborhoodDelivery.getText ( )
		);
		
		builder.setPositiveButton ( "Confirmar", new DialogInterface.OnClickListener ( ) {
			
			
			@Override
			public void onClick ( DialogInterface dialog, int which ) {
				
				SimpleDateFormat dateFormat_data = new SimpleDateFormat ( "ddMMyyyy" );
				SimpleDateFormat horaFormat_hora = new SimpleDateFormat ( "HHmm" );
				Calendar cal = Calendar.getInstance ( );
				
				Date data_atual = cal.getTime ( );
				
				String hora = horaFormat_hora.format ( data_atual );
				String dataAtual = dateFormat_data.format ( data_atual );
				
				ordersRecovery.setDateOrder ( Integer.parseInt ( dataAtual ) );
				ordersRecovery.setHour ( Integer.parseInt ( hora ) );
				String obs = editObservation.getText ( ).toString ( );
				ordersRecovery.setAddress ( edtStreetDelivery.getText ( ).toString ( ) );
				ordersRecovery.setNumberHome ( edtNumberDelivery.getText ( ).toString ( ) );
				ordersRecovery.setNeigthborhood ( edtNeighborhoodDelivery.getText ( ).toString ( ) );
				ordersRecovery.setObservation ( obs );
				ordersRecovery.setQuantProd ( qtdItensCar );
				ordersRecovery.setTotalPrince ( totalCar );
				//gera codigo caso compra maior 30
				if ( totalCar > 30.00 ) {
					ordersRecovery.setQrCode ( retornIdUser + totalCar );
				} else {
					ordersRecovery.setQrCode ( "" );
				}
				
				ordersRecovery.setStatus ( "confirmado" );
				ordersRecovery.confimar ( );
				ordersRecovery.remover ( );
				ordersRecovery = null;
				
				msgShort ( "Pedido Confirmado" );
				startActPromotion ( );
				finish ( );
				
				
			}
		} ).setNegativeButton ( "Cancelar", new DialogInterface.OnClickListener ( ) {
			@Override
			public void onClick ( DialogInterface dialog, int which ) {
				msgShort ( "Pedido não confirmado" );
			}
		} );
		builder.create ( );
		builder.show ( );
	}

	//recupera pedido
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
					if ( itensCars != null ) {
						
						for ( OrderItens orderItens : itensCars ) {
							int qtde = orderItens.getQuantity ( );
							
							String strPreco = orderItens.getItenSalePrice ( );
							double preco = Double.parseDouble ( strPreco );
							
							totalCar += ( qtde * preco );
							qtdItensCar += qtde;
						}
					} else {
						Orders orders = new Orders ( );
						orders.removerOrderItens ( retornIdUser );
					}
				} else {
					Orders orders = new Orders ( );
					orders.removerOrderItens ( retornIdUser );
				}
				
				DecimalFormat df = new DecimalFormat ( "0.00" );
				
				txtTotal.setText ( String.format ( "%s", df.format ( totalCar ).replace ( ".", "," ) ) );
				
				//Trata Nullpointer
				if ( itensCars != null ) {
					
					adapter = new ArrayAdapter<> ( getApplicationContext ( ), android.R.layout.simple_list_item_1, itensCars );
					
					lstorder.setAdapter ( adapter );
				}
				dialog.dismiss ( );
			}
			
			@Override
			public void onCancelled ( DatabaseError databaseError ) {
			
			}
		} );
	}
	
	//recupera todos os ids
	private void findViewByIds ( ) {
		spnFillPayment = findViewById ( R.id.spnfillPayMent );
		txtTitle = findViewById ( R.id.txtTitleReg );
		txtPedido = findViewById ( R.id.txtPedido );
		txtTotal = findViewById ( R.id.txtTotal );
		chkBxRetirar = findViewById ( R.id.chkBxRetirar );
		chkBxEntrega = findViewById ( R.id.chkBxEntrega );
		btnFinishOrder = findViewById ( R.id.btnFinishOrder );
		editObservation = findViewById ( R.id.editObservation );
		lstorder = findViewById ( R.id.lstOrder );
		edtStreetDelivery = findViewById ( R.id.edtStreetDelivery );
		edtNumberDelivery = findViewById ( R.id.edtNumberDelivery );
		edtNeighborhoodDelivery = findViewById ( R.id.edtNeighborhoodDelivery );
		// seta os listeners
		chkBxRetirar.setOnClickListener ( this );
		chkBxEntrega.setOnClickListener ( this );
	}
	
	//captura o click no listview
	private void lstorderClick ( ) {
		lstorder.setOnItemClickListener ( new AdapterView.OnItemClickListener ( ) {
			@Override
			public void onItemClick ( AdapterView< ? > parent, View view, final int position, long rowId ) {
				
				AlertDialog.Builder builder = new AlertDialog.Builder ( ActOrder.this );
				builder.setTitle ( "Remover item" );
				builder.setMessage ( "Você está removendo: \n" + parent.getItemAtPosition ( position ) );
				builder.setPositiveButton ( "Remover", new DialogInterface.OnClickListener ( ) {
					@Override
					public void onClick ( DialogInterface dialog, int which ) {
						//Product productSelectd = productsList.get ( position );
						OrderItens itemOrder = new OrderItens ( );
						
						//subtrai da quantidade total
						itemOrder.setQuantity ( itemOrder.getQuantity ( ) - 1 );
						
						itensCars.remove ( itemOrder );//remove o item do carrinho!
						adapter.remove ( adapter.getItem ( position ) ); //remove do listview
						adapter.notifyDataSetChanged ( ); //atualiza o listview
						
						msgShort ( "Item removido do seu carrinho! ;)" );
						
						ordersRecovery.setName ( user.getName ( ) );
						ordersRecovery.setAddress ( user.getAddress ( ) );
						ordersRecovery.setNeigthborhood ( user.getNeigthborhood ( ) );
						ordersRecovery.setNumberHome ( user.getNumberHome ( ) );
						ordersRecovery.setCellphone ( user.getPhone ( ) );
						ordersRecovery.setOrderItens ( itensCars );
						ordersRecovery.salvar ( );
					}
				} );
				builder.setNegativeButton ( "Cancelar", null );
				builder.create ( );
				builder.show ( );
				
			}
			
		} );
	}
	
	private void ShowMSG ( String msg ) {
		Snackbar.make ( ActOrder, msg, Snackbar.LENGTH_LONG ).show ( );
	}
	
	private void startActPromotion ( ) {
		Intent it = new Intent ( this, ActPromotion.class );
		startActivity ( it );
	}
	
	
}
