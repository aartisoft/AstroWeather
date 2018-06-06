package pl.politechnika.szczesm3.astroweather.data;

import org.json.JSONArray;
import org.json.JSONObject;

public class Place implements JSONCrawler {
    public String name;
    public int woeid;
    public CentroId centroid;

    @Override
    public void crawl(JSONObject obj) {
        this.name = obj.optString("name");
        this.woeid = obj.optInt("woeid");
        centroid = new CentroId();
        centroid.crawl(obj.optJSONObject("centroid"));
    }

    @Override
    public void crawl(JSONArray array) {

    }
}
