package com.example.hashisushi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hashisushi.R;
import com.example.hashisushi.model.OrderItens;

import java.util.List;

public class AdapterItensOrders extends BaseAdapter {

    private Context context;
    private List<OrderItens> itensList;

    public AdapterItensOrders(Context context, List<OrderItens> list) {
        this.context = context;
        this.itensList = list;
    }

    @Override
    public int getCount() {
        return itensList.size();
    }

    @Override
    public Object getItem(int position) {
        return itensList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        OrderItens orderItens = itensList.get(position);

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.itens_adp_list, null);

        TextView idProd = (TextView) view.findViewById(R.id.txtIdProd_It);
        idProd.setText("Cod Produto :" + String.valueOf(orderItens.getIdProduct()));

        TextView nameProd = (TextView) view.findViewById(R.id.txtName_It);
        nameProd.setText("Nome :" + orderItens.getNameProduct());

        TextView quantidade = (TextView) view.findViewById(R.id.txtQuant_It);
        quantidade.setText("Qt :" + orderItens.getQuantity());

        TextView valor = (TextView) view.findViewById(R.id.txtSalesPrice_It);
        valor.setText("R$: " + orderItens.getItenSalePrice());

        return view;
    }

    public void remove(int position){

        this.itensList.remove(position);
         notifyDataSetChanged();

    }
}