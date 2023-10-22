package com.example.bookly;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

public class rate_activity extends Dialog {

    private float userrate=0;

    public rate_activity(@NonNull Context context){
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rateus);
        final AppCompatButton ratenowbutton=findViewById(R.id.ratenowbut);
        final AppCompatButton ratelaterbutton=findViewById(R.id.ratelate);
        final RatingBar ratingBar=findViewById(R.id.ratingbar);
        final ImageView rateimg=findViewById(R.id.ratingimage);

        ratenowbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code here
            }
        });

        ratelaterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                if(rating<=1){
                    rateimg.setImageResource(R.drawable.one);
                }
                else if(rating <=2){
                    rateimg.setImageResource(R.drawable.two);
                }
                else if (rating <=3) {
                    rateimg.setImageResource(R.drawable.three);
                }
                else if (rating <=4) {
                    rateimg.setImageResource(R.drawable.four);
                }
                else if(rating <=5){
                    rateimg.setImageResource(R.drawable.five);

                }
                animateImage(rateimg);

                userrate=rating;

            }
        });
    }
    protected void animateImage(ImageView ratingImage){
        ScaleAnimation scaleAnimation=new ScaleAnimation(0,1f,0,1f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(200);
        ratingImage.startAnimation(scaleAnimation);
    }
}
