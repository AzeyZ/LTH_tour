package com.example.lth_tour;

import java.io.Serializable;

public class PlatsObjekt implements Serializable {
    public double latitude = 0;
    public double longitude = 0;
    public String title = "noTitleFound";
    public String bodyFront = "error missing text";
    public String bodyMore = "error missing text";
    public String imageName = "ehuset";

    public PlatsObjekt(double latitude, double longitude, String title, String bodyFront, String bodyMore, String imageName){
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.bodyFront = bodyFront;
        this.bodyMore = bodyMore;
        this.imageName = imageName;
    }
}
