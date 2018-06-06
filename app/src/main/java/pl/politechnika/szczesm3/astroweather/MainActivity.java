package pl.politechnika.szczesm3.astroweather;

import android.content.Intent;
import android.content.res.Configuration;
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

import pl.politechnika.szczesm3.astroweather.config.AppConfig;
import pl.politechnika.szczesm3.astroweather.fragment.ForecastFragment;
import pl.politechnika.szczesm3.astroweather.fragment.MoonFragment;
import pl.politechnika.szczesm3.astroweather.fragment.SunFragment;
import pl.politechnika.szczesm3.astroweather.fragment.SunMoonConnector;
import pl.politechnika.szczesm3.astroweather.fragment.WeatherFragment;
import pl.politechnika.szczesm3.astroweather.repository.LocationRepository;

public class MainActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_main);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
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
}