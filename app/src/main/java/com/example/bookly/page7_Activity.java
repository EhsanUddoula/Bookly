
package com.example.bookly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class page7_Activity extends AppCompatActivity {
    private ArrayList<CartItem>dataList;
    CartViewAdapter adapter;
    public ProgressBar progressBar;
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private String uid;
    public TextView total;
    private double TotalCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page7);

        getSupportActionBar().setTitle("Bookly");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ColorDrawable color =new ColorDrawable(Color.parseColor("#FFD700"));
        getSupportActionBar().setBackgroundDrawable(color);

        Bundle bundle =getIntent().getExtras();
        if(bundle != null) uid=bundle.getString("tag");

        recyclerView = findViewById(R.id.recycleView);
        progressBar = findViewById(R.id.progressBarId);
        total=findViewById(R.id.totalprice);
        dataList  = new ArrayList<>();
        adapter =new CartViewAdapter(this,dataList,uid,total,progressBar,TotalCost);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        recyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.VISIBLE);
        db= FirebaseFirestore.getInstance();
        db.collection("Cart").document(uid).collection("currentUser").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        progressBar.setVisibility(View.GONE);
                        for(DocumentSnapshot d:list){
                            Log.d("tag", "DocumentSnapshot data: " + d.getData());
                            CartItem obj=d.toObject(CartItem.class);
                            dataList.add(obj);
                            Log.d("tag2", "Object added... " + Double.parseDouble(obj.getPrice().replaceAll("[^\\d.]","")));
                        }
                        //update adapter
                        adapter.notifyDataSetChanged();
                        Log.d("tag3", "Adapter Added...");
                        calculateTotal(dataList);
                    }
                });

        //back button
        ImageView backbutton = findViewById(R.id.back);
        backbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(page7_Activity.this, MainActivity.class);//MainActivity
                startActivity(intent);
            }
        });

        //place order
        Button placeOrderButton;
        placeOrderButton=findViewById(R.id.placeOrder);
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent= new Intent(page7_Activity.this, page8_placeOrder_Activity.class);
                intent.putExtra("user",uid);
                calculateTotal(dataList);
                intent.putExtra("total",TotalCost+"Tk");
                startActivity(intent);
            }
        });


    }

    private void calculateTotal(ArrayList<CartItem> dataList) {
        TotalCost=0.0;
        double cost;
        Log.d("price","inside updateTotal..");
        for(CartItem item: dataList){
            cost = Double.parseDouble(item.getPrice().replaceAll("[^\\d.]",""))*Integer.parseInt(item.getAmount());
            TotalCost += cost;
        }
        Log.d("price1","after data read..."+ TotalCost);
        total.setText(TotalCost+ " Tk");
    }

}
