package pl.politechnika.szczesm3.astroweather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;

import pl.politechnika.szczesm3.astroweather.data.Channel;
import pl.politechnika.szczesm3.astroweather.data.Place;
import pl.politechnika.szczesm3.astroweather.entity.Location;
import pl.politechnika.szczesm3.astroweather.repository.LocationRepository;
import pl.politechnika.szczesm3.astroweather.service.Callback;
import pl.politechnika.szczesm3.astroweather.service.YahooWeatherService;

public class FavoriteActivity extends AppCompatActivity implements Callback {

    private EditText cityName;
    private Switch units;
    private LocationsAdapter locationsAdapter;
    private String unit;
    private SharedPreferences sharedPreferences;
    private RecyclerView favs;
    private LocationRepository lr;
    private YahooWeatherService service;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        lr = new LocationRepository(getApplication());
        service = new YahooWeatherService(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        cityName = findViewById(R.id.cityName);
        units = findViewById(R.id.units);
        Button save = findViewById(R.id.save);
        favs = findViewById(R.id.favs);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        favs.setLayoutManager(mLayoutManager);
        favs.setItemAnimator(new DefaultItemAnimator());


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = cityName.getText().toString();
                if (!city.isEmpty()) {
                    service.getLocation(city);
                } else {
                    Toast.makeText(FavoriteActivity.this,"City cannot be blank!", Toast.LENGTH_LONG).show();
                }
            }
        });

        initSwitch(units);

        units.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (units.isChecked()) {
                    units.setText("Imperial");
                    unit = "f";
                } else {
                    units.setText("Metric");
                    unit = "c";
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("units", unit);
                editor.apply();
            }
        });

        new RefreshAsyncTask(getApplicationContext(), lr, this.locationsAdapter, favs).execute();
    }

    @SuppressLint("SetTextI18n")
    private void initSwitch(Switch units) {
        if (units.isChecked()) {
            units.setText("Imperial");
            unit = "f";
        } else {
            units.setText("Metric");
            unit = "c";
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("units", unit);
        editor.apply();
    }

    private void insertLocation(final Location location) {
        try {
            lr.insert(location);
            new RefreshAsyncTask(getApplicationContext(), lr, this.locationsAdapter, favs).execute();
        } catch (Exception e) {
            Log.d("EXCEPTION: ", e.getMessage());
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class RefreshAsyncTask extends AsyncTask<Void, Void, List> {

        private Context context;
        private LocationRepository locationRepository;
        private LocationsAdapter locationsAdapter;
        private RecyclerView recyclerView;

        RefreshAsyncTask(Context context,
                         LocationRepository locationRepository,
                         LocationsAdapter locationsAdapter,
                         RecyclerView listView) {
            this.context = context;
            this.locationRepository = locationRepository;
            this.locationsAdapter = locationsAdapter;
            this.recyclerView = listView;
        }

        @Override
        protected List doInBackground(Void... params) {
            return locationRepository.getAllLocations();
        }

        @Override
        protected void onPostExecute(List locations) {
            if (locationsAdapter == null) {
                locationsAdapter = new LocationsAdapter(locations, context);
                recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(locationsAdapter);
            } else {
                locationsAdapter.setItems(locations);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        locationsAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }

    @Override
    public void callbackSuccess(JSONObject json) {
        Place place = new Place();
        place.crawl(json);
        Location location = new Location();
        location.setName(place.name);
        location.setLatitude(place.centroid.latitude);
        location.setLongitude(place.centroid.longitude);
        location.setWoeid(place.woeid);
        location.setUnit(sharedPreferences.getString("units", "f"));
        insertLocation(location);
    }

    @Override
    public void callbackFailure(Exception e) {
        Toast.makeText(FavoriteActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
    }
}
