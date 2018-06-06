package pl.politechnika.szczesm3.astroweather.data;

import org.json.JSONArray;
import org.json.JSONObject;

public interface JSONCrawler {
    void crawl(JSONObject obj);
    void crawl(JSONArray array);
}
