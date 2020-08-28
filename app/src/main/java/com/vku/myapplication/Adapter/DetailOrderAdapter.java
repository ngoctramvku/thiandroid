package com.vku.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vku.myapplication.Model.Cart;
import com.vku.myapplication.Model.DetailOrder;
import com.vku.myapplication.R;

import java.util.ArrayList;

public class DetailOrderAdapter extends RecyclerView.Adapter<DetailOrderAdapter.ViewHolder>{
    ArrayList<DetailOrder> detailOrders;
    Context context;

    public DetailOrderAdapter(ArrayList<DetailOrder> detailOrders, Context context) {
        this.detailOrders = detailOrders;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemview = layoutInflater.inflate(R.layout.detail_order, parent, false);
        context = parent.getContext();
        return new  ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetailOrder d = detailOrders.get(position);
        Picasso.with(context).load("http://192.168.2.113/doancoso/public/img/" + d.getImage()).into(holder.imgHinh);
//        holder.txtName.setText(d.getName());
//        holder.txtsl.setText(d.getQuantity());
//        holder.txtprice.setText(d.getPrice());
    }

    @Override
    public int getItemCount() {
        return detailOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtiddonhang, txtName, txtsl, txtprice;
        ImageView imgHinh;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtiddonhang = (TextView)itemView.findViewById(R.id.txtiddonhang);
            txtName =(TextView)itemView.findViewById(R.id.txtName);
            txtsl = (TextView)itemView.findViewById(R.id.txtsl);
            txtprice =(TextView)itemView.findViewById(R.id.txtprice);
            imgHinh = (ImageView) itemView.findViewById(R.id.imgImage);
        }
    }
}
