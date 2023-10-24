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

public class CartViewAdapter extends RecyclerView.Adapter<CartViewAdapter.CartViewHolder> {

    private ArrayList<CartItem> dataList;
    private FirebaseFirestore db;


    Context context;

    public CartViewAdapter(Context context, ArrayList<CartItem> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewAdapter.CartViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.bookNameView.setText(dataList.get(position).getBookName());
        holder.writerView.setText(dataList.get(position).getWriter());
        holder.priceView.setText(dataList.get(position).getPrice());
        holder.amountView.setText(dataList.get(position).getAmount());

        Glide.with(context)
                .load(CartItem.getBookPic())
                .error(R.drawable.app_icon)// Assuming you have a method to get the image URL from NovelModel
                .into(holder.bookPicView);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView bookPicView;
        TextView bookNameView,writerView,priceView,copiesView,amountView;

        public CartViewHolder(@NonNull View itemView){
            super(itemView);
            bookPicView=itemView.findViewById(R.id.bookpic);
            bookNameView=itemView.findViewById(R.id.bookname);
            writerView=itemView.findViewById(R.id.writer);
            priceView=itemView.findViewById(R.id.price);
            copiesView=itemView.findViewById(R.id.copies);
            amountView=itemView.findViewById(R.id.amount);
        }
    }
}
