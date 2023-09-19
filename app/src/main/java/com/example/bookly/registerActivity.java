package com.example.bookly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

public class registerActivity extends AppCompatActivity {

    private TextView loginText;
    private EditText passVisibility1,passVisibility2;
    private ImageView icon1,icon2;
    private Button register;
    private EditText registerEmail,registerPass,registerPass2,registerName,registerPhone,registerAddress;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    DatabaseReference databaseReference;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ColorDrawable color =new ColorDrawable(Color.parseColor("#FFD700"));
        getSupportActionBar().setBackgroundDrawable(color);

        mAuth=FirebaseAuth.getInstance();

        loginText=findViewById(R.id.goToLogin);
        passVisibility1=findViewById(R.id.passwordBox);
        passVisibility2=findViewById(R.id.passwordBox2);
        register=findViewById(R.id.registerButton);
        icon1=findViewById(R.id.passIcon);
        icon2=findViewById(R.id.passIcon2);
        registerEmail=findViewById(R.id.editTextTextEmailAddress);
        registerPass=findViewById(R.id.passwordBox);
        registerPass2=findViewById(R.id.passwordBox2);
        progressBar=findViewById(R.id.progressBarId);
        registerName=findViewById(R.id.editTextName);
        registerPhone=findViewById(R.id.editTextPhone);
        registerAddress=findViewById(R.id.editTextAddress);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("users");


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
        
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRegister();
            }
        });


    }
    private void userRegister() {
        String email=registerEmail.getText().toString().trim();
        String password=registerPass.getText().toString().trim();
        String ConPassword=registerPass2.getText().toString().trim();
        String phone=registerPhone.getText().toString().trim();
        String address=registerAddress.getText().toString().trim();
        String name=registerName.getText().toString().trim();

        if(email.isEmpty())
        {
            registerEmail.setError("Enter an email address");
            registerEmail.requestFocus();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            registerEmail.setError("Enter a valid email address");
            registerEmail.requestFocus();
            return;
        }

        //checking the validity of the password
        if(password.isEmpty())
        {
            registerPass.setError("Enter a password");
            registerPass.requestFocus();
            return;
        }
        if(ConPassword.isEmpty())
        {
            registerPass2.setError("Enter a password");
            registerPass2.requestFocus();
            return;
        }

        if(!password.equals(ConPassword)){
            registerPass2.setError("Confirm password is not matched with Password");
            registerPass2.requestFocus();
            return;
        }

        if(password.length()<6){
            registerPass.setError("Your Password is too short");
            registerPass.requestFocus();
            return;
        }

        if(phone.isEmpty()){
            registerPhone.setError("Enter a phone number");
            registerPhone.requestFocus();
            return;
        }

        if(name.isEmpty()){
            registerName.setError("Enter your Name");
            registerName.requestFocus();
            return;
        }

        if(address.isEmpty()){
            registerAddress.setError("Enter a valid address");
            registerAddress.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    String key=databaseReference.push().getKey();
                    User user= new User(name,phone,address);
                    databaseReference.child(key).setValue(user);
                    Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                    finish();
                } else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                        registerEmail.setError("Already registered Email");
                        registerEmail.requestFocus();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Not Successful",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}