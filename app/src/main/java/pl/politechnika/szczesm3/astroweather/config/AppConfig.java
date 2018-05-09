package pl.politechnika.szczesm3.astroweather.config;

public class AppConfig {
    private static final AppConfig ourInstance = new AppConfig();

    private Float latitude;
    private Float longtitude;
    private Integer refreshInterval;


    public static AppConfig getInstance() {
        return ourInstance;
    }

    private AppConfig() {
        latitude = 0.00f;
        longtitude = 0.00f;
        refreshInterval = 1;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Float longtitude) {
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
