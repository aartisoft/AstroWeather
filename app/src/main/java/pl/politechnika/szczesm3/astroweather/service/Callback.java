package pl.politechnika.szczesm3.astroweather.service;

import org.json.JSONObject;

import pl.politechnika.szczesm3.astroweather.data.Channel;
import pl.politechnika.szczesm3.astroweather.data.Place;

public interface Callback {
    void callbackSuccess(JSONObject json);
    void callbackFailure(Exception e);
}
