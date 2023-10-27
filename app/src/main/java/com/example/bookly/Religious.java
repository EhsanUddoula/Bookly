package com.example.bookly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Religious extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<NovelModel> dataList;
    private FirebaseFirestore db;
    myAdapter adapter;
    private ProgressBar progressBar;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_religious);

        getSupportActionBar().setTitle("Religious");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ColorDrawable color =new ColorDrawable(Color.parseColor("#FFD700"));
        getSupportActionBar().setBackgroundDrawable(color);
        Bundle bundle =getIntent().getExtras();
        if(bundle != null) {
            String identity=bundle.getString("tag");
            if(identity.equals("DidNotLog")){
                uid="DidNotLog";
            }else uid=identity;
        }

        recyclerView=findViewById(R.id.recycleBook);
        progressBar=findViewById(R.id.progbar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataList=new ArrayList<>();
        adapter=new myAdapter(this,dataList,uid,progressBar);
        recyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.VISIBLE);


        db=FirebaseFirestore.getInstance();
        db.collection("Religious").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        progressBar.setVisibility(View.GONE);
                        for(DocumentSnapshot d:list){
                            NovelModel obj=d.toObject(NovelModel.class);
                            dataList.add(obj);
                        }
                        //update adapter
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}