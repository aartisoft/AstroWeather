package pl.politechnika.szczesm3.astroweather.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.politechnika.szczesm3.astroweather.R;

public class ForecastFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View sunView = inflater.inflate(R.layout.fragment_forecast, container, false);
        return sunView;
    }
}
