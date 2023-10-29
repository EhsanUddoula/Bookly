package com.example.bookly;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

public class myAdapter extends RecyclerView.Adapter<myAdapter.myViewHolder> {
    private ArrayList<NovelModel> dataList;
    private FirebaseFirestore db;

    private ProgressBar progressBar;

    private String uid;


    Context context;

    public myAdapter(Context context,ArrayList<NovelModel> dataList,String uid,ProgressBar progressBar){
        this.context=context;
        this.dataList=dataList;
        this.uid=uid;
        this.progressBar=progressBar;
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
        holder.details.setText(dataList.get(position).getDescription());
        Glide.with(context)
                .load(model.getImage())
                .error(R.drawable.app_icon)// Assuming you have a method to get the image URL from NovelModel
                .into(holder.imageName);

        boolean isExpandable=dataList.get(position).isExpanded();
        holder.expand.setVisibility(isExpandable ? View.VISIBLE :View.GONE);
        holder.addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uid.equals("DidNotLog")){
                    Toast.makeText(context.getApplicationContext(), "Please login first...",Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(holder.addCart.getContext(), loginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    holder.addCart.getContext().startActivity(intent);
                }
                else addToCart(model);
            }
        });

        holder.imageName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(holder.imageName.getContext(), BookDetails.class);
                intent.putExtra("tag",uid);
                intent.putExtra("collect",dataList.get(position).getGenre());
                intent.putExtra("book",dataList.get(position).getBookId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.imageName.getContext().startActivity(intent);
            }
        });
    }

    private void addToCart(NovelModel model) {
        db = FirebaseFirestore.getInstance();
        progressBar.setVisibility(View.VISIBLE);
        HashMap<String,Object>cart =new HashMap<>();

        cart.put("bookPic",model.getImage());
        cart.put("bookName",model.getBook());
        cart.put("writer",model.getWriter());
        cart.put("price",model.getPrice());
        cart.put("bookId",model.getBookId());
        cart.put("amount","1");

        db.collection("Cart").document(uid).collection("currentUser").document(model.getBookId()).set(cart)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context.getApplicationContext(), "Added to Cart...",Toast.LENGTH_SHORT).show();
                        Log.d("tag0", "DocumentSnapshot successfully written!");
                        progressBar.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("tag0", "Error writing document", e);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView bookName,writerName,price,details,show;
        LinearLayout expand;
        ImageView imageName;
        Button addCart;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName=itemView.findViewById(R.id.bookName);
            writerName=itemView.findViewById(R.id.writerName);
            imageName=itemView.findViewById(R.id.imageName);
            price=itemView.findViewById(R.id.price);
            details=itemView.findViewById(R.id.details);
            expand=itemView.findViewById(R.id.expandable);
            addCart=itemView.findViewById(R.id.addToCart);
            show=itemView.findViewById(R.id.copynumbers);

            show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NovelModel model= dataList.get(getAdapterPosition());
                    model.setExpanded(!model.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }
    }
}
