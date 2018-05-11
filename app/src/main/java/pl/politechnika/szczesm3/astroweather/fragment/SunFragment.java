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

import java.text.DecimalFormat;
import java.util.Calendar;

import pl.politechnika.szczesm3.astroweather.R;
import pl.politechnika.szczesm3.astroweather.config.AppConfig;

public class SunFragment extends Fragment {

    private double LAT, LON;
    private Integer FREQ;
    private TextView sunriseTime, sunriseAzimuth, sunsetTime, sunsetAzimuth, dusk, dawn;
    private final Handler handler = new Handler();

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

    private void startHandler() {
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
        sunriseAzimuth = v.findViewById(R.id.sunriseAzimuth);
        sunsetTime = v.findViewById(R.id.sunsetTime);
        sunsetAzimuth = v.findViewById(R.id.sunsetAzimuth);
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
                Calendar.getInstance().get(Calendar.YEAR) + 1900,
                Calendar.getInstance().get(Calendar.MONTH) + 1,
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                Calendar.getInstance().get(Calendar.SECOND),
                Calendar.getInstance().get(Calendar.ZONE_OFFSET) / 3600000,
                true
        );
        final AstroCalculator calc = new AstroCalculator(date, loc);
        setData(calc);
    }

    private void setData(AstroCalculator calc) {
        AstroCalculator.SunInfo si = calc.getSunInfo();

        String sunrise = (si.getSunrise().getHour()) + ":" + si.getSunrise().getMinute() + ":" + si.getSunrise().getSecond();
        sunrise = sunrise + "  " + si.getSunrise().getDay() + "/" + si.getSunrise().getMonth() + "/" + si.getSunrise().getYear();
        sunriseTime.setText(sunrise);

        double sunriseA = si.getAzimuthRise();
        DecimalFormat df = new DecimalFormat("##.00");
        String sunriseS = df.format(sunriseA) + "°";
        sunriseAzimuth.setText(sunriseS);

        String sunset = (si.getSunset().getHour()) + ":" + si.getSunset().getMinute() + ":" + si.getSunset().getSecond();
        sunset = sunset + "  " + si.getSunset().getDay() + "/" + si.getSunset().getMonth() + "/" + si.getSunset().getYear();
        sunsetTime.setText(sunset);

        double sunsetA = calc.getSunInfo().getAzimuthSet();
        String sunsetS = df.format(sunsetA) + "°";
        sunsetAzimuth.setText(sunsetS);

        String duskS = (si.getTwilightMorning().getHour()) + ":" + si.getTwilightMorning().getMinute() + ":" + si.getTwilightMorning().getSecond();
        duskS = duskS + "  " + si.getTwilightMorning().getDay() + "/" + si.getTwilightMorning().getMonth() + "/" + si.getTwilightMorning().getYear();
        dusk.setText(duskS);

        String dawnS = (si.getTwilightEvening().getHour()) + ":" + si.getTwilightEvening().getMinute() + ":" + si.getTwilightEvening().getSecond();
        dawnS = dawnS + "  " + si.getTwilightEvening().getDay() + "/" + si.getTwilightEvening().getMonth() + "/" + si.getTwilightEvening().getYear();
        dawn.setText(dawnS);
    }
}
