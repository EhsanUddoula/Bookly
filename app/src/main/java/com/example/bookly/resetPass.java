package com.example.bookly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class resetPass extends AppCompatActivity implements View.OnClickListener {

    private Button resetButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        resetButton=findViewById(R.id.resetButton);
        resetButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.resetButton){
            Toast.makeText(getApplicationContext(),"Button is clicked",Toast.LENGTH_SHORT).show();
        }
    }
}