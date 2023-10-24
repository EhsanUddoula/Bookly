
package com.example.bookly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

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
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private String uid;

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
        else uid="";

//        items.add(new Cart_Item(R.drawable.allyourperfects,"All Your Perfects","Colleen Hoover","250","10","1"));
//        items.add(new Cart_Item(R.drawable.thealchemist,"The Alchemist","Paulo Coelho","100","15","2"));
//        items.add(new Cart_Item(R.drawable.thesilentpatient,"The Silent Patient","Alex Michaelides","200","2","1"));
//        items.add(new Cart_Item(R.drawable.variety,"Variety","Colleen Hoover","100","1","1"));
//        items.add(new Cart_Item(R.drawable.allyourperfects,"All Your Perfects","Colleen Hoover","100","5","1"));
//        items.add(new Cart_Item(R.drawable.allyourperfects,"All Your Perfects","Colleen Hoover","100","8","1"));
        recyclerView = findViewById(R.id.rview);
        progressBar = findViewById(R.id.progressBarId);
        dataList  = new ArrayList<>();
        adapter =new CartViewAdapter(this,dataList);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        recyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.VISIBLE);
        db= FirebaseFirestore.getInstance();
        db.collection("Cart").document("x6l3lx3HC2QhZRAwpB1AmTA83Yw2").collection("currentUser").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        progressBar.setVisibility(View.GONE);
                        for(DocumentSnapshot d:list){
                            CartItem obj=d.toObject(CartItem.class);
                            dataList.add(obj);
                        }
                        //update adapter
                        adapter.notifyDataSetChanged();
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
                startActivity(intent);
            }
        });


    }
}
