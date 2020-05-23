package com.example.lth_tour;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

    // Shake sensor
    private SensorManager sm;
    private float acelVal; //current acceleration & gravity
    private float acelLast; //last acceleration & gravity
    private float shake; //acceleration diffr. from gravity

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
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibratorPattern = new long[]{0, 50}; //sleep 0 ms, vibrate 50 ms
        //String platsTitle = i.getStringExtra(MainActivity.PLATS_TITLE);
        //changePlace(plats.title);

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
        }
        else if(platsTitle.equals("E-huset")){
            imageSwitcher.setImageResource(images[1]);
            textbox.setText("E-huset vid LTH är ett gemensamt hus för forskning och undervisning " +
                    "vid institutionerna för Biomedicinsk teknik, Datavetenskap, Elektro- och informationsteknik " +
                    "och Fastighetsvetenskap. \nI huset har E-sektionen, D-sektionen samt Lantmätarna sina lokaler.\n" +
                    "Tillsammans driver de LED-caféet på entréplan, i anslutning till foajén.");
        }
        else if(platsTitle.equals("V-huset")){
            imageSwitcher.setImageResource(images[2]);
            textbox.setText("I V-huset finns verksamheter som har anknytning till projektering,"+
                    " byggande och förvaltning av byggnader och annan infrastruktur."+
                    " De utbildningar som främst har sin anknytning till V-huset är: " +
                    "Civilingenjörsutbildningen i Väg och vatten, Lantmäteri, Riskhantering och Brandingenjörsutbildningen.");
        }
        else if(platsTitle.equals("A-huset")){
            imageSwitcher.setImageResource(images[3]);
            textbox.setText("Här utbildar  och vidareutbildar vi framtidens samhällsbyggare – samtidigt"+
                    " som vi bygger upp ny kunskap inom de tre forskarutbildningsämnena arkitektur, byggande"+
                    " och arkitektur och miljöpsykologi. Utbildning och forskning bedrivs av avdelningarna"+
                    " för arkitektur, boende och bostadsutveckling samt energi och byggnadsdesign.");
        }
        else if(platsTitle.equals("IKDC")){
            imageSwitcher.setImageResource(images[4]);
            textbox.setText("IKDC rymmer Institutionen för designvetenskaper, Industridesignskolan"+
                    " och flera forskningslaboratorier såsom Virtual Reality-, Usability-, Rapid Prototyping-"+
                    ", aerosol-, klimat- och mekaniklaboratorier." +
                    "IKDC är resultatet av Stichting Ikea Foundations satsning för att stötta utbildning i industridesign vid Lunds Tekniska Högskola.");
        }
        else if(platsTitle.equals("Kemicentrum")){
            imageSwitcher.setImageResource(images[5]);
            textbox.setText("På Kemicentrum bedrivs forskning och utbildning inom kemi i världsklass."+
                    " Här arbetar forskare från olika forskningsområden och inriktningar inom kemin"+
                    "Kemicentrums läge i det norra universitetsområdet i Lund.bidrar till en kreativ studie-och forskningmiljö." );
        }
        else if(platsTitle.equals("Kårhuset")){
            imageSwitcher.setImageResource(images[6]);
            textbox.setText("Teknologkåren ville ha ett eget kårhus som teknologerna på KTH och Chalmers hade, men i många år fick de nöja sig med en källare i V-huset." +
                    "\n" +
                    "1986 fick teknologerna nog och bestämde sig för att ta saken i egna händer.");
        }
        else if(platsTitle.equals("Mattehuset")){
            imageSwitcher.setImageResource(images[7]);
            textbox.setText("Matematikcentrum består av tre avdelningar, Matematik LTH och numerisk analys, Matematik NF och Matematisk statistik" +
                    "I Mattehuset finns även Hilbert Café som f-sektionen driver, och här kan du bland annat köpa gott kaffe, baguetter och sallader.");
        }
        else{
            imageSwitcher.setImageResource(images[1]);
            textbox.setText("FEL");
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
        infoText = (TextView) infoDialog.findViewById(R.id.infoText);
        if(plats.title.equals("M-huset")){
            infoText.setText("Maskinsektionen skapades år 1963, två år efter att LTH och Teknologkåren vid LTH grundades. Dock sattes detta i skrift först den 27 maj 1964. Vid utbildningens början tog sektionen in drygt 40 studenter. Med tiden har antalet studenter ökat och därmed kurserna likaså. År 2004 tillkom programmet Teknisk Design, och idag består sektionen av cirka 1000 medlemmar varav över 160 är aktiva inom sektionens olika utskott. Varje år omsätter sektionen över 2 miljoner kronor." +
                    "\t\n" +
                    "Sektionens logotyp, M:et, är en avbildning av de två kyrktornen som pryder domkyrkan. Den vackra röda färgen som Maskinsektionen starkt förknippas med är av typen Pantone 485 C. Maskinsektionen har även en egen maskot, Joe Cool, som dyker upp lite då och då när sektionen visas upp. Han finns omnämnd flera gånger om i sångböcker och sagor, och han har personnummer 580229-3550. Man får inte heller glömma Joe Cools sidekick Woodstock, som representerar Teknisk Design." +
                    "Utöver maskotar och logotyper har Maskinsektionen även en del coola prylar. En av de saker sektionen är känd för är den magnifika M-cykeln, som ofta är med vid sektionens större arrangemang under nollningen. Den byggdes någon gång på mitten av 1970-talet i verkstaden nere i M-husets källare och tar en besättning på 12 cyklister, varav en är kopplare och en annan är växlare, samt en förare som hanterar ratten och bromsarna. " +
                    "M-Husets webbsida: www.maskinsektionen.se");
        }
        else if(plats.title.equals("E-huset")){
            infoText.setText( "I E-huset finns sektionerna: D (Rosa), E (Vit)");
        }
        else if(plats.title.equals("V-huset")){
            infoText.setText( "I V-huset finns sektionen: V (Blå)");
        }
        else if(plats.title.equals("A-huset")){
            infoText.setText("I V-huset finns sektionen: A");
        }
        else if(plats.title.equals("IKDC")){
            infoText.setText("Ingvar Kamprad Designcentrum");
        }
        else if(plats.title.equals("Kemicentrum")){
            infoText.setText("I Kemicentrum finns sektionerna: W (Turkus), K (Gula)");
        }
        else if(plats.title.equals("Kårhuset")){
           infoText.setText("I kärhuset finns Teknologkåren");
        }
        else if(plats.title.equals("Mattehuset")){
            infoText.setText("I Kemicentrum finns sektionen: F (Orange)");
        }
        else{
            infoText.setText( "@string/info_main");
        }
        closePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePopUp();
            }
        });
        infoDialog.show();
        infoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    private final SensorEventListener sensorListener = new SensorEventListener(){

        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            acelLast = acelVal;
            acelVal = (float)(Math.sqrt((double) (x*x+y*y+z*z) ));
            float d = acelVal-acelLast;

            shake = shake *0.9f +d;
            if(shake > 12){
                closePopUp();

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    public void closePopUp(){
        infoDialog.dismiss();
        vibrator.vibrate(vibratorPattern,-1);
    }
}
