package pl.politechnika.szczesm3.astroweather.data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Forecast implements JSONCrawler {

    private static final int DAYS_COUNT = 5;
    public List<DayForecast> dayForecasts;

    @Override
    public void crawl(JSONObject obj) {

    }

    @Override
    public void crawl(JSONArray array) {
        dayForecasts = new ArrayList<DayForecast>();
        for (int i = 0; i < DAYS_COUNT; i++) {
            DayForecast dayForecast = new DayForecast();
            dayForecast.crawl(array.optJSONObject(i));
            dayForecasts.add(dayForecast);
        }
    }
}
