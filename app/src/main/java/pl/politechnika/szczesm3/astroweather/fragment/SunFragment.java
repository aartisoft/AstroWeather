package pl.politechnika.szczesm3.astroweather.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.politechnika.szczesm3.astroweather.R;

public class SunFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View sunView = inflater.inflate(R.layout.fragment_sun, container, false);

        return sunView;
    }
}
