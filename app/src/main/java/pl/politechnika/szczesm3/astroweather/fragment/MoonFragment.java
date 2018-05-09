package pl.politechnika.szczesm3.astroweather.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import pl.politechnika.szczesm3.astroweather.R;
import pl.politechnika.szczesm3.astroweather.config.AppConfig;

public class MoonFragment extends Fragment {

    private double LAT, LON;
    private Integer FREQ;
    private TextView sunriseTime, sunsetTime, closeNew, closeFull, phase, synod;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View moonView = inflater.inflate(R.layout.fragment_moon, container, false);
        retrieveTextViews(moonView);
        fetchData();
        init();
        return moonView;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
        init();
    }

    private void retrieveTextViews(View v) {
        sunriseTime = v.findViewById(R.id.sunriseTime);
        sunsetTime = v.findViewById(R.id.sunsetTime);
        closeNew = v.findViewById(R.id.newMoon);
        closeFull = v.findViewById(R.id.fullMoon);
        phase = v.findViewById(R.id.phase);
        synod = v.findViewById(R.id.synod);
    }

    private void fetchData() {
        LAT = AppConfig.getInstance().getLatitude();
        LON = AppConfig.getInstance().getLongtitude();
        FREQ = AppConfig.getInstance().getRefreshInterval();
    }

    private void init() {
        AstroCalculator.Location loc = new AstroCalculator.Location(LAT, LON);
        AstroDateTime date = new AstroDateTime();
        final AstroCalculator calc = new AstroCalculator(date, loc);
        setData(calc);
    }

    private void setData(AstroCalculator calc) {
        sunriseTime.setText(calc.getMoonInfo().getMoonrise().toString());
        sunsetTime.setText(calc.getMoonInfo().getMoonset().toString());
        closeNew.setText(calc.getMoonInfo().getNextNewMoon().toString());
        closeFull.setText(calc.getMoonInfo().getNextFullMoon().toString());
        phase.setText(String.valueOf(calc.getMoonInfo().getIllumination()) + "%");
        synod.setText(String.valueOf(calc.getMoonInfo().getAge()));
    }
}
