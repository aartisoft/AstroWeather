package pl.politechnika.szczesm3.astroweather.data;

import org.json.JSONArray;
import org.json.JSONObject;

public class Wind implements JSONCrawler {
    public double chill;
    public double direction;
    public double speed;

    @Override
    public void crawl(JSONObject obj) {
        this.chill = obj.optDouble("chill");
        this.direction = obj.optDouble("direction");
        this.speed = obj.optDouble("speed");
    }

    @Override
    public void crawl(JSONArray array) {

    }
}
