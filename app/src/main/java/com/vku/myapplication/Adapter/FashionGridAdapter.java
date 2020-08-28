package com.vku.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vku.myapplication.Fashion_Activity;
import com.vku.myapplication.Model.Fashion;
import com.vku.myapplication.Model.Product;
import com.vku.myapplication.R;

import java.util.ArrayList;

public class FashionGridAdapter extends RecyclerView.Adapter<FashionGridAdapter.ViewHolder> {
    ArrayList<Fashion> fashiongrids;
    Context context;

    public FashionGridAdapter(ArrayList<Fashion> fashiongrids, Context context) {
        this.fashiongrids = fashiongrids;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemview = layoutInflater.inflate(R.layout.item_fashion_gridview, parent, false);
        context = parent.getContext();

        return new  ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fashion f = fashiongrids.get(position);
        Picasso.with(context).load("http://192.168.2.113/doancoso/public/img/"+f.getImg()).into(holder.imgHinh);
        holder.txtName.setText(f.getName()+"");
        holder.txtPrice.setText(f.getPrice()+" "+ "VNĐ");
        holder.fasid = f.getFasid();

    }

    @Override
    public int getItemCount() {
        return fashiongrids.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtName, txtPrice;
        ImageView imgHinh;
        String fasid;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = (TextView)itemView.findViewById(R.id.txtName);
            txtPrice = (TextView)itemView.findViewById(R.id.txtPrice);
            imgHinh = (ImageView) itemView.findViewById(R.id.imgImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, Fashion_Activity.class);
                    i.putExtra("fasid", fasid);
                    context.startActivity(i);
                }
            });


        }
    }
}
