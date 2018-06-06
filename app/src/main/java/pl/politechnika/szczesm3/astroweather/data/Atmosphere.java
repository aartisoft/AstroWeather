package pl.politechnika.szczesm3.astroweather.data;

import org.json.JSONArray;
import org.json.JSONObject;

public class Atmosphere implements JSONCrawler {
    public double humidity;
    public double pressure;
    public double rising;
    public double visibility;

    @Override
    public void crawl(JSONObject obj) {
        this.humidity = obj.optDouble("humidity");
        this.pressure = obj.optDouble("pressure");
        this.rising = obj.optDouble("rising");
        this.visibility = obj.optDouble("visibility");
    }

    @Override
    public void crawl(JSONArray array) {

    }
}
