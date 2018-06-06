package pl.politechnika.szczesm3.astroweather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.politechnika.szczesm3.astroweather.config.AppConfig;
import pl.politechnika.szczesm3.astroweather.entity.Location;
import pl.politechnika.szczesm3.astroweather.repository.LocationRepository;

public class FavoriteActivity extends AppCompatActivity {

    private EditText cityName;
    private Spinner units;
    private HashMap<String, String> availableUnits;
    private String[] favLocations;
    private Button save;
    private ListView favs;
    private LocationRepository lr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        availableUnits = new HashMap<>();
        String[] temperatureOptions = {"Celsius", "Fahrenheit"};
        availableUnits.put(temperatureOptions[0], "f");
        availableUnits.put(temperatureOptions[1], "c");

        cityName = findViewById(R.id.cityName);
        units = findViewById(R.id.units);
        save = findViewById(R.id.save);
        favs = findViewById(R.id.favs);
        fetchFavs();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertNewLocation();
            }
        });

        units.setAdapter(new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, temperatureOptions));
    }

    private void insertNewLocation() {
        Boolean isUpdated = false;
        Boolean errorAlreadySent = false;
        String city = String.valueOf(cityName.getText());

        try {
            if (!"".equals(city.trim()) && city != null) {
                insertLocation(city);
            } else {
                Toast.makeText(FavoriteActivity.this,"City cannot be blank!", Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            Log.d("EXC: ", e.getMessage());
            Toast.makeText(FavoriteActivity.this,"ERROR!", Toast.LENGTH_LONG).show();
        }
    }

    private void insertLocation(final String cityName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Location location = new Location();
                    location.setName(cityName);
                    lr.insert(location);
                    fetchFavs();
                } catch (Exception e) {
                    Log.d("$$$ ", e.getMessage());
                }
            }
        }).start();
    }

    private void fetchFavs() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lr = new LocationRepository(getApplication());
                    List<Location> locations = lr.getAllLocations();
                    favLocations = new String[locations.size()];
                    for (int i = 0; i<locations.size(); i++) {
                        Location location = locations.get(i);
                        favLocations[i] = location.toString();
                    }
                    updateFavs(favLocations);
                } catch (Exception e) {
                    Log.d("$$$ ", e.getMessage());
                }
            }
        }).start();
    }

    private void updateFavs(String[] favLocations) {
        favs.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                favLocations));
    }
}
