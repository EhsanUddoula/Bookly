package com.example.bookly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BookDetails extends AppCompatActivity {

    private ImageView bookImage;
    private TextView bookName,writer,price,genre,language,description,author;
    private String uid,collect,Id;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        getSupportActionBar().setTitle("Book Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ColorDrawable color =new ColorDrawable(Color.parseColor("#FFD700"));
        getSupportActionBar().setBackgroundDrawable(color);

        Bundle bundle =getIntent().getExtras();
        if(bundle != null) {
            String identity=bundle.getString("tag");
            if(identity.equals("DidNotLog")){
                uid="DidNotLog";
            }else uid=identity;

            collect=bundle.getString("collect");
            Id=bundle.getString("book");
        }
        Log.d("tagDet",uid);
        Log.d("tagDet1",collect);
        Log.d("tagDet2",Id);

        bookImage=findViewById(R.id.bookImage);
        bookName=findViewById(R.id.bookText);
        writer=findViewById(R.id.writerText);
        price=findViewById(R.id.priceText);
        genre=findViewById(R.id.genre);
        language=findViewById(R.id.language);
        description=findViewById(R.id.description);
        author=findViewById(R.id.author);
        ArrayList<NovelModel>arrayList=new ArrayList<>();

        db=FirebaseFirestore.getInstance();
        db.collection(collect)
                .whereEqualTo("bookId",Id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list){
                            NovelModel obj=d.toObject(NovelModel.class);
                            Log.d("tagDet", "DocumentSnapshot data: " + d.getData());
                            arrayList.add(obj);
                        }
                        //update adapter
                        for( NovelModel item: arrayList){
                            Log.d("tagDet3", "DocumentSnapshot data: " + item.getGenre());
                            Glide.with(getApplicationContext())
                                .load(item.getImage())
                               .error(R.drawable.app_icon)// Assuming you have a method to get the image URL from NovelModel
                                .into(bookImage);
                            genre.setText(item.getGenre());
                            language.setText(item.getLanguage());
                            description.setText(item.getDescription());
                            author.setText(item.getAuthor());
                            bookName.setText(item.getBook());
                            writer.setText(item.getWriter());
                            price.setText(item.getPrice());

                        }
                    }
                });

    }
}