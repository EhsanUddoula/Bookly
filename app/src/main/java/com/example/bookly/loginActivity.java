package com.example.bookly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class loginActivity extends AppCompatActivity {
    private TextView goToRegister;
    private ImageView passVisibility;
    private TextView passwordView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        goToRegister=findViewById(R.id.goToRegister);
        passVisibility=findViewById(R.id.eyeIcon);
        passwordView=findViewById(R.id.passwordBox);
        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(loginActivity.this,registerActivity.class);
                startActivity(intent);
            }
        });

        passVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passwordView.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    passwordView.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else{
                    passwordView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }
}