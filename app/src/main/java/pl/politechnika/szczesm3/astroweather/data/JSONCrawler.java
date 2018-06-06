package pl.politechnika.szczesm3.astroweather.data;

import org.json.JSONObject;

public interface JSONCrawler {
    void crawl(JSONObject obj);
}
