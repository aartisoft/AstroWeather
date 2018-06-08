package pl.politechnika.szczesm3.astroweather;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import pl.politechnika.szczesm3.astroweather.config.AppConfig;
import pl.politechnika.szczesm3.astroweather.data.Channel;
import pl.politechnika.szczesm3.astroweather.data.Place;
import pl.politechnika.szczesm3.astroweather.fragment.ForecastFragment;
import pl.politechnika.szczesm3.astroweather.fragment.MoonFragment;
import pl.politechnika.szczesm3.astroweather.fragment.SunFragment;
import pl.politechnika.szczesm3.astroweather.fragment.SunMoonConnector;
import pl.politechnika.szczesm3.astroweather.fragment.WeatherFragment;
import pl.politechnika.szczesm3.astroweather.manager.FileManager;
import pl.politechnika.szczesm3.astroweather.repository.LocationRepository;
import pl.politechnika.szczesm3.astroweather.service.Callback;
import pl.politechnika.szczesm3.astroweather.service.YahooWeatherService;

public class MainActivity extends FragmentActivity implements Callback {
    LocationRepository locationRepository;
    YahooWeatherService service;
    FileManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_main);

        fm = new FileManager(this);

        service = new YahooWeatherService(this);
        if (isInternetAvailable()){
            service.getForecast(AppConfig.getInstance().getWoeid(), AppConfig.getInstance().getUnits());
        } else {
            Toast.makeText(getApplicationContext(), "No internet connection,\n Data might be not up to date!", Toast.LENGTH_LONG).show();

        }

        locationRepository = new LocationRepository(getApplication());
        //locationRepository.deleteAll(); // uncomment to remove DB at start-up

        ImageButton goToSettings = findViewById(R.id.goToSettings);

        goToSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Settings.class);
                startActivity(intent);
            }
        });

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        setCurrentPosition();
    }

    private void setCurrentPosition(){
        String pos = String.valueOf(AppConfig.getInstance().getLatitude()) + "  " + String.valueOf(AppConfig.getInstance().getLongitude());
        TextView currentPos = findViewById(R.id.currPosition);
        currentPos.setText(pos);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setCurrentPosition();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public void callbackSuccess(JSONObject json) {
        try {
            fm.saveForecastToFile(AppConfig.getInstance().getWoeid() + AppConfig.getInstance().getUnits() + ".json", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callbackFailure(Exception e) {
        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final int PORTRAIT_TAB_COUNT = 4;
        private final int LANDSCAPE_TAB_COUNT = 3;
        private final Integer orientation;

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            orientation = getResources().getConfiguration().orientation;
        }

        @Override
        public Fragment getItem(int position) {
            if (orientation.equals(Configuration.ORIENTATION_PORTRAIT)) {
                switch (position) {
                    case 0:
                        return new SunFragment();
                    case 1:
                        return new MoonFragment();
                    case 2:
                        return new WeatherFragment();
                    case 3:
                        return new ForecastFragment();
                    default:
                        return null;
                }
            } else {
                // if orientation == Configuration.ORIENTATION_LANDSCAPE
                switch (position) {
                    case 0:
                        return new SunMoonConnector();
                    case 1:
                        return new WeatherFragment();
                    case 2:
                        return new ForecastFragment();
                    default:
                        return null;
                }
            }
        }

        @Override
        public int getCount() {
            //Log.d("ORIENTATION: ", orientation.toString());
            if (orientation.equals(Configuration.ORIENTATION_PORTRAIT)) {
                return PORTRAIT_TAB_COUNT;
            } else {
                // if orientation == Configuration.ORIENTATION_LANDSCAPE
                return LANDSCAPE_TAB_COUNT;
            }
        }
    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) Objects.requireNonNull(getApplicationContext()).getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}