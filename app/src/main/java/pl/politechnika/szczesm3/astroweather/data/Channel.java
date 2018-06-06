package pl.politechnika.szczesm3.astroweather.data;

import org.json.JSONArray;
import org.json.JSONObject;

public class Channel implements JSONCrawler{
    public Unit unit;
    public Location location;
    public Wind wind;
    public Atmosphere atmosphere;
    public Item item;

    @Override
    public void crawl(JSONObject obj) {
        this.unit = new Unit();
        this.unit.crawl(obj.optJSONObject("units"));

        this.location = new Location();
        this.location.crawl(obj.optJSONObject("location"));

        this.wind = new Wind();
        this.wind.crawl(obj.optJSONObject("wind"));

        this.atmosphere = new Atmosphere();
        this.atmosphere.crawl(obj.optJSONObject("atmosphere"));

        this.item = new Item();
        this.item.crawl(obj.optJSONObject("item"));
    }

    @Override
    public void crawl(JSONArray array) {

    }
}
