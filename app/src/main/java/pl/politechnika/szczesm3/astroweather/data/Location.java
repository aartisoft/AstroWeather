package pl.politechnika.szczesm3.astroweather.data;

import org.json.JSONArray;
import org.json.JSONObject;

public class Location implements JSONCrawler {
    public String city;
    public String country;
    public String region;
    @Override
    public void crawl(JSONObject obj) {
        this.city = obj.optString("city");
        this.country = obj.optString("country");
        this.region = obj.optString("region");

    }

    @Override
    public void crawl(JSONArray array) {

    }
}
