package com.example.bookly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TextView NameText;
    private FirebaseAuth mAuth;
    private static int logKey=0;
    private  String name="hello";
    private RecyclerView recyclerView;
    private ArrayList<popBook>dataList;
    private FirebaseFirestore db;

    popAdapter adapter;

    private ImageView Novel,Poetry,mystery_book,religious,cart,top1,top2,top3,top4,profile,biography,self,children,othres;
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
                            String st6=""+snapshot.child("image").getValue();
                            Glide.with(getApplicationContext())
                                    .load(st6)
                                    .error(R.drawable.profile_empty)// Assuming you have a method to get the image URL from NovelModel
                                    .into(profile);
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
        profile=header.findViewById(R.id.profile_pic);
        Novel=findViewById(R.id.novel);
        Poetry=findViewById(R.id.cat2_poetry);
        mystery_book=findViewById(R.id.cat3_mystery);
        religious=findViewById(R.id.cat4_religious);
        biography=findViewById(R.id.cat6_biography);
        children=findViewById(R.id.cat7_children);
        self=findViewById(R.id.cat5_selfhelp);
        othres=findViewById(R.id.cat8_others);
        cart=findViewById(R.id.appbar_cart);
        top1=findViewById(R.id.Top1);
        top2=findViewById(R.id.Top2);
        top3=findViewById(R.id.Top3);
        top4=findViewById(R.id.Top4);

        popularBookView();
        advertiseView();

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
                            Log.d("main","HELLO");
                            name = ""+snapshot.child("name").getValue();
                            NameText.setText(name);
                            String st6=""+snapshot.child("image").getValue();
                            Log.d("main",st6);
                            Log.d("main",name);
                            Glide.with(getApplicationContext())
                                    .load(st6)
                                    .error(R.drawable.profile_empty)// Assuming you have a method to get the image URL from NovelModel
                                    .into(profile);
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
                FirebaseUser currentUser=mAuth.getCurrentUser();
                if(currentUser != null)
                    intent.putExtra("tag",currentUser.getUid());
                else  intent.putExtra("tag","DidNotLog");
                startActivity(intent);
            }
        });

        Poetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, PoetryBook.class);
                FirebaseUser currentUser=mAuth.getCurrentUser();
                if(currentUser != null)
                    intent.putExtra("tag",currentUser.getUid());
                else  intent.putExtra("tag","DidNotLog");
                startActivity(intent);
            }
        });

        mystery_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Mystery.class);
                FirebaseUser currentUser=mAuth.getCurrentUser();
                if(currentUser != null)
                    intent.putExtra("tag",currentUser.getUid());
                else  intent.putExtra("tag","DidNotLog");
                startActivity(intent);
            }
        });

        religious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Religious.class);
                FirebaseUser currentUser=mAuth.getCurrentUser();
                if(currentUser != null)
                    intent.putExtra("tag",currentUser.getUid());
                else  intent.putExtra("tag","DidNotLog");
                startActivity(intent);
            }
        });

        biography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Biography.class);
                FirebaseUser currentUser=mAuth.getCurrentUser();
                if(currentUser != null)
                    intent.putExtra("tag",currentUser.getUid());
                else  intent.putExtra("tag","DidNotLog");
                startActivity(intent);
            }
        });

        self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, SelfHelp.class);
                FirebaseUser currentUser=mAuth.getCurrentUser();
                if(currentUser != null)
                    intent.putExtra("tag",currentUser.getUid());
                else  intent.putExtra("tag","DidNotLog");
                startActivity(intent);
            }
        });
        othres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Others.class);
                FirebaseUser currentUser=mAuth.getCurrentUser();
                if(currentUser != null)
                    intent.putExtra("tag",currentUser.getUid());
                else  intent.putExtra("tag","DidNotLog");
                startActivity(intent);
            }
        });
        children.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Children.class);
                FirebaseUser currentUser=mAuth.getCurrentUser();
                if(currentUser != null)
                    intent.putExtra("tag",currentUser.getUid());
                else  intent.putExtra("tag","DidNotLog");
                startActivity(intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser currentUser=mAuth.getCurrentUser();
                if(currentUser != null) {
                    Intent intent = new Intent(MainActivity.this,page7_Activity.class);
                    intent.putExtra("tag", currentUser.getUid());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please Log In First...",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, loginActivity.class);
                    startActivity(intent);
                }
            }
        });

        //top 4 categories listener
        top1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, NovelBook.class);
                FirebaseUser currentUser=mAuth.getCurrentUser();
                if(currentUser != null)
                    intent.putExtra("tag",currentUser.getUid());
                else  intent.putExtra("tag","DidNotLog");
                startActivity(intent);
            }
        });
        top2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Children.class);
                FirebaseUser currentUser=mAuth.getCurrentUser();
                if(currentUser != null)
                    intent.putExtra("tag",currentUser.getUid());
                else  intent.putExtra("tag","DidNotLog");
                startActivity(intent);
            }
        });

        top3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, NovelBook.class);
                FirebaseUser currentUser=mAuth.getCurrentUser();
                if(currentUser != null)
                    intent.putExtra("tag",currentUser.getUid());
                else  intent.putExtra("tag","DidNotLog");
                startActivity(intent);
            }
        });

        top4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Religious.class);
                FirebaseUser currentUser=mAuth.getCurrentUser();
                if(currentUser != null)
                    intent.putExtra("tag",currentUser.getUid());
                else  intent.putExtra("tag","DidNotLog");
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
                        Glide.with(getApplicationContext())
                                .load(R.drawable.profile_empty)
                                .error(R.drawable.profile_empty)// Assuming you have a method to get the image URL from NovelModel
                                .into(profile);
                    }
                }

                if(item.getItemId()==R.id.nav_settings){
                    FirebaseUser currentUser=mAuth.getCurrentUser();
                    if(currentUser != null) {
                        Intent intent = new Intent(MainActivity.this,setting.class);
                        intent.putExtra("tag", currentUser.getUid());
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Please Log In First...",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, loginActivity.class);
                        startActivity(intent);
                    }
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
                if(item.getItemId()==R.id.nav_my_wishlist){
                    Intent intent=new Intent(MainActivity.this, FavouriteBook.class);
                    FirebaseUser currentUser=mAuth.getCurrentUser();
                    if(currentUser != null)
                        intent.putExtra("tag",currentUser.getUid());
                    else  intent.putExtra("tag","DidNotLog");
                    startActivity(intent);
                    drawer.closeDrawer(GravityCompat.START);
                }
                if(item.getItemId()==R.id.nav_my_cart){
                    FirebaseUser currentUser=mAuth.getCurrentUser();
                    if(currentUser != null) {
                        Intent intent = new Intent(MainActivity.this,page7_Activity.class);
                        intent.putExtra("tag", currentUser.getUid());
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Please Log In First...",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, loginActivity.class);
                        startActivity(intent);
                    }
                    drawer.closeDrawer(GravityCompat.START);
                }
                if(item.getItemId()==R.id.nav_register){
                    Intent intent=new Intent(MainActivity.this, registerActivity.class);
                    FirebaseUser currentUser=mAuth.getCurrentUser();
                    if(currentUser != null)
                        intent.putExtra("tag",currentUser.getUid());
                    else  intent.putExtra("tag","DidNotLog");
                    startActivity(intent);
                    drawer.closeDrawer(GravityCompat.START);
                }



                return true;
            }
        });
    }

    private void advertiseView() {
        ArrayList<SlideModel> imageList=new ArrayList<>();

        imageList.add(new SlideModel("https://bit.ly/2YoJ77H", "The animal population decreased by 58 percent in 42 years.",ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://bit.ly/2BteuF2", "Elephants and tigers may become extinct.",ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://bit.ly/3fLJf72", "And people do that.",ScaleTypes.CENTER_CROP));
        ImageSlider imageSlider=findViewById(R.id.image_slider);
        imageSlider.setImageList(imageList);
        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void doubleClick(int i) {

            }

            @Override
            public void onItemSelected(int position) {
                // You can listen here.
            }
        });
    }

    private void popularBookView() {
        recyclerView=findViewById(R.id.mainReview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        dataList=new ArrayList<>();
        String uid;
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if(currentUser != null)
          uid=currentUser.getUid();
        else  uid="DidNotLog";
        adapter=new popAdapter(this,dataList,uid);
        recyclerView.setAdapter(adapter);
        db=FirebaseFirestore.getInstance();
        db.collection("popular").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list){
                            popBook obj=d.toObject(popBook.class);
                            Log.d("tag10", "DocumentSnapshot data: " + d.getData());
                            dataList.add(obj);
                        }
                        //update adapter
                        adapter.notifyDataSetChanged();
                    }
                });
    }

}