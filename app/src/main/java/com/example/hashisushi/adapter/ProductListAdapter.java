package com.example.hashisushi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hashisushi.R;
import com.example.hashisushi.model.Product;

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

        TextView txtName = view.findViewById(R.id.txtNameAd);
        txtName.setText(""+(p.getName()));

        TextView txtDescription = view.findViewById(R.id.txtDescriptionAd);
        txtDescription.setText("Descrição: " + p.getDescription());

        TextView txtSalesPrice =  view.findViewById(R.id.txtSalesPriceAd);
        txtSalesPrice.setText("Preço: " + p.getSalePrice());

        TextView txtIdProduction = view.findViewById(R.id.txtIdProductAd);
        txtIdProduction.setText("Nº: " + String.valueOf( p.getIdProd()));


        return view;
    }
}
