package com.example.hashisushi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hashisushi.R;
import com.example.hashisushi.model.Orders;

import java.util.List;

public class AdapterStatusOrders extends RecyclerView.Adapter<AdapterStatusOrders.MyViewHolder> {

    private List<Orders> ordersList;
    private Context context;


    public AdapterStatusOrders(List<Orders> ordersList, Context context) {
        this.ordersList = ordersList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLista = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.orders_status_adp_list, parent, false);
        return new MyViewHolder(itemLista);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        Orders orders = ordersList.get(i);

        holder.idOrders.setText("Cod Pedido :" + orders.getIdOrders());
        holder.name.setText("Nome :" + orders.getName());
        holder.dateOrder.setText("Data :" + orders.getDateOrder());
        holder.hour.setText("Hora :" + orders.getHour());
        holder.quantProd.setText("Qt itens :" + orders.getQuantProd());
        holder.discont.setText("Desconto :" + orders.getDiscont());
        holder.status.setText("Status :" + orders.getStatus());
        holder.totalPrince.setText("Total do Pedido:" + orders.getTotalPrince());
    }

    @Override
    public int getItemCount() {

        return ordersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView idOrders;
        TextView name;
        TextView dateOrder;
        TextView hour;
        TextView quantProd;
        TextView discont;
        TextView status;
        TextView totalPrince;
        TextView observation;

        public MyViewHolder(View itemView) {
            super(itemView);

            idOrders = itemView.findViewById(R.id.txtIdOrder);
            name = itemView.findViewById(R.id.txtNameClient);
            dateOrder = itemView.findViewById(R.id.txtDateOrder);
            hour = itemView.findViewById(R.id.txtHour);
            quantProd = itemView.findViewById(R.id.txtQuatProtod);
            discont = itemView.findViewById(R.id.txtDiscont);
            totalPrince = itemView.findViewById(R.id.txtTotalPrince);
            observation = itemView.findViewById(R.id.txtObservation);
            status = itemView.findViewById(R.id.txtStatus);

        }
    }

    public void updateListOrdes(int position) {

        this.ordersList.remove(position);
        notifyDataSetChanged();

    }

}