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

import com.android.volley.Response;
import com.squareup.picasso.Picasso;
import com.vku.myapplication.Model.Fashion;
import com.vku.myapplication.R;

import java.util.ArrayList;

public class FashionAdapter extends RecyclerView.Adapter<FashionAdapter.ViewHolder> {
    ArrayList<Fashion> fashions;
    Context context;
    OnFashionListener onfashionListener;

    public FashionAdapter(ArrayList<Fashion> fashions, Context context, OnFashionListener fashionListener) {
        this.fashions = fashions;
        this.context = context;
        this.onfashionListener = fashionListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemview = layoutInflater.inflate(R.layout.item_fashion, parent, false);
        context = parent.getContext();
        return new ViewHolder(itemview, onfashionListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fashion f = fashions.get(position);
        holder.txtName.setText(f.getName()+"");
        holder.txtPrice.setText(f.getPrice()+" "+ "VNĐ");
        Picasso.with(context).load("http://192.168.2.113/doancoso/public/img/"+f.getImg()).into(holder.imgHinh);
    }

    @Override
    public int getItemCount() {
        return fashions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView txtName, txtPrice;
        ImageView imgHinh;
        Button btnaddfas;
        private OnFashionListener onFashionListener;

        public ViewHolder(@NonNull View itemView, OnFashionListener onFashionListener )
        {
            super(itemView);
            txtName = (TextView)itemView.findViewById(R.id.txtName);
            txtPrice = (TextView)itemView.findViewById(R.id.txtPrice);
            imgHinh =(ImageView)itemView.findViewById(R.id.imgImage);
            btnaddfas = (Button)itemView.findViewById(R.id.btnadd);

            this.onFashionListener = onFashionListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onFashionListener.onFashionClick(getAdapterPosition());
        }
    }
    public interface OnFashionListener {
        void onFashionClick(int position);
    }
}
