package pl.politechnika.szczesm3.astroweather.data;

import org.json.JSONArray;
import org.json.JSONObject;

public class Condition implements JSONCrawler {

    public String date;
    public double temp;
    public String text;

    @Override
    public void crawl(JSONObject obj) {
        this.date = obj.optString("date");
        this.temp = obj.optDouble("temp");
        this.text = obj.optString("text");
    }

    @Override
    public void crawl(JSONArray array) {

    }
}
