package pl.politechnika.szczesm3.astroweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

import pl.politechnika.szczesm3.astroweather.config.*;

public class Settings extends AppCompatActivity implements Serializable{

    private EditText lat, lon, freq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        lat = findViewById(R.id.latitude);
        lon = findViewById(R.id.longitude);
        freq = findViewById(R.id.refreshTime);

        Button goToFavs = findViewById(R.id.goToFavs);

        goToFavs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FavoriteActivity.class);
                startActivity(intent);
            }
        });

        if(savedInstanceState != null){
            lat.setText(savedInstanceState.getString("lat"));
            lon.setText((savedInstanceState.getString("lon")));
            freq.setText(savedInstanceState.getString("freq"));
        }

        updateHints();
    }

    private void updateHints() {
        lat.setHint(getCurrentLat());
        lon.setHint(getCurrentLon());
        freq.setHint(getCurrentFreq());
    }

    private String getCurrentLat() {
        return String.valueOf(AppConfig.getInstance().getLatitude());
    }

    private String getCurrentLon() {
        return String.valueOf(AppConfig.getInstance().getLongitude());
    }

    private String getCurrentFreq() {
        return String.valueOf(AppConfig.getInstance().getRefreshInterval());
    }

    public void goToFavorites(View v) {
        Intent intent = new Intent(v.getContext(), FavoriteActivity.class);
        startActivity(intent);
    }

    public void saveConfig(View v){
        Boolean isUpdated = false;
        Boolean errorAlreadySent = false;
        String latT = lat.getText().toString();
        String lonT = lon.getText().toString();
        Integer refresh = 0;
        if(!freq.getText().toString().isEmpty())
            refresh = Integer.valueOf(freq.getText().toString());
        if(!latT.isEmpty()){
            if(-90 <= Double.parseDouble(latT) && Double.parseDouble(latT) <= 90){
                isUpdated = true;
                AppConfig.getInstance().setLatitude(Double.parseDouble(latT));
            } else {
                errorAlreadySent = true;
                Toast.makeText(Settings.this,"Incorrect latitude!", Toast.LENGTH_LONG).show();
            }
        }
        if(!lonT.isEmpty()){
            if(-180 <= Double.parseDouble(lonT) && Double.parseDouble(lonT) <= 180){
                isUpdated = true;
                AppConfig.getInstance().setLongitude(Double.parseDouble(lonT));
            } else {
                errorAlreadySent = true;
                Toast.makeText(Settings.this,"Incorrect longitude!", Toast.LENGTH_LONG).show();

            }
        }
        if(refresh > 0){
            isUpdated = true;
            AppConfig.getInstance().setRefreshInterval(refresh);
        }
        //TODO: remove
        Log.d("CURRENT/APPCONFIG", AppConfig.getInstance().toString());
        if(isUpdated){
            Toast.makeText(Settings.this,"Settings saved!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(v.getContext(), MainActivity.class);
            startActivity(intent);
        } else if (!errorAlreadySent) {
            // nothing to do here;
            Toast.makeText(Settings.this,"Enter some values!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateHints();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("lat", lat.getText().toString());
        outState.putString("lon", lon.getText().toString());
        outState.putString("freq", freq.getText().toString());
    }
}
