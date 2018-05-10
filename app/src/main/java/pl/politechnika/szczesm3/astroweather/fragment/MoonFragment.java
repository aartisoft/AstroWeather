package pl.politechnika.szczesm3.astroweather.fragment;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
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
    final Handler  handler = new Handler();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View moonView = inflater.inflate(R.layout.fragment_moon, container, false);
        retrieveTextViews(moonView);
        fetchData();
        init();
        handler.postDelayed(new Runnable(){
            public void run(){
                Log.d("LOOP MESSAGE", "*** moon refreshed ***");
                fetchData();
                init();
                handler.postDelayed(this, FREQ * 1000);
            }
        }, FREQ * 1000);
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
        double proc = calc.getMoonInfo().getIllumination() * 100;
        String procText = String.valueOf(proc) + "%";
        phase.setText(procText);
        synod.setText(String.valueOf(calc.getMoonInfo().getAge()));
    }
}
