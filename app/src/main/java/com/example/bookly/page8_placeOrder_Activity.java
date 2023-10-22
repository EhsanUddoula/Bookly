package com.example.bookly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class page8_placeOrder_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page8_place_order);

        //back button
        ImageView backbutton = findViewById(R.id.back);
        backbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(page8_placeOrder_Activity.this, page7_Activity.class);
                startActivity(intent);
            }
        });

        //place order
        Button placeOrderButton;
        placeOrderButton=findViewById(R.id.paybutton);
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent= new Intent(page8_placeOrder_Activity.this, page9_pay_Activity.class);
                startActivity(intent);
            }
        });

    }
}