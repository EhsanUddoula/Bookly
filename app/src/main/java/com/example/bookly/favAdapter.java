package com.example.bookly;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class favAdapter extends RecyclerView.Adapter<favAdapter.myViewHolder> {
    private ArrayList<favBook> dataList;
    private FirebaseFirestore db;

    private ProgressBar progressBar;

    private String uid;
    Context context;

    public favAdapter(Context context,ArrayList<favBook> dataList,String uid,ProgressBar progressBar){
        this.context=context;
        this.dataList=dataList;
        this.uid=uid;
        this.progressBar=progressBar;
    }

    @NonNull
    @Override
    public favAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favview,parent,false);
        return new favAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull favAdapter.myViewHolder holder, int position) {
        favBook model=dataList.get(position);
        holder.bookName.setText(dataList.get(position).getBookName());
        holder.writerName.setText(dataList.get(position).getWriter());
        Glide.with(context)
                .load(model.getBookPic())
                .error(R.drawable.app_icon)// Assuming you have a method to get the image URL from NovelModel
                .into(holder.image);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(holder.image.getContext(), BookDetails.class);
                intent.putExtra("tag",uid);
                intent.putExtra("collect",dataList.get(position).getGenre());
                intent.putExtra("book",dataList.get(position).getBookId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.image.getContext().startActivity(intent);
            }
        });
        holder.delImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = FirebaseFirestore.getInstance();

                progressBar.setVisibility(View.VISIBLE);
                if(uid == null){
                    Toast.makeText(context.getApplicationContext(), "Please login first...",Toast.LENGTH_SHORT).show();
                    //Intent intent= new Intent(context, loginActivity.class);
                }
                else {
                    db.collection("Favourites").document(uid).collection("currentUser").document(model.getBookId()).delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("deleteFav", "DocumentSnapshot successfully deleted!");
                                    progressBar.setVisibility(View.GONE);
                                    notifyDataSetChanged();
                                    dataList.remove(position);
                                    notifyItemRemoved(position);
                                    Toast.makeText(context.getApplicationContext(), "Removed from Favourite",Toast.LENGTH_SHORT).show();
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
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView bookName,writerName;
        ImageView image,delImg;
        Button button;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName=itemView.findViewById(R.id.fabBook);
            writerName=itemView.findViewById(R.id.favWriter);
            image=itemView.findViewById(R.id.favImage);
            button=itemView.findViewById(R.id.favButton);
            delImg=itemView.findViewById(R.id.favDelete);
        }
    }
}
