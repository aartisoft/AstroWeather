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

public class MoonFragment extends Fragment {

    private double LAT, LON;
    private Integer FREQ;
    private TextView sunriseTime, sunsetTime, closeNew, closeFull, phase, synod;
    private final Handler handler = new Handler();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View moonView = inflater.inflate(R.layout.fragment_moon, container, false);
        retrieveTextViews(moonView);
        fetchData();
        init();
        return moonView;
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
                Log.d("LOOP MESSAGE", "*** moon refreshed ***");
                fetchData();
                init();
                handler.postDelayed(this, FREQ * 1000);
            }
        }, FREQ * 1000);
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
        LON = AppConfig.getInstance().getLongitude();
        FREQ = AppConfig.getInstance().getRefreshInterval();
    }

    private void init() {
        AstroCalculator.Location loc = new AstroCalculator.Location(LAT, LON);
        Log.d("CURRENT_TIME", Calendar.getInstance().getTime().toString());
        AstroDateTime date = new AstroDateTime(
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH) + 1,
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
        AstroCalculator.MoonInfo mi = calc.getMoonInfo();

        String sunrise = (mi.getMoonrise().getHour()) + ":" + mi.getMoonrise().getMinute() + ":" + mi.getMoonrise().getSecond();
        sunrise = sunrise + "  " + mi.getMoonrise().getDay() + "/" + mi.getMoonrise().getMonth() + "/" + mi.getMoonrise().getYear();
        sunriseTime.setText(sunrise);

        String sunset = (mi.getMoonset().getHour()) + ":" + mi.getMoonset().getMinute() + ":" + mi.getMoonset().getSecond();
        sunset = sunset + "  " + mi.getMoonset().getDay() + "/" + mi.getMoonset().getMonth() + "/" + mi.getMoonset().getYear();
        sunsetTime.setText(sunset);

        String newMoon = (mi.getNextNewMoon().getHour()) + ":" + mi.getNextNewMoon().getMinute() + ":" + mi.getNextNewMoon().getSecond();
        newMoon = newMoon + "  " + mi.getNextNewMoon().getDay() + "/" + mi.getNextNewMoon().getMonth() + "/" + mi.getNextNewMoon().getYear();
        closeNew.setText(newMoon);

        String fullMoon = (mi.getNextFullMoon().getHour()) + ":" + mi.getNextFullMoon().getMinute() + ":" + mi.getMoonset().getSecond();
        fullMoon = fullMoon + "  " + mi.getNextFullMoon().getDay() + "/" + mi.getNextFullMoon().getMonth() + "/" + mi.getNextFullMoon().getYear();
        closeFull.setText(fullMoon);

        double prc = calc.getMoonInfo().getIllumination() * 100;
        DecimalFormat df = new DecimalFormat("##.00");
        String prcText = df.format(prc) + "%";
        phase.setText(prcText);

        synod.setText(String.valueOf(Math.round(calc.getMoonInfo().getAge())));
    }
}
