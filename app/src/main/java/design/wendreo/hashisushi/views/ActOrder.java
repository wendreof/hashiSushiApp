package design.wendreo.hashisushi.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
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

import design.wendreo.hashisushi.R;
import design.wendreo.hashisushi.adapter.AdapterItensOrders;
import design.wendreo.hashisushi.dao.FirebaseConfig;
import design.wendreo.hashisushi.dao.UserFirebase;
import design.wendreo.hashisushi.model.Costs;
import design.wendreo.hashisushi.model.OrderItens;
import design.wendreo.hashisushi.model.Orders;
import design.wendreo.hashisushi.model.Product;
import design.wendreo.hashisushi.model.User;
import design.wendreo.hashisushi.utils.MockPaymentMethods;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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
	
	public static boolean STATUS;
	public String EntregaRetira = "";
	DatabaseReference reference;
	Activity activity;
	private AdapterItensOrders adapter;
	
	private TextView txtTitle;
	private TextView txtPedido;
	private TextView txtTotal;
	private TextView txtDesconto;
	private TextView textCostDelivery;
	private Spinner spnFillPayment;
	private RadioButton chkBxRetirar;
	private RadioButton chkBxEntrega;
	private EditText editObservation;
	private EditText edtStreetDelivery;
	private EditText edtNumberDelivery;
	private EditText edtNeighborhoodDelivery;
	private Button btnFinishOrder;
	private String emailUser;
	private ListView lstOrder;
	private AlertDialog dialog;
	private String retornIdUser;
	private User user;
	private Costs costs;
	private Orders ordersRecovery;
	private ScrollView ActOrder;
	
	private List< OrderItens > itensCars = new ArrayList<> ( );
	private List< Product > productsList = new ArrayList<> ( );
	private List< Orders > ordersList = new ArrayList<> ( );
	
	private int qtdItensCar;
	private Double totalCar;
	private Double desconto;
	private Double entregaCusto;
	private Orders orders;
	
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
		recuperaDesconto ( ); //recupera o desconto
		getDate ( ); // verica hora atual e altera o STATUS (true or false)
		recoveryCostDelivery ( );//recupera custo entrega
	}
	
	//finaliza se voltar
	@Override
	public void onBackPressed ( ) {
		finish ( );
	}
	
	//recupera desconto enviado por usuario
	private void recuperaDesconto ( ) {
		String desc = "0,00";
		
		if ( System.getProperty ( "DESCONTO_ENV" ) != null ) {
			
			desc = System.getProperty ( "DESCONTO_ENV" );
			
			if ( desc.equals ( "30,00" ) ) {
				txtDesconto.setText ( desc );
			} else {
				txtDesconto.setText ( "0,00" );
			}
			
		}
	}
	
	@Override
	protected void attachBaseContext ( Context newBase ) {
		super.attachBaseContext ( CalligraphyContextWrapper.wrap ( newBase ) );
	}
	
	//Altera fonte do txtLogo
	private void fontLogo ( ) {
		Typeface font = Typeface.createFromAsset ( getAssets ( ), "RagingRedLotusBB.ttf" );

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
		Toast.makeText ( getApplicationContext ( ), msg, Toast.LENGTH_LONG ).show ( );
	}
	
	//captura os clicks dos botões
	@Override
	public void onClick ( View v ) {
		if ( v.getId ( ) == R.id.btnFinishOrder ) {
			startVibrate ( 190 );
			valueTest ( );
		} else if ( v.getId ( ) == R.id.chkBxRetirar ) {

			//msgShort ( "Retirar" );
			edtStreetDelivery.setText ( "Rua São Pedro" );
			edtNeighborhoodDelivery.setText ( "Centro" );
			edtNumberDelivery.setText ( "661" );
			//seta valor da entraga recuperado
			textCostDelivery.setText( "0,00");

		} else if ( v.getId ( ) == R.id.chkBxEntrega ) {
			msgShort ( "Clique 2x nos campos para alterar o endereço de entrega!" );

			//seta valor da entraga recuperado
			textCostDelivery.setText( costs.getCustoEntrega() );

			// permite alterar os dados do endereço de entrega!
			edtStreetDelivery.setEnabled ( true );
			edtNeighborhoodDelivery.setEnabled ( true );
			edtNumberDelivery.setEnabled ( true );
			
			//trata null pointer casp orders_user não exita ainda para o usuario
			if ( ordersRecovery != null ) {
				edtStreetDelivery.setText ( ordersRecovery.getAddress ( ) );
				edtNeighborhoodDelivery.setText ( ordersRecovery.getNeigthborhood ( ) );
				edtNumberDelivery.setText ( ordersRecovery.getNumberHome ( ) );
				
			} else {
				msgShort ( "Voçê não tem itens no carrinho. \n" +
						"Primeiro adicione itens para definir o endereço. \n" );
			}
		} else if ( v.getId ( ) == R.id.edtStreetDelivery
				|| v.getId ( ) == R.id.edtNeighborhoodDelivery
				|| v.getId ( ) == R.id.edtNumberDelivery ) {
			edtStreetDelivery.setText ( "" );
			edtNeighborhoodDelivery.setText ( "" );
			edtNumberDelivery.setText ( "" );
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
		String neighborhood = edtNeighborhoodDelivery.getText ( ).toString ( );
		String streetDelivery = edtStreetDelivery.getText ( ).toString ( );
		String numberDelivery = edtNumberDelivery.getText ( ).toString ( );
		
		if ( streetDelivery.equals ( "" ) || neighborhood.equals ( "" ) || numberDelivery.equals ( "" ) ) {
			msgShort ( "Por favor, informe o endereço completo para entrega." );
			
		} else {
			double value = Double.parseDouble (
					txtTotal.getText ( )
							.toString ( )
							.replace ( "R$ ", "" )
							.replace ( ",", "." ) );
			
			if ( value <= 0 ) {
				msgShort ( "Não há itens para finalizar o pedido! :[" );
			} else
				confirmOrder ( );
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
			public void onCancelled ( DatabaseError databaseError ) { }
		} );
	}

	//recupera custo de entrega
	private void recoveryCostDelivery ( ) {

		DatabaseReference costsDB = reference.child ("costs").child("-LlDgMNFRyCWC1bjkL55");

		costsDB.addListenerForSingleValueEvent ( new ValueEventListener ( ) {
			@Override
			public void onDataChange ( DataSnapshot dataSnapshot ) {

				if ( dataSnapshot.getValue ( ) != null ) {
					costs = dataSnapshot.getValue ( Costs.class );
				}
			}
			@Override
			public void onCancelled ( DatabaseError databaseError ) { }
		} );
	}
	
	//confimar pedido
	private void confirmOrder ( ) {
		if ( chkBxEntrega.isChecked ( ) ) {
			EntregaRetira = "A ser entregue em: ";
		} else
			EntregaRetira = "A ser retirado em: ";
		
		AlertDialog.Builder builder = new AlertDialog.Builder ( this );
		builder.setTitle ( "Finalizar Pedido" );
		
		//captura valores
		 String strCustoEntrega = textCostDelivery.getText().toString();
		String strDesconto = txtDesconto.getText ( ).toString ( );
		String strTotal = txtTotal.getText ( ).toString ( );

		double dblCustoEntrega = Double.parseDouble (
				strCustoEntrega.replace ( ",", "." ) );

		double dblDesconto = Double.parseDouble (
				strDesconto.replace ( ",", "." ) );
		
		//troca vigula por ponto
		double dblTotal = Double.parseDouble (
				strTotal.replace ( ",", "." ) );

		//se custo de entrega maior que zero soma em total
		if ( dblCustoEntrega > 0 )
		{
			dblTotal = dblTotal + dblCustoEntrega;
		}
		
		//calcula desconto
		final Double totalComDesconto = dblTotal - dblDesconto;
		//formato decimal
		DecimalFormat df = new DecimalFormat ( "0.00" );

		builder.setMessage ( "\nDeseja confirmar o pedido de:\n" +
				"R$: " + df.format ( totalComDesconto )+ " - " + spnFillPayment.getSelectedItem ( ) +
				"\n\n" + EntregaRetira + "\n" +
				edtStreetDelivery.getText ( ) + ", nº " +
				edtNumberDelivery.getText ( ) + " - " +
				edtNeighborhoodDelivery.getText ( )
		);
		
		builder.setPositiveButton ( "Confirmar", new DialogInterface.OnClickListener ( ) {
			@Override
			public void onClick ( DialogInterface dialog, int which ) {
				
				//se o restaurante estiver aberto, continua com finalização do pedido!
				if ( STATUS ) {
					
					SimpleDateFormat dateFormat_data = new SimpleDateFormat ( "dd/MM/yyyy" );
					SimpleDateFormat horaFormat_hora = new SimpleDateFormat ( "HH:mm" );
					Calendar cal = Calendar.getInstance ( );
					
					Date data_atual = cal.getTime ( );
					
					String hora = horaFormat_hora.format ( data_atual );
					String dataAtual = dateFormat_data.format ( data_atual );
					
					ordersRecovery.setDateOrder ( dataAtual );
					ordersRecovery.setHour ( hora );
					String obs = editObservation.getText ( ).toString ( );
					ordersRecovery.setAddress ( edtStreetDelivery.getText ( ).toString ( ) );
					ordersRecovery.setNumberHome ( edtNumberDelivery.getText ( ).toString ( ) );
					ordersRecovery.setNeigthborhood ( edtNeighborhoodDelivery.getText ( ).toString ( ) );
					ordersRecovery.setObservation ( obs );
					ordersRecovery.setQuantProd ( qtdItensCar );

					//recupera custo e converte troca , por .
					String strCustoEntregaRecuperado = textCostDelivery.getText().toString( ) ;
					entregaCusto = Double.parseDouble (
							strCustoEntregaRecuperado.replace ( ",", "." ) );

					ordersRecovery.setDeliveryCost( strCustoEntregaRecuperado );
					
					//recupera desconto e e converte troca , por .
					String strDescontoRecuperado = txtDesconto.getText ( ).toString ( );
					desconto = Double.parseDouble (
							strDescontoRecuperado.replace ( ",", "." ) );
					
					ordersRecovery.setDiscont ( desconto );

					//Recupera pontos
					int p = user.getPonts ( );
					
					//se  maior 0 ponto  igual zero
					// total recebe totalCar - desconto
					//seta total zera e atualiza pontos
					if ( desconto > 0 && p == 15 )
					{
						Double total = totalCar - desconto;
						
						ordersRecovery.setTotalPrince ( total );
						//zera pontos atualiza
						p = 0;
						user.uploadPonts ( p );
						System.clearProperty ( "DESCONTO_ENV" );

					} if( entregaCusto > 0 )
					{
						// se custo de entrega maior zero soma em total
						Double totalComEntrega = totalCar + entregaCusto;

						ordersRecovery.setTotalPrince ( totalComEntrega );

					}else {
						ordersRecovery.setTotalPrince ( totalCar );
					}
					
					/* Gera ponto compra maior 30
					 * Removido ***
					 */
					
					ordersRecovery.setStatus ( "confirmado" );
					ordersRecovery.confimar ( );
					
					orders = ordersRecovery;
					
					ordersRecovery.remover ( );
					ordersRecovery = null;
					
					msgShort ( "Pedido Confirmado" );
					startActWailt ( );
					finish ( );
				} else {
					//se o restaurante estiver fechado, não permite prosseguir com o pedido!
					msgShort ( "Você poderá finalizar um pedido quando estivermos abertos! :)" );
				}
			}
			
		} ).setNegativeButton ( "Cancelar", new DialogInterface.OnClickListener ( ) {
			@Override
			public void onClick ( DialogInterface dialog, int which ) {
				msgShort ( "Pedido não confirmado !" );
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
					
					adapter = new AdapterItensOrders ( getApplicationContext ( ), itensCars );
					
					lstOrder.setAdapter ( adapter );
				}
				dialog.dismiss ( );
			}
			
			@Override
			public void onCancelled ( DatabaseError databaseError ) {
			
			}
		} );
	}
	
	//recupera todos os ids e seta os listeners
	private void findViewByIds ( )
	{
		spnFillPayment = findViewById ( R.id.spnfillPayMent );
		txtTitle = findViewById ( R.id.txtTitleReg );
		txtDesconto = findViewById ( R.id.txtDesconto );
		txtPedido = findViewById ( R.id.txtPedido );
		textCostDelivery = findViewById(R.id.textCostDelivery);
		txtTotal = findViewById ( R.id.txtTotal );
		chkBxRetirar = findViewById ( R.id.chkBxRetirar );
		chkBxEntrega = findViewById ( R.id.chkBxEntrega );
		btnFinishOrder = findViewById ( R.id.btnFinishOrder );
		editObservation = findViewById ( R.id.editObservation );
		lstOrder = findViewById ( R.id.lstOrder );
		edtStreetDelivery = findViewById ( R.id.edtStreetDelivery );
		edtNumberDelivery = findViewById ( R.id.edtNumberDelivery );
		edtNeighborhoodDelivery = findViewById ( R.id.edtNeighborhoodDelivery );
		
		// seta os listeners
		chkBxRetirar.setOnClickListener ( this );
		chkBxEntrega.setOnClickListener ( this );
		edtStreetDelivery.setOnClickListener ( this );
		edtNumberDelivery.setOnClickListener ( this );
		edtNeighborhoodDelivery.setOnClickListener ( this );
		
	}
	
	//captura o click no listview
	private void lstorderClick ( )
	{
		lstOrder.setOnItemClickListener ( new AdapterView.OnItemClickListener ( ) {
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
						
						//adapter.remove ( adapter.getItem ( position ) ); //remove do listview
						//adp chama metudo remover
						adapter.remove ( position );
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
	
	// Inicializa activityPromotion
	private void startActWailt  ( )
	{
		Intent i = new Intent ( getApplicationContext ( ), ActWait.class );
		startActivity ( i );
	}
	
	// verica hora atual e altera o STATUS aberto ou fechado  (true or false)
	private void getDate ( )
	{
		SimpleDateFormat dateFormat_hora = new SimpleDateFormat ( "HHmm" );
		Calendar cal = Calendar.getInstance ( );
		Date data_atual = cal.getTime ( );
		
		String hora_atual = dateFormat_hora.format ( data_atual );
		int intHora = Integer.parseInt ( hora_atual );
		
		STATUS = intHora > 900 && intHora < 2200;
	}
}
