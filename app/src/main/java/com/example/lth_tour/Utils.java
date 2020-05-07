package com.example.lth_tour;

import android.location.Location;

class Utils {
    static float[] calculateDistanceTo(double currentLatitude, double currentLongitude, double targetLatitude, double targetLongitude) {
        float[] results = new float[2];
        Location.distanceBetween(currentLatitude, currentLongitude,
                targetLatitude, targetLongitude, results);
        return results;
        //The computed distance is stored in results[0]. If results has length 2 or greater, the initial bearing is stored in results[1]
        //https://stackoverflow.com/questions/56144107/what-is-initial-bearing-and-final-bearing
    }
}
