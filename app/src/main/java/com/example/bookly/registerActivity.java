package com.example.bookly;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class registerActivity extends AppCompatActivity {

    private TextView loginText;
    private EditText passVisibility1,passVisibility2;
    private ImageView icon1,icon2;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loginText=findViewById(R.id.goToLogin);
        passVisibility1=findViewById(R.id.passwordBox);
        passVisibility2=findViewById(R.id.passwordBox2);
        icon1=findViewById(R.id.passIcon);
        icon2=findViewById(R.id.passIcon2);
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(registerActivity.this,loginActivity.class);
                startActivity(intent);
            }
        });
        icon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passVisibility1.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    passVisibility1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else{
                    passVisibility1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        icon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passVisibility2.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    passVisibility2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else{
                    passVisibility2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }
}