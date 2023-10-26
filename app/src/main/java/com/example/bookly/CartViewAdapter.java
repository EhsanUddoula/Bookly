package com.example.bookly;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class CartViewAdapter extends RecyclerView.Adapter<CartViewAdapter.CartViewHolder> {

    private ArrayList<CartItem> dataList;
    private FirebaseFirestore db;
    private String uid;
    Context context;

    public CartViewAdapter(Context context, ArrayList<CartItem> dataList,String uid) {
        this.context = context;
        this.dataList = dataList;
        this.uid=uid;
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewAdapter.CartViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem=dataList.get(position);
        holder.bookNameView.setText(dataList.get(position).getBookName());
        holder.writerView.setText(dataList.get(position).getWriter());
        holder.priceView.setText(dataList.get(position).getPrice());
        holder.amountView.setText(dataList.get(position).getAmount());

        Glide.with(context)
                .load(cartItem.getBookPic())
                .error(R.drawable.app_icon)// Assuming you have a method to get the image URL from NovelModel
                .into(holder.bookPic);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCart(cartItem,position);
            }
        });
    }

    private void deleteCart(CartItem cartItem,int position) {
        db = FirebaseFirestore.getInstance();


        if(uid == null){
            Toast.makeText(context.getApplicationContext(), "Please login first...",Toast.LENGTH_SHORT).show();
            //Intent intent= new Intent(context, loginActivity.class);
        }
        else {
           db.collection("Cart").document(uid).collection("currentUser").document(cartItem.getBookId()).delete()
                   .addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void unused) {
                           Log.d("deleteCart", "DocumentSnapshot successfully deleted!");
                           notifyDataSetChanged();
                           dataList.remove(position);
                           Toast.makeText(context.getApplicationContext(), "Removed from Cart",Toast.LENGTH_SHORT).show();
                       }
                   })
                   .addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Log.w("deleteCart", "Error deleting document", e);
                           Toast.makeText(context.getApplicationContext(), "Failed to remove",Toast.LENGTH_SHORT).show();
                       }
                   });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView bookPic,delete;
        TextView bookNameView,writerView,priceView,amountView;

        public CartViewHolder(@NonNull View itemView){
            super(itemView);
            bookPic=itemView.findViewById(R.id.bookPic);
            bookNameView=itemView.findViewById(R.id.bookNameCart);
            writerView=itemView.findViewById(R.id.writer);
            priceView=itemView.findViewById(R.id.priceBook);
            amountView=itemView.findViewById(R.id.amount);
            delete=itemView.findViewById(R.id.remove);
        }
    }
}
