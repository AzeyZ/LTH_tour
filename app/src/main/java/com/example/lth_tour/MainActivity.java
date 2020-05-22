package com.example.lth_tour;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.hardware.SensorManager;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    public static final String PLATS_TITLE = "com.example.lth_tour.platsTitle";
    // A reference to the service used to get location updates.
    private GpsService mService = null;
    // Tracks the bound state of the service.
    private boolean mBound = false;
    private TextView textView;
    private ImageView button;
    private ImageView info_but;
    private ImageView väljBygg;
    public double latitude;
    public double longitude;
    private MyReceiver myReceiver;
    private MediaPlayer mp;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView menuText;

    //PopUp dialog
    private ImageView closePopUp;
    private Dialog infoDialog;

    //Vibration
    private Vibrator vibrator;
    private long[] vibratorPattern;


    // Shake sensor
    private SensorManager sm;
    private float acelVal; //current acceleration & gravity
    private float acelLast; //last acceleration & gravity
    private float shake; //acceleration diffr. from gravity


    //test
    private ImageView image_lthlogo;

    // Monitors the state of the connection to the service.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            GpsService.LocalBinder binder = (GpsService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myReceiver = new MyReceiver();
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.relativeLayout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        navigationView.bringToFront();
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibratorPattern = new long[]{0, 50}; //sleep 0 ms, vibrate 50 ms


    }

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            GpsActivity.indexTour=1;
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //textView = (TextView) findViewById(R.id.textView);
        info_but = (ImageView) findViewById(R.id.infoButton);
        button = (ImageView) findViewById(R.id.search_btn);
        väljBygg = (ImageView) findViewById(R.id.väljByggnad);
        image_lthlogo = (ImageView) findViewById(R.id.image_lthlogo);
        mp = MediaPlayer.create(this, R.raw.clickon);
        /*textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startGPS(v);
            }
        });*/
        if (ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION
            );
        }
        infoDialog = new Dialog(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissions();
            }
        });
        väljBygg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        info_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPopup();
            }
        });
        // Bind to the service. If the service is in foreground mode, this signals to the service
        // that since this activity is in the foreground, the service can exit foreground mode.
        bindService(new Intent(this, GpsService.class), mServiceConnection,
                Context.BIND_AUTO_CREATE);

        image_lthlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlatsActivity();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
                new IntentFilter(GpsService.ACTION_BROADCAST));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (mBound) {
            // Unbind from the service. This signals to the service that this activity is no longer
            // in the foreground, and the service can respond by promoting itself to a foreground
            // service.
            unbindService(mServiceConnection);
            mBound = false;
        }
        super.onStop();
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
    /*
    private void startGPS(View v){
        if(ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION
            );
        } else{
            getCurrentLocation();
        }
    }
    */
   @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

        public void checkPermissions(){
            if (ContextCompat.checkSelfPermission(
                    getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_LOCATION_PERMISSION
                );
            }
            if (ContextCompat.checkSelfPermission(
                    getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mService.requestLocationUpdates();
                openGpsActivity();
                mp.start();
            }

        }


    public void openPlatsActivity() {
        Intent intent = new Intent(this, PlatsActivity.class);
        startActivity(intent);
    }

    public void openGpsActivity() {
        Intent intent = new Intent(this, GpsActivity.class);
        startActivity(intent);
    }
    public void openPopup(){
        infoDialog.setContentView(R.layout.info_popup);
        closePopUp = (ImageView) infoDialog.findViewById(R.id.closePopUp);

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
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.nav_mhuset:
                GpsActivity.indexTour=1;
                checkPermissions();
                break;
            case R.id.nav_ehuset:
                GpsActivity.indexTour=2;
                checkPermissions();
                break;
            case R.id.nav_vhuset:
                GpsActivity.indexTour=3;
                checkPermissions();
                break;
            case R.id.nav_ahuset:
                GpsActivity.indexTour=4;
                checkPermissions();
                break;
            case R.id.nav_ikdc:
                GpsActivity.indexTour=5;
                checkPermissions();
                break;
            case R.id.nav_khuset:
                GpsActivity.indexTour=6;
                checkPermissions();
                break;
            case R.id.nav_karhuset:
                GpsActivity.indexTour=7;
                checkPermissions();
                break;
            case R.id.nav_emattehuset:
                GpsActivity.indexTour=8;
                checkPermissions();
                break;

        }

        return true;
    }


    /**
     * Receiver for broadcasts sent by {@link GpsService}.
     */
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location location = intent.getParcelableExtra(GpsService.EXTRA_LOCATION);
            if (location != null) {
                Toast.makeText(MainActivity.this, Utils.getLocationText(location),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}

