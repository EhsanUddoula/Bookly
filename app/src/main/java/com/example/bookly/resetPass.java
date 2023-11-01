package com.example.bookly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class resetPass extends AppCompatActivity {
    private Button button;
    private EditText edit;
    private String email="";
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        getSupportActionBar().setTitle("Reset Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ColorDrawable color =new ColorDrawable(Color.parseColor("#FFD700"));
        getSupportActionBar().setBackgroundDrawable(color);
        mAuth=FirebaseAuth.getInstance();
        button=findViewById(R.id.resetButton);
        edit=findViewById(R.id.editTextTextEmailAddress);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=edit.getText().toString().trim();
                if(email.isEmpty())
                {
                    edit.setError("Enter an email address");
                    edit.requestFocus();
                }

                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    edit.setError("Enter a valid email address");
                    edit.requestFocus();
                }
                else reset();
            }
        });

    }
    private void reset(){
        mAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(),"Instructions sent to "+ email,Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Failed to send password...",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}