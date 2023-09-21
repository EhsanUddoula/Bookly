package com.example.bookly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity{
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TextView NameText;
    private FirebaseAuth mAuth;
    private static int logKey=0;

    private ImageView Novel,Poetry,mystery_book,religious;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                // WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        mAuth=FirebaseAuth.getInstance();


        ColorDrawable color =new ColorDrawable(Color.parseColor("#FFD700"));
        getSupportActionBar().setBackgroundDrawable(color);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_open, R.string.nav_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView=findViewById(R.id.nav_view);
        Menu menu=navigationView.getMenu();
        MenuItem logBtn=menu.findItem(R.id.log_in);
        View header=navigationView.getHeaderView(0);
        NameText=header.findViewById(R.id.nameText);
        Novel=findViewById(R.id.novel);
        Poetry=findViewById(R.id.cat2_poetry);
        mystery_book=findViewById(R.id.cat3_mystery);
        religious=findViewById(R.id.cat4_religious);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)logKey=1;
        if(logKey==1) {
            logBtn.setTitle("Sign Out");
            NameText.setText("Ehsan");
        }

        Novel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, NovelBook.class);
                startActivity(intent);
            }
        });

        Poetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, PoetryBook.class);
                startActivity(intent);
            }
        });

        mystery_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Mystery.class);
                startActivity(intent);
            }
        });

        religious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Mystery.class);
                startActivity(intent);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.log_in){
                    if(logKey==0) {
                        Intent intent = new Intent(MainActivity.this, loginActivity.class);
                        startActivity(intent);
                        drawer.closeDrawer(GravityCompat.START);
                    }
                    else{
                        FirebaseAuth.getInstance().signOut();
                        logKey=0;
                        logBtn.setTitle("Sign In");
                        NameText.setText("Your Name");
                    }
                }

                if(item.getItemId()==R.id.nav_settings){
                    Intent intent=new Intent(MainActivity.this,setting.class);
                    startActivity(intent);
                    drawer.closeDrawer(GravityCompat.START);
                }

                if(item.getItemId()==R.id.nav_contact_us){

                    try {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/email");

                        intent.putExtra(intent.EXTRA_EMAIL, "ehsan.siam135@gmail.com");
                        startActivity(Intent.createChooser(intent, "contact us"));
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Exception "+e,Toast.LENGTH_SHORT)
                                .show();                    }
                }

                return true;
            }
        });
    }

}