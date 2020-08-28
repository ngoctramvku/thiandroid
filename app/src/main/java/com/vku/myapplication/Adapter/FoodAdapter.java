package com.vku.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.vku.myapplication.Food_Activity;
import com.vku.myapplication.Model.Food;
import com.vku.myapplication.R;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    ArrayList<Food> foods;
    Context context;
    OnFoodListener onFoodListener;

    public FoodAdapter(ArrayList<Food> foods, Context context, OnFoodListener onFoodListener) {
        this.foods = foods;
        this.context = context;
        this.onFoodListener = onFoodListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemview = layoutInflater.inflate(R.layout.item_food, parent, false);
        context = parent.getContext();
        return new ViewHolder(itemview, onFoodListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food fo = foods.get(position);
        holder.txtName.setText(fo.getFoodname()+"");
        holder.txtPrice.setText(fo.getPrice()+" "+ "VNĐ");
        Picasso.with(context).load("http://192.168.2.113/doancoso/public/img/"+fo.getImg()).into(holder.imgHinh);
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtName, txtPrice;
        ImageView imgHinh;
        Button btnadd;
        private OnFoodListener onFoodListener;
        public ViewHolder(@NonNull View itemView, OnFoodListener onFoodListener) {

            super(itemView);
            txtName =  (TextView)itemView.findViewById(R.id.txtName);
            txtPrice = (TextView)itemView.findViewById(R.id.txtPrice);
            imgHinh = (ImageView)itemView.findViewById(R.id.imgImage);
            this.onFoodListener = onFoodListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onFoodListener.onFoodClick(getAdapterPosition());
        }
    }
    public interface OnFoodListener{
        void onFoodClick(int position);
    }

}
