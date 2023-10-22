
package com.example.bookly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

class Cart_Item {
    int bookpic;
    String bookname;
    String writer;
    String price;
    String copies;
    String amount;

    public Cart_Item(int bookpic, String bookname, String writer, String price, String copies, String amount) {
        this.bookpic = bookpic;
        this.bookname = bookname;
        this.writer = writer;
        this.price = price;
        this.copies = copies;
        this.amount = amount;
    }

    public int getBookpic() {
        return bookpic;
    }

    public void setBookpic(int bookpic) {
        this.bookpic = bookpic;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCopies() {
        return copies;
    }

    public void setCopies(String copies) {
        this.copies = copies;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}

class Cart_ViewHolder extends RecyclerView.ViewHolder {
    ImageView bookpicView;
    TextView booknameView,writerView,priceView,copiesView,amountView;

    public Cart_ViewHolder(@NonNull View itemView){
        super(itemView);
        bookpicView=itemView.findViewById(R.id.bookpic);
        booknameView=itemView.findViewById(R.id.bookname);
        writerView=itemView.findViewById(R.id.writer);
        priceView=itemView.findViewById(R.id.price);
        copiesView=itemView.findViewById(R.id.copies);
        amountView=itemView.findViewById(R.id.amount);
    }

}


class Cart_viewAdapter extends RecyclerView.Adapter<Cart_ViewHolder> {

    Context context;
    List<Cart_Item> items;

    public Cart_viewAdapter(Context context, List<Cart_Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public Cart_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new Cart_ViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_item,parent,false));
    }

    @Override
    public  void onBindViewHolder(@NonNull Cart_ViewHolder holder, int position){
        holder.bookpicView.setImageResource(items.get(position).getBookpic());
        holder.booknameView.setText(items.get(position).getBookname());
        holder.writerView.setText(items.get(position).getWriter());
        holder.priceView.setText(items.get(position).getPrice());
        holder.copiesView.setText(items.get(position).getCopies());
        holder.amountView.setText(items.get(position).getAmount());
    }

    @Override
    public int getItemCount(){
        return items.size();
    }

}


public class page7_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page7);

        getSupportActionBar().setTitle("Bookly");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ColorDrawable color =new ColorDrawable(Color.parseColor("#FFD700"));
        getSupportActionBar().setBackgroundDrawable(color);

        RecyclerView rv = findViewById(R.id.rview);
        List<Cart_Item> items  = new ArrayList<Cart_Item>();
        items.add(new Cart_Item(R.drawable.allyourperfects,"All Your Perfects","Colleen Hoover","250","10","1"));
        items.add(new Cart_Item(R.drawable.thealchemist,"The Alchemist","Paulo Coelho","100","15","2"));
        items.add(new Cart_Item(R.drawable.thesilentpatient,"The Silent Patient","Alex Michaelides","200","2","1"));
        items.add(new Cart_Item(R.drawable.variety,"Variety","Colleen Hoover","100","1","1"));
        items.add(new Cart_Item(R.drawable.allyourperfects,"All Your Perfects","Colleen Hoover","100","5","1"));
        items.add(new Cart_Item(R.drawable.allyourperfects,"All Your Perfects","Colleen Hoover","100","8","1"));


        rv.setLayoutManager((new LinearLayoutManager(this)));
        rv.setAdapter(new Cart_viewAdapter(getApplicationContext(),items));

        //back button
        ImageView backbutton = findViewById(R.id.back);
        backbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(page7_Activity.this, MainActivity.class);//MainActivity
                startActivity(intent);
            }
        });

        //place order
        Button placeOrderButton;
        placeOrderButton=findViewById(R.id.placeOrder);
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent= new Intent(page7_Activity.this, page8_placeOrder_Activity.class);
                startActivity(intent);
            }
        });


    }
}
