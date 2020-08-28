package com.vku.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vku.myapplication.Model.Product;
import com.vku.myapplication.R;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    ArrayList<Product> products;
    Context context;
    OnProductListener onProductListener;

    public ProductAdapter(ArrayList<Product> products, Context context, OnProductListener onProductListener) {
        this.products = products;
        this.context = context;
        this.onProductListener = onProductListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemview = layoutInflater.inflate(R.layout.item_product, parent, false);
        context = parent.getContext();
        return new ViewHolder(itemview, onProductListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Product p = products.get(position);
        Picasso.with(context).load("http://192.168.2.113/doancoso/public/img/" + p.getImg()).into(holder.imgHinh);
        holder.txtName.setText(p.getName() + "");
        holder.txtPrice.setText(p.getPrice() + " "+ "VNĐ");

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtName, txtPrice;
        ImageView imgHinh;
        Button btnAddProduct;

        private OnProductListener onProductListener;

        public ViewHolder(@NonNull View itemView, OnProductListener onProductListener) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
            imgHinh = (ImageView) itemView.findViewById(R.id.imgImage);
            btnAddProduct = (Button) itemView.findViewById(R.id.btnadd);

            this.onProductListener = onProductListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onProductListener.onProductClick(getAdapterPosition());
        }
    }

    public interface OnProductListener {
        void onProductClick(int position);
    }
}
