package com.example.bookly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CartViewAdapter extends RecyclerView.Adapter<CartViewAdapter.Cart_ViewHolder> {

    private ArrayList<CartItem> dataList;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;


    Context context;

    public CartViewAdapter(Context context, ArrayList<CartItem> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public Cart_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewAdapter.Cart_ViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Cart_ViewHolder holder, int position) {
        holder.booknameView.setText(dataList.get(position).getBookName());
        holder.writerView.setText(dataList.get(position).getWriter());
        holder.priceView.setText(dataList.get(position).getPrice());
        holder.amountView.setText(dataList.get(position).getAmount());

        Glide.with(context)
                .load(CartItem.getBookPic())
                .error(R.drawable.app_icon)// Assuming you have a method to get the image URL from NovelModel
                .into(holder.bookpicView);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class Cart_ViewHolder extends RecyclerView.ViewHolder {
        ImageView bookpicView;
        TextView booknameView,writerView,priceView,copiesView,amountView;

        public Cart_ViewHolder(@NonNull View itemView){
            super(itemView);
            bookpicView=itemView.findViewById(R.id.bookpic);
            booknameView=itemView.findViewById(R.id.bookname);
            writerView=itemView.findViewById(R.id.writer);
            priceView=itemView.findViewById(R.id.price);
            copiesView=itemView.findViewById(R.id.copies);
            amountView=itemView.findViewById(R.id.amount);
        }
    }
}
