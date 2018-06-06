package pl.politechnika.szczesm3.astroweather.data;

import org.json.JSONArray;
import org.json.JSONObject;

public class CentroId implements JSONCrawler {
    public Double latitude;
    public Double longitude;

    @Override
    public void crawl(JSONObject obj) {
        this.latitude = obj.optDouble("latitude");
        this.longitude = obj.optDouble("longitude");
    }

    @Override
    public void crawl(JSONArray array) {

    }
}
