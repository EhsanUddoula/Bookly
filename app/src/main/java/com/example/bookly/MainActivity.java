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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity{
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TextView NameText;
    private FirebaseAuth mAuth;
    private static int logKey=0;
    private  String name="hello";

    private ImageView Novel,Poetry,mystery_book,religious,cart;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        Menu menu=navigationView.getMenu();
        MenuItem logBtn=menu.findItem(R.id.log_in);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            logKey=1;
            String uid= currentUser.getUid();

            DatabaseReference ref= FirebaseDatabase.getInstance().getReference("users");

            ref.child(uid)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            name = ""+snapshot.child("name").getValue();
                            NameText.setText(name);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
            logBtn.setTitle("Sign Out");
        }

    }
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
        cart=findViewById(R.id.appbar_cart);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            logKey=1;
            FirebaseUser firebaseUser=mAuth.getCurrentUser();
            String uid= firebaseUser.getUid();

            DatabaseReference ref= FirebaseDatabase.getInstance().getReference("users");

            ref.child(uid)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            name = ""+snapshot.child("name").getValue();
                            NameText.setText(name);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }

        if(logKey==1) {
            logBtn.setTitle("Sign Out");

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
                Intent intent=new Intent(MainActivity.this, Religious.class);
                startActivity(intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,page7_Activity.class);
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

                if(item.getItemId()==R.id.nav_rate_us){
                    Intent intent =new Intent(MainActivity.this,rateus.class);
                    startActivity(intent);
                    drawer.closeDrawer(GravityCompat.START);
                }

                if(item.getItemId()==R.id.nav_about){
                    Intent intent =new Intent(MainActivity.this,about_us.class);
                    startActivity(intent);
                    drawer.closeDrawer(GravityCompat.START);
                }


                return true;
            }
        });
    }

}