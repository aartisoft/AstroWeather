package pl.politechnika.szczesm3.astroweather;

import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.math.RoundingMode;

import pl.politechnika.szczesm3.astroweather.config.*;

public class Settings extends AppCompatActivity {

    EditText lat, lon, freq;
    protected static final String LATITUDE_PATTERN="^(\\+|-)?(?:90(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,6})?))$";
    protected static final String LONGITUDE_PATTERN="^(\\+|-)?(?:180(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,6})?))$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        lat = findViewById(R.id.latitude);
        lon = findViewById(R.id.longitude);
        freq = findViewById(R.id.refreshTime);
    }

    public void saveConfig(View v){
        Boolean isUpdated = false;
        String latT = lat.getText().toString();
        String lonT = lon.getText().toString();
        Integer refresh = new Integer(0);
        if(!freq.getText().toString().isEmpty())
            refresh = Integer.valueOf(freq.getText().toString());
        if(!latT.isEmpty()){
            if(latT.matches(LATITUDE_PATTERN)){
                isUpdated = true;
                AppConfig.getInstance().setLatitude(Float.parseFloat(latT));
            } else {
                Toast.makeText(Settings.this,"Incorrect latitude!", Toast.LENGTH_LONG).show();
            }
        }
        if(!lonT.isEmpty()){
            if(lonT.matches(LONGITUDE_PATTERN)){
                isUpdated = true;
                AppConfig.getInstance().setLongtitude(Float.parseFloat(lonT));
            } else {
                Toast.makeText(Settings.this,"Incorrect longitude!", Toast.LENGTH_LONG).show();

            }
        }
        if(refresh > 0){
            isUpdated = true;
            AppConfig.getInstance().setRefreshInterval(refresh);
        }
        //TODO: remove
        Log.d("CORRENT/APPCONFIG", AppConfig.getInstance().toString());
        if(isUpdated){
            Toast.makeText(Settings.this,"Settings saved!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(v.getContext(), MainActivity.class);
            startActivity(intent);
        } else {
            // nothing to do here;
            Toast.makeText(Settings.this,"Enter some values!", Toast.LENGTH_LONG).show();
        }
    }
}
