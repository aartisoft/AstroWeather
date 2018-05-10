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

import java.util.Calendar;

import pl.politechnika.szczesm3.astroweather.R;
import pl.politechnika.szczesm3.astroweather.config.AppConfig;

public class SunFragment extends Fragment {

    private double LAT, LON;
    private Integer FREQ;
    private TextView sunriseTime, sunriseAzimuth, sunsetTime, sunsetAzimuth, dusk, dawn;
    final Handler handler = new Handler();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View sunView = inflater.inflate(R.layout.fragment_sun, container, false);
        retrieveTextViews(sunView);
        fetchData();
        init();
        return sunView;
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onStart() {
        super.onStart();
        startHandler();
    }

    public void startHandler() {
        handler.postDelayed(new Runnable(){
            public void run(){
                Log.d("LOOP MESSAGE", "*** sun refreshed ***");
                fetchData();
                init();
                handler.postDelayed(this, FREQ * 1000);
            }
        }, FREQ * 1000);
    }

    private void retrieveTextViews(View v) {
        sunriseTime = v.findViewById(R.id.sunriseTime);
        sunriseAzimuth = v.findViewById(R.id.sunriseAzymut);
        sunsetTime = v.findViewById(R.id.sunsetTime);
        sunsetAzimuth = v.findViewById(R.id.sunsetAzymut);
        dusk = v.findViewById(R.id.dusk);
        dawn = v.findViewById(R.id.dawn);
    }

    private void fetchData() {
        LAT = AppConfig.getInstance().getLatitude();
        LON = AppConfig.getInstance().getLongitude();
        FREQ = AppConfig.getInstance().getRefreshInterval();
    }

    private void init() {
        AstroCalculator.Location loc = new AstroCalculator.Location(LAT, LON);
        Log.d("CURRENT_TIME", Calendar.getInstance().getTime().toString());
        AstroDateTime date = new AstroDateTime(
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                Calendar.getInstance().get(Calendar.SECOND),
                Calendar.getInstance().get(Calendar.ZONE_OFFSET) / 3600000,
                false
        );
        final AstroCalculator calc = new AstroCalculator(date, loc);
        setData(calc);
    }

    private void setData(AstroCalculator calc) {
        sunriseTime.setText(calc.getSunInfo().getSunrise().toString());
        sunriseAzimuth.setText(String.valueOf(calc.getSunInfo().getAzimuthRise()));
        sunsetTime.setText(calc.getSunInfo().getSunset().toString());
        sunsetAzimuth.setText(String.valueOf(calc.getSunInfo().getAzimuthSet()));
        dusk.setText(calc.getSunInfo().getTwilightMorning().toString());
        dawn.setText(calc.getSunInfo().getTwilightEvening().toString());
    }
}
