package com.example.lth_tour;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import java.util.ArrayList;

public class GpsActivity extends AppCompatActivity implements SensorEventListener {
    private ArrayList<PlatsObjekt> platser = new ArrayList<>();
    private static int indexTour = 1;
    private double[] results = new double[2];
    private int bearing = 0;
    private static final int REQUEST_CODE_LOCATION_PERMISSION= 1;
    private ImageButton homeButton;
    private TextView txtMeter;
    private TextView txt_heading;
    private MyReceiver myReceiver;
    private GpsService mService = null;
    private boolean mBound = false;
    ImageView arrow_img;
    int mAzimuth;
    private SensorManager mSensorManager;
    private Sensor mRotationV, mAccelerometer, mMagnetometer;
    boolean haveSensor = false, haveSensor2 = false;
    float[] rMat = new float[9];
    float[] orientation = new float[3];
    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;
    public static Vibrator vibrator;
    private MediaPlayer mp;
    RelativeLayout currentLayout;

    private ServiceConnection mServiceConnection = new ServiceConnection() {

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
        setContentView(R.layout.activity_gps);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mp = MediaPlayer.create(this, R.raw.alarmsound);
        start();
        addPlatser();
        currentLayout =
                (RelativeLayout) findViewById(R.id.gps_layout);
    }

    private void addPlatser(){

        //testplatser
        platser.add(0, new PlatsObjekt(55.697129, 13.196747,"Andreas Hus", "Här bor Andreas", "Andreas hemligheter finner du inte här","ehuset"));

        //rundturen
        platser.add(1,new PlatsObjekt(55.709439, 13.209761, "M-huset", "M-huset information", "mer info om M-huset", "mhuset"));
        platser.add(2,new PlatsObjekt(55.711066, 13.210312, "E-huset", "E-huset info", "Mer om E-huset", "ehuset"));
        platser.add(3,new PlatsObjekt(55.712557, 13.211693, "V-huset", "V-huset info", "Mer om V-huset", "vhuset"));
        platser.add(4,new PlatsObjekt(55.713700, 13.212326, "A-huset", "A-huset info", "Mer om A-huset", "ahuset"));
        platser.add(5,new PlatsObjekt(55.714881, 13.212598, "IKDC", "IKDC info", "Mer om IKDC", "ikdc"));
        platser.add(6,new PlatsObjekt(55.715884, 13.210055, "Kemicentrum", "Kemicentrum info", "Mer om Kemicentrum", "khuset"));
        platser.add(7,new PlatsObjekt(55.712248, 13.209284, "Kårhuset", "Kårhuset info", "Mer om Kårhuset", "karhuset"));
        platser.add(8,new PlatsObjekt(55.710662, 13.206892, "Mattehuset", "Mattehuset info", "Mer om Mattehuset", "mattehuset"));
    }

    @Override
    public void onStart() {
        super.onStart();
        txtMeter = (TextView) findViewById(R.id.txt_meter);
        txt_heading = (TextView) findViewById(R.id.txt_heading);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        homeButton = (ImageButton) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
        arrow_img = findViewById(R.id.img_arrow);
        bindService(new Intent(GpsActivity.this, GpsService.class), mServiceConnection,
                Context.BIND_AUTO_CREATE);

    }


    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
        super.onPause();
        stop();
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
                new IntentFilter(GpsService.ACTION_BROADCAST));
        start();
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

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            SensorManager.getRotationMatrixFromVector(rMat, event.values);
            mAzimuth = (int) (Math.toDegrees(SensorManager.getOrientation(rMat, orientation)[0]) + 360) % 360;

        }

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
            mLastAccelerometerSet = true;
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
            mLastMagnetometerSet = true;
        }
        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            SensorManager.getRotationMatrix(rMat, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(rMat, orientation);
            mAzimuth = (int) (Math.toDegrees(SensorManager.getOrientation(rMat, orientation)[0]) + 360) % 360;
        }
        mAzimuth = Math.round(mAzimuth);
        arrow_img.setRotation(-mAzimuth + bearing);
        if((-mAzimuth + bearing) < -150 && (-mAzimuth + bearing)>-210 ){
            mp.start();
            currentLayout.setBackgroundColor(Color.RED);
        }else{
            if(mp.isPlaying()) {
                mp.pause();
                currentLayout.setBackgroundColor(Color.WHITE);
            }
        }


    }

    public void start() {
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) == null) {
            if ((mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null) || (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) == null)) {
                noSensorsAlert();
            }
            else {
                mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                haveSensor = mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
                haveSensor2 = mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_UI);
            }
        }
        else{
            mRotationV = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            haveSensor = mSensorManager.registerListener(this, mRotationV, SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void noSensorsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Your device doesn't support the Compass.")
                .setCancelable(false)
                .setNegativeButton("Close",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        alertDialog.show();
    }

    public void stop() {
        if(haveSensor && haveSensor2){
            mSensorManager.unregisterListener(this,mAccelerometer);
            mSensorManager.unregisterListener(this,mMagnetometer);
        }
        else{
            if(haveSensor)
                mSensorManager.unregisterListener(this,mRotationV);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            Location location = intent.getParcelableExtra(GpsService.EXTRA_LOCATION);
            if (location != null) {
                results[0] = location.getLatitude();
                results[1] = location.getLongitude();
                float[] nextLocation = Utils.calculateDistanceTo(results[0],results[1],platser.get(indexTour).latitude, platser.get(indexTour).longitude);
                double distanceNext = nextLocation[0];
                bearing = (int)nextLocation[1];
                txtMeter.setText((int)distanceNext + " m");
                if(distanceNext<30) {
                    vibrator.vibrate(2000);
                    Toast toast = Toast.makeText(GpsActivity.this, platser.get(indexTour).title,Toast.LENGTH_LONG);
                    toast.show();
                    openPlatsActivity();
                }
            }

        }
    }

    private void openPlatsActivity() {
        Intent intent = new Intent(this, PlatsActivity.class);
        intent.putExtra("plats", platser.get(indexTour));
        if(indexTour<9) {
            indexTour++;
        }
        else{
            indexTour = 1;
        }

        startActivity(intent);
    }
}


