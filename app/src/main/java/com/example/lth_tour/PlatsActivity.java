package com.example.lth_tour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.io.Serializable;

public class PlatsActivity extends AppCompatActivity implements Serializable {

    private ImageButton homeButton;
    private PlatsObjekt plats = new PlatsObjekt(0,0,"0", "0", "0", "0");
    ImageSwitcher imageSwitcher;
    TextView rubrik;
    TextView underRubrik;
    TextView textbox_rubrik;
    TextView textbox;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        plats = (PlatsObjekt) i.getSerializableExtra("plats");
        setContentView(R.layout.activity_plats);
        rubrik = (TextView) findViewById(R.id.bild_rubrik);
        underRubrik = (TextView) findViewById(R.id.bild_underrubrik);
        textbox_rubrik = (TextView) findViewById(R.id.textbox_rubrik);
        textbox = (TextView) findViewById(R.id.textbox);

        homeButton = (ImageButton) findViewById(R.id.homeButton);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        };

    @Override
    public void onStart() {
        super.onStart();
        rubrik.setText(plats.title);
        underRubrik.setText("Du är vid " + plats.title);
        textbox_rubrik.setText(plats.title);
        textbox.setText(plats.bodyFront);
        Integer images[] ={R.drawable.ehuset, R.drawable.kcentrum, R.drawable.mhuset};
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
        if(plats.title == "E-huset"){
            imageSwitcher.setImageResource(images[1]);
        }
        else if(plats.title == "Kemi-huset"){
            imageSwitcher.setImageResource(images[0]);
        }
        else if(plats.title == "Maskin-huset"){
            imageSwitcher.setImageResource(images[2]);
        }
        else{
            imageSwitcher.setImageResource(images[0]);
        }
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
