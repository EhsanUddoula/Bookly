package com.example.bookly;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.myViewHolder> {
    ArrayList<NovelModel> dataList;

    public myAdapter(ArrayList<NovelModel> dataList){
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
        holder.text1.setText(dataList.get(position).getBook());
        holder.text2.setText(dataList.get(position).getWriter());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView text1,text2;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            text1=itemView.findViewById(R.id.bookName);
            text2=itemView.findViewById(R.id.writerName);
        }
    }
}
