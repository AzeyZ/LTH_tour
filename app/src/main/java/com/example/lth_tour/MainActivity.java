package com.example.lth_tour;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_LOCATION_PERMISSION= 1;
    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // textView = (TextView) findViewById(R.id.textView4);
        button= (Button) findViewById(R.id.searchButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(
                    getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(
                                MainActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                REQUEST_CODE_LOCATION_PERMISSION
                        );
                    } else{
                    //getCurrentLocation();
                }
            openPlatsActivity();
            }
        });



    }

 /*   @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE_LOCATION_PERMISSION && grantResults.length>0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }*/

    public void openPlatsActivity(){
        Intent intent = new Intent(this, PlatsActivity.class);
        startActivity(intent);
    }

    /*private void getCurrentLocation(){
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.getFusedLocationProviderClient(MainActivity.this)
                .requestLocationUpdates(locationRequest,new LocationCallback(){
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(MainActivity.this)
                                .removeLocationUpdates(this);
                        if(locationResult!=null && locationResult.getLocations().size()>0){
                            int latestLocationIndex = locationResult.getLocations().size()-1;
                            double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                            textView.setText(
                            String.format("Latitude: %s\n Longitude: %s ", latitude, longitude));
                        }
                    }
                }, Looper.getMainLooper());


    }*/
}

