package com.example.lth_tour;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.os.Vibrator;
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
import java.util.ArrayList;

public class PlatsActivity extends AppCompatActivity implements Serializable {

    //public static String DIALOG_STRING = "@string/info_main";
    private ImageButton homeButton;
    private PlatsObjekt plats = new PlatsObjekt(0,0,"0", "0", "0", "0");
    ImageSwitcher imageSwitcher;
    //TextView rubrik;
    TextView underRubrik;
    TextView textbox_rubrik;
    TextView textbox;
    private ImageView nextPlace;
    private String platsTitle;
    private TextView infoText;
    private ImageView more_info;
    private ImageView closePopUp;
    private Dialog infoDialog;
    private Vibrator vibrator;
    private long[] vibratorPattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        plats = (PlatsObjekt) i.getSerializableExtra("plats");
        setContentView(R.layout.activity_plats);
        underRubrik = (TextView) findViewById(R.id.bild_underrubrik);
        textbox_rubrik = (TextView) findViewById(R.id.textbox_rubrik);
        textbox = (TextView) findViewById(R.id.textbox);
        nextPlace = (ImageView) findViewById(R.id.back);
        nextPlace.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        infoText = (TextView) findViewById(R.id.infoText);
        //infoText.setText("@string/info_main");
        homeButton = (ImageButton) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
        more_info = (ImageView) findViewById(R.id.imageButton_readmore);
        more_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPopup();
            }
        });
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibratorPattern = new long[]{0, 50}; //sleep 0 ms, vibrate 50 ms

        //String platsTitle = i.getStringExtra(MainActivity.PLATS_TITLE);
        //changePlace(plats.title);

        /*
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
        */

     /*   if(platsTitle.equals("M-huset")){
            infoText.setText("@string/info_Mhuset");
            imageSwitcher.setImageResource(images[0]);
            textbox.setText("I M-huset, LTH, finns forskning och undervisning vid institutionerna för: Energivetenskaper," +
                    "Industriell elektroteknik och automation, Maskinteknologi, Reglerteknik & Teknisk ekonomi och logistik.\n" +
                    "Välkomna ner i källaren i M-huset där vi i caféet serverar kaffe, mackor och annat lätt att äta.");
        }
        else if(platsTitle.equals("E-huset")){
            infoText.setText( "@string/info_Ehuset");
            imageSwitcher.setImageResource(images[1]);
            textbox.setText("E-huset vid LTH är ett gemensamt hus för forskning och undervisning " +
                    "vid institutionerna för Biomedicinsk teknik, Datavetenskap, Elektro- och informationsteknik " +
                    "och Fastighetsvetenskap. \nI huset har E-sektionen, D-sektionen samt Lantmätarna sina lokaler.\n" +
                    "Tillsammans driver de LED-caféet på entréplan, i anslutning till foajén.");

        }
        else if(platsTitle.equals("V-huset")){
            infoText.setText( "@string/info_Vhuset");
            imageSwitcher.setImageResource(images[2]);
            textbox.setText("I V-huset finns verksamheter som har anknytning till projektering,"+
                    " byggande och förvaltning av byggnader och annan infrastruktur."+
                    " De utbildningar som främst har sin anknytning till V-huset är: " +
                    "Civilingenjörsutbildningen i Väg och vatten, Lantmäteri, Riskhantering och Brandingenjörsutbildningen."
            );
        }
        else if(platsTitle.equals("A-huset")){
            infoText.setText( "@string/info_Ahuset");
            imageSwitcher.setImageResource(images[3]);
            textbox.setText("Här utbildar  och vidareutbildar vi framtidens samhällsbyggare – samtidigt"+
                    " som vi bygger upp ny kunskap inom de tre forskarutbildningsämnena arkitektur, byggande"+
                    " och arkitektur och miljöpsykologi. Utbildning och forskning bedrivs av avdelningarna"+
                    " för arkitektur, boende och bostadsutveckling samt energi och byggnadsdesign.");
        }
        else if(platsTitle.equals("IKDC")){
            infoText.setText("@string/info_IKDC");
            imageSwitcher.setImageResource(images[4]);
            textbox.setText("IKDC rymmer Institutionen för designvetenskaper, Industridesignskolan"+
                    " och flera forskningslaboratorier såsom Virtual Reality-, Usability-, Rapid Prototyping-"+
                    ", aerosol-, klimat- och mekaniklaboratorier." +
                    "IKDC är resultatet av Stichting Ikea Foundations satsning för att stötta utbildning i industridesign vid Lunds Tekniska Högskola.");
        }
        else if(platsTitle.equals("Kemicentrum")){
            infoText.setText("@string/info_Kemicentrum");
            imageSwitcher.setImageResource(images[5]);
            textbox.setText("På Kemicentrum bedrivs forskning och utbildning inom kemi i världsklass."+
                    " Här arbetar forskare från olika forskningsområden och inriktningar inom kemin"+
                    "Kemicentrums läge i det norra universitetsområdet i Lund.bidrar till en kreativ studie-och forskningmiljö." );
        }
        else if(platsTitle.equals("Kårhuset")){
            infoText.setText("@string/info_Karhuset");
            imageSwitcher.setImageResource(images[6]);
            textbox.setText("Teknologkåren ville ha ett eget kårhus som teknologerna på KTH och Chalmers hade, men i många år fick de nöja sig med en källare i V-huset." +
                    "\n" +
                    "1986 fick teknologerna nog och bestämde sig för att ta saken i egna händer.");
        }
        else if(platsTitle.equals("Mattehuset")){
            infoText.setText("@string/info_Mattehuset");
            imageSwitcher.setImageResource(images[7]);
            textbox.setText("Matematikcentrum består av tre avdelningar, Matematik LTH och numerisk analys, Matematik NF och Matematisk statistik" +
                    "I Mattehuset finns även Hilbert Café som f-sektionen driver, och här kan du bland annat köpa gott kaffe, baguetter och sallader.");
        }
        else{
            infoText.setText( "@string/info_main");
            imageSwitcher.setImageResource(images[1]);
            textbox.setText("FEL");
        }*/
        }

    @Override
    public void onStart() {
        super.onStart();
        infoDialog = new Dialog(this);
        underRubrik.setText("Du är vid " + plats.title);
        textbox_rubrik.setText(plats.bodyFront);
        textbox.setText(plats.bodyMore);
        changePlace(plats.title);
    }

    public void changePlace(String platsTitle){
       // plats.title = platsTitle;
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

        if(platsTitle.equals("M-huset")){
            imageSwitcher.setImageResource(images[0]);
            textbox.setText("I M-huset, LTH, finns forskning och undervisning vid institutionerna för: Energivetenskaper," +
                    "Industriell elektroteknik och automation, Maskinteknologi, Reglerteknik & Teknisk ekonomi och logistik.\n" +
                    "Välkomna ner i källaren i M-huset där vi i caféet serverar kaffe, mackor och annat lätt att äta.");
            //infoText.setText("@string/info_Mhuset");
        }
        else if(platsTitle.equals("E-huset")){
            imageSwitcher.setImageResource(images[1]);
            textbox.setText("E-huset vid LTH är ett gemensamt hus för forskning och undervisning " +
                    "vid institutionerna för Biomedicinsk teknik, Datavetenskap, Elektro- och informationsteknik " +
                    "och Fastighetsvetenskap. \nI huset har E-sektionen, D-sektionen samt Lantmätarna sina lokaler.\n" +
                    "Tillsammans driver de LED-caféet på entréplan, i anslutning till foajén.");
            //infoText.setText( "@string/info_Ehuset");
        }
        else if(platsTitle.equals("V-huset")){
            imageSwitcher.setImageResource(images[2]);
            textbox.setText("I V-huset finns verksamheter som har anknytning till projektering,"+
                    " byggande och förvaltning av byggnader och annan infrastruktur."+
                    " De utbildningar som främst har sin anknytning till V-huset är: " +
                    "Civilingenjörsutbildningen i Väg och vatten, Lantmäteri, Riskhantering och Brandingenjörsutbildningen.");
            //infoText.setText( "@string/info_Vhuset");
        }
        else if(platsTitle.equals("A-huset")){
            imageSwitcher.setImageResource(images[3]);
            textbox.setText("Här utbildar  och vidareutbildar vi framtidens samhällsbyggare – samtidigt"+
                    " som vi bygger upp ny kunskap inom de tre forskarutbildningsämnena arkitektur, byggande"+
                    " och arkitektur och miljöpsykologi. Utbildning och forskning bedrivs av avdelningarna"+
                    " för arkitektur, boende och bostadsutveckling samt energi och byggnadsdesign.");
            //infoText.setText( "@string/info_Ahuset");
        }
        else if(platsTitle.equals("IKDC")){
            imageSwitcher.setImageResource(images[4]);
            textbox.setText("IKDC rymmer Institutionen för designvetenskaper, Industridesignskolan"+
                    " och flera forskningslaboratorier såsom Virtual Reality-, Usability-, Rapid Prototyping-"+
                    ", aerosol-, klimat- och mekaniklaboratorier." +
                    "IKDC är resultatet av Stichting Ikea Foundations satsning för att stötta utbildning i industridesign vid Lunds Tekniska Högskola.");
            //infoText.setText("@string/info_IKDC");
        }
        else if(platsTitle.equals("Kemicentrum")){
            imageSwitcher.setImageResource(images[5]);
            textbox.setText("På Kemicentrum bedrivs forskning och utbildning inom kemi i världsklass."+
                    " Här arbetar forskare från olika forskningsområden och inriktningar inom kemin"+
                    "Kemicentrums läge i det norra universitetsområdet i Lund.bidrar till en kreativ studie-och forskningmiljö." );
            //infoText.setText("@string/info_Kemicentrum");
        }
        else if(platsTitle.equals("Kårhuset")){
            imageSwitcher.setImageResource(images[6]);
            textbox.setText("Teknologkåren ville ha ett eget kårhus som teknologerna på KTH och Chalmers hade, men i många år fick de nöja sig med en källare i V-huset." +
                    "\n" +
                    "1986 fick teknologerna nog och bestämde sig för att ta saken i egna händer.");
            //infoText.setText("@string/info_Karhuset");
        }
        else if(platsTitle.equals("Mattehuset")){
            imageSwitcher.setImageResource(images[7]);
            textbox.setText("Matematikcentrum består av tre avdelningar, Matematik LTH och numerisk analys, Matematik NF och Matematisk statistik" +
                    "I Mattehuset finns även Hilbert Café som f-sektionen driver, och här kan du bland annat köpa gott kaffe, baguetter och sallader.");
            //infoText.setText("@string/info_Mattehuset");
        }
        else{
            imageSwitcher.setImageResource(images[1]);
            textbox.setText("FEL");
            //infoText.setText( "@string/info_main");
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
    public void openPopup(){
        infoDialog.setContentView(R.layout.info_popup);
        closePopUp = (ImageView) infoDialog.findViewById(R.id.closePopUp);
        //infoText.setText( "@string/info_main");
        closePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePopUp();
            }
        });
        infoDialog.show();
        infoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void closePopUp(){
        infoDialog.dismiss();
        vibrator.vibrate(vibratorPattern,-1);
    }
}
