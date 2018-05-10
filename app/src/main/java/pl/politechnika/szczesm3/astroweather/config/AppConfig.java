package pl.politechnika.szczesm3.astroweather.config;

public class AppConfig {
    private static final AppConfig ourInstance = new AppConfig();

    private double latitude;
    private double longitude;
    private Integer refreshInterval;


    public static AppConfig getInstance() {
        return ourInstance;
    }

    private AppConfig() {
        latitude = 51.648042d;
        longitude = 19.376321d;
        refreshInterval = 7;
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

    @Override
    public String toString() {
        return "AppConfig{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", refreshInterval=" + refreshInterval +
                '}';
    }
}
