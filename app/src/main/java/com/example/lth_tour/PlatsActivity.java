package com.example.lth_tour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
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
    private Button nextPlace;



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
        nextPlace = (Button) findViewById(R.id.next_place);
        nextPlace.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
        textbox_rubrik.setText(plats.bodyFront);
        textbox.setText(plats.bodyMore);
        Integer images[] ={R.drawable.mhuset, R.drawable.ehuset, R.drawable.vhuset, R.drawable.ahuset, R.drawable.ikdc, R.drawable.kcentrum, R.drawable.karhuset, R.drawable.mattehuset};
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
        if(plats.title.equals("M-huset")){
            imageSwitcher.setImageResource(images[0]);
        }
        else if(plats.title.equals("E-huset")){
            imageSwitcher.setImageResource(images[1]);
            textbox.setText("E-huset vid LTH är ett gemensamt hus för forskning och undervisning " +
                    "vid institutionerna för Biomedicinsk teknik, Datavetenskap, Elektro- och informationsteknik " +
                    "och Fastighetsvetenskap. \nI huset har E-sektionen, D-sektionen samt Lantmätarna sina lokaler.\n" +
                    "Tillsammans driver de LED-caféet på entréplan, i anslutning till foajén.");

        }
        else if(plats.title.equals("V-huset")){
            imageSwitcher.setImageResource(images[2]);
        }
        else if(plats.title.equals("A-huset")){
            imageSwitcher.setImageResource(images[3]);
        }
        else if(plats.title.equals("IKDC")){
            imageSwitcher.setImageResource(images[4]);
        }
        else if(plats.title.equals("Kemicentrum")){
            imageSwitcher.setImageResource(images[5]);
        }
        else if(plats.title.equals("Kårhuset")){
            imageSwitcher.setImageResource(images[6]);
        }
        else if(plats.title.equals("Mattehuset")){
            imageSwitcher.setImageResource(images[7]);
        }

        else{
            imageSwitcher.setImageResource(images[1]);
        }
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void openGpsActivity(){
        Intent intent = new Intent(this, GpsActivity.class);
        startActivity(intent);
    }
}
