package com.example.bookly;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class popAdapter extends RecyclerView.Adapter<popAdapter.myViewHolder>{
    Context context;
    private ArrayList<popBook>dataList;
    String uid;

    public popAdapter(Context context, ArrayList<popBook> dataList,String uid){
        this.context=context;
        this.dataList=dataList;
        this.uid=uid;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recview,parent,false);
        return new popAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull popAdapter.myViewHolder holder, int position) {
        popBook pop=dataList.get(position);
        holder.book.setText(dataList.get(position).getBook());
        holder.writer.setText(dataList.get(position).getWriter());
        holder.price.setText(dataList.get(position).getPrice());

        Glide.with(context)
                .load(pop.getImage())
                .error(R.drawable.app_icon)// Assuming you have a method to get the image URL from NovelModel
                .into(holder.bookPic);

        holder.bookPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(holder.bookPic.getContext(), BookDetails.class);
                intent.putExtra("tag",uid);
                intent.putExtra("collect",dataList.get(position).getGenre());
                intent.putExtra("book",dataList.get(position).getBookId());
                intent.putExtra("action","Popular");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.bookPic.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView book,writer,price;
        ImageView bookPic;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            book=itemView.findViewById(R.id.bookText);
            writer=itemView.findViewById(R.id.writerText);
            price=itemView.findViewById(R.id.bookPrice);
            bookPic=itemView.findViewById(R.id.popBook);
        }
    }
}
