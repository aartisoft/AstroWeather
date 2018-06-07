package pl.politechnika.szczesm3.astroweather.config;

import java.math.BigInteger;

public class AppConfig {
    private static final AppConfig ourInstance = new AppConfig();

    public static final Integer MAX_WEATHER_INFO_AGE = 420000;
    public static final int DAYS_COUNT = 4;

    private double latitude;
    private double longitude;
    private Integer refreshInterval;
    private Integer woeid;
    private String units;


    public static AppConfig getInstance() {
        return ourInstance;
    }

    private AppConfig() {
        latitude = 51.648042d;
        longitude = 19.376321d;
        refreshInterval = 7;
        woeid = 505120;
        units = "c";
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Integer getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(Integer refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

    public Integer getWoeid() {
        return woeid;
    }

    public void setWoeid(Integer woeid) {
        this.woeid = woeid;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return "AppConfig{" +
                "latitude= " + latitude +
                ", longitude= " + longitude +
                ", refreshInterval= " + refreshInterval +
                ", woeid= " + woeid +
                ", units= " + units +
                '}';
    }
}
