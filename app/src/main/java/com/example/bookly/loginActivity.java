package com.example.bookly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class loginActivity extends AppCompatActivity {
    private TextView goToRegister,forgotPass;
    private ImageView passVisibility;
    private TextView passwordView;
    private EditText logEmail,passwordEmail;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private Button logButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Log In");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ColorDrawable color =new ColorDrawable(Color.parseColor("#FFD700"));
        getSupportActionBar().setBackgroundDrawable(color);

        mAuth=FirebaseAuth.getInstance();

        goToRegister=findViewById(R.id.goToRegister);
        passVisibility=findViewById(R.id.eyeIcon);
        passwordView=findViewById(R.id.passwordBox);
        forgotPass=findViewById(R.id.forgotPass);
        logEmail=findViewById(R.id.loginEmail);
        passwordEmail=findViewById(R.id.passwordBox);
        progressBar=findViewById(R.id.progressBarId);
        logButton=findViewById(R.id.loginButton);
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

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(loginActivity.this, resetPass.class);
                startActivity(intent);
            }
        });

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }

    private void userLogin() {
        String email=logEmail.getText().toString().trim();
        String password=passwordEmail.getText().toString().trim();

        if(email.isEmpty())
        {
            logEmail.setError("Enter an email address");
            logEmail.requestFocus();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            logEmail.setError("Enter a valid email address");
            logEmail.requestFocus();
            return;
        }

        //checking the validity of the password
        if(password.isEmpty())
        {
            passwordEmail.setError("Enter a password");
            passwordEmail.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("tag",1);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else{
                    passwordEmail.setError("Invalid Email or Password");
                    passwordEmail.requestFocus();
                }
            }
        });
    }
}