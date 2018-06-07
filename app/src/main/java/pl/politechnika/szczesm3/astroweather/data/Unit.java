package pl.politechnika.szczesm3.astroweather.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Unit implements JSONCrawler {
    public String distance;
    public String pressure;
    public String speed;
    public String temperature;


    @Override
    public void crawl(JSONObject obj) {
        this.distance = obj.optString("distance");
        this.pressure = obj.optString("pressure");
        this.speed = obj.optString("speed");
        this.temperature = obj.optString("temperature");
    }

    @Override
    public void crawl(JSONArray array) {

    }
}
