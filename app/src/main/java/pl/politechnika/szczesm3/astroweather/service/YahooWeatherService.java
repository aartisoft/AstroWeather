package pl.politechnika.szczesm3.astroweather.service;

import android.net.Uri;
import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import pl.politechnika.szczesm3.astroweather.data.Channel;
import pl.politechnika.szczesm3.astroweather.data.Place;

public class YahooWeatherService {

    private Callback callback;
    private String location;
    private String woeid;
    private String units;
    private String ENDPOINT_LOCATION = "https://query.yahooapis.com/v1/public/yql?q=%s&format=json";
    private String LOCATION_YQL = "SELECT woeid, name, centroid FROM geo.places(1) WHERE text=\"%s\"";
    private String FORECAST_YQL = "SELECT * FROM weather.forecast WHERE woeid IN (%d) AND u = \"%s\"";

    public YahooWeatherService(Callback callback) {
        this.callback = callback;
    }

    public void getLocation(String location) {
        this.location = location;
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String ... strings) {

                String YQL = String.format(LOCATION_YQL, strings[0]);

                String endpoint = String.format(ENDPOINT_LOCATION, Uri.encode(YQL));

                try {
                    URL url = new URL(endpoint);

                    URLConnection connection = url.openConnection();
                    connection.setConnectTimeout(500);
                    connection.setReadTimeout(500);

                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    return result.toString();
                } catch (Exception e) {
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if (s == null) {
                    callback.callbackFailure(new Exception("Empty response!"));
                    return;
                }

                try {
                    JSONObject data = new JSONObject(s);
                    JSONObject queryResults = data.getJSONObject("query");
                    int count = queryResults.optInt("count");
                    if (count == 0) {
                        callback.callbackFailure(new Exception("Empty response!"));
                        return;
                    }

                    Place place = new Place();
                    place.crawl(queryResults.optJSONObject("results").optJSONObject("place"));
                    callback.callbackSuccess(place);
                } catch (JSONException e) {
                    callback.callbackFailure(e);
                }
            }
        }.execute(location);
    }

    public void getForecast(Integer woeid, String units) {
        this.woeid = String.valueOf(woeid);
        this.units = units;
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String ... strings) {

                String YQL = String.format(FORECAST_YQL, Integer.valueOf(strings[0]), strings[1]);

                String endpoint = String.format(ENDPOINT_LOCATION, Uri.encode(YQL));

                try {
                    URL url = new URL(endpoint);

                    URLConnection connection = url.openConnection();
                    connection.setConnectTimeout(500);
                    connection.setReadTimeout(500);

                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    return result.toString();
                } catch (Exception e) {
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if (s == null) {
                    callback.callbackFailure(new Exception("Empty response!"));
                    return;
                }

                try {
                    JSONObject data = new JSONObject(s);
                    JSONObject queryResults = data.getJSONObject("query");
                    int count = queryResults.optInt("count");
                    if (count == 0) {
                        callback.callbackFailure(new Exception("Empty response!"));
                        return;
                    }

                    Channel channel = new Channel();
                    channel.crawl(queryResults.optJSONObject("results").optJSONObject("channel"));
                    callback.callbackSuccess(channel);
                } catch (JSONException e) {
                    callback.callbackFailure(e);
                }
            }
        }.execute(this.woeid, this.units);
    }

    public String getWoeid() {
        return woeid;
    }

    public String getUnits() {
        return units;
    }

    public String getLocation() {
        return location;
    }
}
