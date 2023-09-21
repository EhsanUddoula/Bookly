package com.example.bookly;

import android.content.Context;
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

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.myViewHolder> {
    private ArrayList<NovelModel> dataList;

    Context context;

    public myAdapter(Context context,ArrayList<NovelModel> dataList){
        this.context=context;
        this.dataList=dataList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookview,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        NovelModel model=dataList.get(position);
        holder.bookName.setText(dataList.get(position).getBook());
        holder.writerName.setText(dataList.get(position).getWriter());
        holder.price.setText(dataList.get(position).getPrice());
        Glide.with(context)
                .load(model.getImage())
                .error(R.drawable.nineteen84)// Assuming you have a method to get the image URL from NovelModel
                .into(holder.imageName);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView bookName,writerName,price;

        ImageView imageName;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName=itemView.findViewById(R.id.bookName);
            writerName=itemView.findViewById(R.id.writerName);
            imageName=itemView.findViewById(R.id.imageName);
            price=itemView.findViewById(R.id.price);
        }
    }
}
