package pl.politechnika.szczesm3.astroweather.data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.politechnika.szczesm3.astroweather.config.AppConfig;

public class Forecast implements JSONCrawler {

    private static final int DAYS_COUNT = AppConfig.DAYS_COUNT;
    public List<DayForecast> dayForecasts;

    @Override
    public void crawl(JSONObject obj) {

    }

    @Override
    public void crawl(JSONArray array) {
        dayForecasts = new ArrayList<DayForecast>();
        for (int i = 1; i <= DAYS_COUNT; i++) {
            DayForecast dayForecast = new DayForecast();
            dayForecast.crawl(array.optJSONObject(i));
            dayForecasts.add(dayForecast);
        }
    }
}
