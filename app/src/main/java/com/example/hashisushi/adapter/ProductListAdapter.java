package com.example.hashisushi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hashisushi.R;
import com.example.hashisushi.model.Product;
import com.squareup.picasso.Picasso;


import java.util.List;

public class ProductListAdapter extends BaseAdapter {

    private Context context;
    private List<Product>products;

    public ProductListAdapter(Context context,List<Product> products){
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Product p = products.get(position);

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.products_adp_list,null);

        TextView name = view.findViewById(R.id.txtNameAd);
        name.setText(""+(p.getName()));

        TextView description = view.findViewById(R.id.txtDescriptionAd);
        description.setText("Descrição: "+(p.getDescription()));

        TextView txtsalesPrice =  view.findViewById(R.id.txtSalesPriceAd);
        txtsalesPrice.setText("Preço: "+(p.getSalePrice()));

        TextView idProduction = view.findViewById(R.id.txtIdProductAd);
        idProduction.setText("Nº: "+(p.getIdProd()));

        //Carregar imagem
        ImageView imgProdution = view.findViewById(R.id.imgProductAd);

        String urlselecionada = p.getImgUrl();

        Picasso.get().load(urlselecionada).into(imgProdution);


        return view;
    }
}
