package com.example.hashisushi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hashisushi.R;
import com.example.hashisushi.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterProduct extends
		RecyclerView.Adapter< AdapterProduct.MyViewHolder > {
	
	private List< Product > products;
	private Context context;
	
	public AdapterProduct ( List< Product > products, Context context ) {
		this.products = products;
		this.context = context;
	}
	
	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder ( @NonNull ViewGroup parent, int i ) {
		View itemLista = LayoutInflater.from ( parent.getContext ( ) ).
				inflate ( R.layout.products_adp_list, parent, false );
		return new MyViewHolder ( itemLista );
	}
	
	@Override
	public void onBindViewHolder ( @NonNull MyViewHolder holder, int i ) {
		
		Product product = products.get ( i );
		
		holder.nameProduction.setText ( product.getName ( ) );
		holder.descrition.setText ( String.format ( " %s", product.getDescription ( ) ) );
		holder.sales_price.setText ( String.format ( " %s", product.getSalePrice ( ).replace ( ".", "," ) ) );
		holder.idProd.setText ( String.format ( "Nº: %s", product.getIdProd ( ) ) );
		
		//Carregar imagem com picasso api
		//casea url nulll ou vazia pega uma imagem setada
		String url = product.getImgUrl ( );
		if ( url.equals ( "" ) || url == null ) {
			System.out.println ( "URL RETORN NADA - " + url );
			url = "https://firebasestorage.googleapis.com/v0/b/hashishushi.appspot.com/o/produtos%2Fimg%2F81d4f1f3-8c8a-4417-a9f4-e314f3c166fcjpeg?alt=media&token=5254b041-1674-4bfe-86e8-afbdeaeb7cd9";
			Picasso.get ( ).load ( url ).into ( holder.imgProductAd );
		} else {
			
			Picasso.get ( ).load ( url ).into ( holder.imgProductAd );
		}
	}
	
	@Override
	public int getItemCount ( ) {
		return products.size ( );
	}
	
	public class MyViewHolder extends RecyclerView.ViewHolder {
		
		//ImageView imagemProduction;
		TextView nameProduction;
		TextView descrition;
		TextView sales_price;
		TextView idProd;
		ImageView imgProductAd;
		
		public MyViewHolder ( View itemView ) {
			super ( itemView );
			
			nameProduction = itemView.findViewById ( R.id.txtNameAd );
			descrition = itemView.findViewById ( R.id.txtDescriptionAd );
			sales_price = itemView.findViewById ( R.id.txtSalesPriceAd );
			idProd = itemView.findViewById ( R.id.txtIdProductAd );
			imgProductAd = itemView.findViewById ( R.id.imgProductAd );
		}
	}
}
