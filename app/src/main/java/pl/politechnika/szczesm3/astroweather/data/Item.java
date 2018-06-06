package pl.politechnika.szczesm3.astroweather.data;

import org.json.JSONArray;
import org.json.JSONObject;


public class Item implements JSONCrawler {

    public double lat;
    public double lon;
    public Condition condition;
    public Forecast forecast;

    @Override
    public void crawl(JSONObject obj) {
        this.lat = obj.optDouble("lat");
        this.lon = obj.optDouble("long");

        this.condition = new Condition();
        condition.crawl(obj.optJSONObject("condition"));

        this.forecast = new Forecast();
        this.forecast.crawl(obj.optJSONArray("forecast"));
    }

    @Override
    public void crawl(JSONArray array) {

    }
}
