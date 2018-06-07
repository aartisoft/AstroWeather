package pl.politechnika.szczesm3.astroweather.data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class DayForecast implements JSONCrawler, Serializable {
    public String date;
    public String day;
    public String code;
    public double high;
    public double low;
    public String text;

    @Override
    public void crawl(JSONObject obj) {
        this.date = obj.optString("date");
        this.day = obj.optString("day");
        this.code = obj.optString("code");
        this.high = obj.optDouble("high");
        this.low = obj.optDouble("low");
        this.text = obj.optString("text");
    }

    @Override
    public void crawl(JSONArray array) {

    }
}
