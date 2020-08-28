package com.vku.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vku.myapplication.Model.Product;
import com.vku.myapplication.ProductActivity;
import com.vku.myapplication.R;

import java.util.ArrayList;

public class ProductGridAdapter extends RecyclerView.Adapter<ProductGridAdapter.ViewHolder> {
    ArrayList<Product> productgrids;
    Context context;

    public ProductGridAdapter(ArrayList<Product> productgrids, Context context) {
        this.productgrids = productgrids;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemview = layoutInflater.inflate(R.layout.item_product_gridview, parent, false);
        context = parent.getContext();

        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Product p = productgrids.get(position);

        holder.id = p.getId();


        Picasso.with(context).load("http://192.168.2.113/doancoso/public/img/"+p.getImg()).into(holder.imgHinh);

        holder.txtName.setText(p.getName()+"");
        holder.txtPrice.setText(p.getPrice()+" "+ "VNĐ");



    }

    @Override
    public int getItemCount() {
        return productgrids.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        TextView txtName, txtPrice;
        ImageView imgHinh;
        CardView cardView;
        int id = 0;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = (TextView)itemView.findViewById(R.id.txtName);
            txtPrice = (TextView)itemView.findViewById(R.id.txtPrice);
            imgHinh = (ImageView)itemView.findViewById(R.id.imgImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ProductActivity.class);
                    i.putExtra("id",id);
                    context.startActivity(i);
                }
            });
        }
    }
}
