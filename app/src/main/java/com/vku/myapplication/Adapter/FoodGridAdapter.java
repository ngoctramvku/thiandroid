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
import com.vku.myapplication.Food_Activity;
import com.vku.myapplication.Model.Food;
import com.vku.myapplication.R;

import java.util.ArrayList;

public class FoodGridAdapter extends RecyclerView.Adapter<FoodGridAdapter.ViewHolder> {
    ArrayList<Food> foods;
    Context context;

    public FoodGridAdapter(ArrayList<Food> foods, Context context) {
        this.foods = foods;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_food_gridview, parent,false);
        context = parent.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food fo = foods.get(position);
        holder.txtName.setText(fo.getFoodname()+"");
        holder.txtPrice.setText(fo.getPrice()+" "+ "VNĐ");
        holder.foodid = fo.getFoodid();
        Picasso.with(context).load("http://192.168.2.113/doancoso/public/img/"+fo.getImg()).into(holder.imgHinh);

    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtName, txtPrice;
        ImageView imgHinh;
        String foodid;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = (TextView)itemView.findViewById(R.id.txtName);
            txtPrice = (TextView)itemView.findViewById(R.id.txtPrice);
            imgHinh = (ImageView)itemView.findViewById(R.id.imgImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, Food_Activity.class);
                    i.putExtra("foodid",foodid);
                    context.startActivity(i);
                }
            });
        }
    }
}
