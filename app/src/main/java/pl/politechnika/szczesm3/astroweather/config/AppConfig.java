package pl.politechnika.szczesm3.astroweather.config;

public class AppConfig {
    private static final AppConfig ourInstance = new AppConfig();

    private double latitude;
    private double longtitude;
    private Integer refreshInterval;


    public static AppConfig getInstance() {
        return ourInstance;
    }

    private AppConfig() {
        latitude = 0.00f;
        longtitude = 0.00f;
        refreshInterval = 15;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
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
                ", longtitude=" + longtitude +
                ", refreshInterval=" + refreshInterval +
                '}';
    }
}
