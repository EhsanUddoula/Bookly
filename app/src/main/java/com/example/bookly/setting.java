package com.example.bookly;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class setting extends AppCompatActivity {
    private EditText name,phone,address,workplace,alt;
    private ImageView image;
    private TextView profileName;
    private ProgressBar progressBar;
    String uid,st1,st2,st3,st4,st5,st6;
    ActivityResultLauncher<String> TakePhoto;
    FirebaseStorage storage;
    Uri imageUri;
    private Button button;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getSupportActionBar().setTitle("Setting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ColorDrawable color =new ColorDrawable(Color.parseColor("#FFD700"));
        getSupportActionBar().setBackgroundDrawable(color);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            uid=bundle.getString("tag");
        }

        progressBar=findViewById(R.id.progressBar);
        image=findViewById(R.id.logo);
        name=findViewById(R.id.namebox);
        phone=findViewById(R.id.editTextPhone);
        address=findViewById(R.id.namebox2);
        workplace=findViewById(R.id.editTextTextEmailAddress);
        alt=findViewById(R.id.Passwordbox);
        profileName=findViewById(R.id.profile_name);
        button=findViewById(R.id.button);
        storage=FirebaseStorage.getInstance();

        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("users");

        ref.child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String st1=""+snapshot.child("name").getValue();
                        if(st1!=null){
                            name.setText(st1);
                            profileName.setText(st1);
                        }
                        st2=""+snapshot.child("phone").getValue();
                        if(st2!=null)phone.setText(st2);
                        st3=""+snapshot.child("address").getValue();
                        if(st3!=null)address.setText(st3);
                         st4=""+snapshot.child("alt").getValue();
                        if(st4!=null)alt.setText(st4);
                        st5=""+snapshot.child("workplace").getValue();
                        if(st5!=null)workplace.setText(st5);
                        st6=""+snapshot.child("image").getValue();
                        Glide.with(getApplicationContext())
                                .load(st6)
                                .error(R.drawable.profile_empty)// Assuming you have a method to get the image URL from NovelModel
                                .into(image);

                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        TakePhoto=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result !=null) {
                    image.setImageURI(result);
                    imageUri=result;
                }
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TakePhoto.launch("image/*");
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                if(imageUri!=null)
                upload();
                else update();
            }
        });


    }
    Map<String,Object> map=new HashMap<>();
    private void upload() {
        StorageReference reference= storage.getReference().child(uid);

        Log.d("log","problem ki?");
        reference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                       Log.d("Firebase","image Uploaded");
                       Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                       while(!uriTask.isSuccessful());
                       st6=""+uriTask.getResult();
                        Log.d("Firebase",st6);
                        update();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Firebase","image Upload failed");
                    }
                });
    }

    private void update(){
        st1=name.getText().toString().trim();
        st2=phone.getText().toString().trim();
        st3=address.getText().toString().trim();
        st4=alt.getText().toString().trim();
        st5=workplace.getText().toString().trim();
        map.put("image",""+st6);
        map.put("name",st1);
        map.put("phone",st2);
        map.put("address",st3);
        map.put("alt",st4);
        map.put("workplace",st5);
        Log.d("Firebase1",st6);
        databaseReference= FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(uid).updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}