package pl.politechnika.szczesm3.astroweather.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "location")
public class Location {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String name;
    @NonNull
    private double latitude;
    @NonNull
    private double longitude;
    @NonNull
    private double woeid;

    public Location() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(@NonNull double latitude) {
        this.latitude = latitude;
    }

    @NonNull
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(@NonNull double longitude) {
        this.longitude = longitude;
    }

    @NonNull
    public double getWoeid() {
        return woeid;
    }

    public void setWoeid(@NonNull double woeid) {
        this.woeid = woeid;
    }

    @Override
    public String toString() {
        return name + " | " + latitude + "  " + longitude;
    }
}
