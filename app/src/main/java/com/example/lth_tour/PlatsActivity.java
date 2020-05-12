package com.example.lth_tour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class PlatsActivity extends AppCompatActivity {

    private ImageButton homeButton;
    ImageSwitcher imageSwitcher;
    public double latitude;
    public double longitude;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plats);
        homeButton = (ImageButton) findViewById(R.id.homeButton);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        Integer images[] ={R.drawable.ehuset, R.drawable.kcentrum};
        imageSwitcher=(ImageSwitcher) findViewById(R.id.imageView2);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setLayoutParams(
                        new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
                );
                return imageView;
            }



            });
            if(Utils.calculateDistanceTo(latitude,longitude,55.6971392, 13.1967143)[0] < 15){
                imageSwitcher.setImageResource(images[0]);

        }else{
                imageSwitcher.setImageResource(images[1]);
            }
        };



    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
