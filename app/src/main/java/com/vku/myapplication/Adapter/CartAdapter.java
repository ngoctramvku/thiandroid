package com.vku.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vku.myapplication.Model.Cart;
import com.vku.myapplication.Model.Product;
import com.vku.myapplication.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    ArrayList<Cart> carts;
    Context context;
    private OnItemClickListener removeitem;

    public interface OnItemClickListener{
        void OnItemClick(int i);
    }

    public void setOnRemoveItem(OnItemClickListener removeItem){
        removeitem = removeItem;
    }

    public CartAdapter(ArrayList<Cart> cart, Context context) {
        this.carts = cart;
        this.context = context;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemview = layoutInflater.inflate(R.layout.cart, parent, false);
        context = parent.getContext();
        return new CartAdapter.ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart p = carts.get(position);
        Picasso.with(context).load("http://192.168.2.113/doancoso/public/img/" + p.getImage()).into(holder.imgHinh);
        holder.txtName.setText(p.getName());
        holder.txtPrice.setText(p.getPrice() +" "+ "VNĐ");
        holder.etQuantity.setText("1");

    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtPrice;
        EditText etQuantity;
        ImageView imgHinh;
        Button btnDelete;
        Button btnthanhtoan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
            imgHinh = (ImageView) itemView.findViewById(R.id.imgImage);
            etQuantity = (EditText) itemView.findViewById(R.id.etsl);
            btnDelete = (Button) itemView.findViewById(R.id.btnxoa);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (removeitem != null){
                        int i = getAdapterPosition();
                        if (i != RecyclerView.NO_POSITION){
                            removeitem.OnItemClick(i);
                        }
                    }
                }
            });
        }
    }
}
