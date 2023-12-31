package com.example.bookly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class page8_placeOrder_Activity extends AppCompatActivity {
    private EditText name,phone,email,address;
    private TextView total;
    String uid,TotalCost;
    private ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page8_place_order);
        getSupportActionBar().setTitle("Bookly");

        ColorDrawable color =new ColorDrawable(Color.parseColor("#FFD700"));
        getSupportActionBar().setBackgroundDrawable(color);

        Bundle bundle =getIntent().getExtras();
        if(bundle != null){
            uid=bundle.getString("user");
            TotalCost=bundle.getString("total");
        }

        name=findViewById(R.id.namebox);
        phone=findViewById(R.id.Phone);
        email=findViewById(R.id.email);
        address=findViewById(R.id.address);
        backButton=findViewById(R.id.back);
        total=findViewById(R.id.totalprice);

        total.setText(TotalCost);

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("users");

        ref.child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String st1=""+snapshot.child("name").getValue();
                        String st2=" "+snapshot.child("phone").getValue();
                        name.setText(st1);
                        phone.setText(st2);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        //place order
        Button placeOrderButton;
        placeOrderButton=findViewById(R.id.paybutton);
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(veryfication()) {
                    Intent intent = new Intent(page8_placeOrder_Activity.this, page9_pay_Activity.class);
                    intent.putExtra("user",uid);
                    intent.putExtra("total",TotalCost);
                    intent.putExtra("name",name.getText().toString());
                    intent.putExtra("email",email.getText().toString());

                    startActivity(intent);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(page8_placeOrder_Activity.this,page7_Activity.class);
                intent.putExtra("tag",uid);
                startActivity(intent);
            }
        });

    }

    private boolean veryfication(){
        String mail=email.getText().toString().trim();
        String Name=name.getText().toString().trim();
        String Phone=phone.getText().toString().trim();
        String add= address.getText().toString().trim();

        if(mail.isEmpty())
        {
            email.setError("Enter an email address");
            email.requestFocus();
            return false;
        }

        if(Name.isEmpty())
        {
            name.setError("Enter your name");
            name.requestFocus();
            return false;
        }

        if(Phone.isEmpty())
        {
            phone.setError("Enter your name");
            phone.requestFocus();
            return false;
        }

        if(add.isEmpty())
        {
            address.setError("Enter your name");
            address.requestFocus();
            return false;
        }
        return true;
    }
}