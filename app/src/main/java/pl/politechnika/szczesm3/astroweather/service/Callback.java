package pl.politechnika.szczesm3.astroweather.service;

import pl.politechnika.szczesm3.astroweather.data.Channel;
import pl.politechnika.szczesm3.astroweather.data.Place;

public interface Callback {
    void callbackSuccess(Channel channel);
    void callbackSuccess(Place place);
    void callbackFailure(Exception e);
}
