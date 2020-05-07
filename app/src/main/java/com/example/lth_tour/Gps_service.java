package com.example.lth_tour;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class Gps_service extends Service {
    private final IBinder mBinder = new LocalBinder();
    public Gps_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        Gps_service getService() {
            return Gps_service.this;
        }
    }

    @Override
    public void onCreate(){
        //Intent intent = new Intent("com.example.lth_tour.TEST");
        //intent.putExtra("test", "123");
        //sendBroadcast(intent);
    }
    public void sendTest(){
        startService(new Intent(getApplicationContext(), Gps_service.class));
        Intent intent = new Intent("com.example.lth_tour.TEST");
        intent.putExtra("test", "123");
        sendBroadcast(intent);
    }
}
