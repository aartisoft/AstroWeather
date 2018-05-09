package pl.politechnika.szczesm3.astroweather;

import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.math.RoundingMode;

import pl.politechnika.szczesm3.astroweather.config.*;

public class Settings extends AppCompatActivity implements Serializable{

    EditText lat, lon, freq;
    protected static final String LATITUDE_PATTERN="^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$";
    protected static final String LONGITUDE_PATTERN="^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        lat = findViewById(R.id.latitude);
        lon = findViewById(R.id.longitude);
        freq = findViewById(R.id.refreshTime);

        lat.setHint(getCurrentLat());
        lon.setHint(getCurrentLon());
        freq.setHint(getCurrentFreq());
    }

    public String getCurrentLat() {
        return String.valueOf(AppConfig.getInstance().getLatitude());
    }

    public String getCurrentLon() {
        return String.valueOf(AppConfig.getInstance().getLongtitude());
    }

    public String getCurrentFreq() {
        return String.valueOf(AppConfig.getInstance().getRefreshInterval());
    }

    public void saveConfig(View v){
        Boolean isUpdated = false;
        Boolean errorAlreadySent = false;
        String latT = lat.getText().toString();
        String lonT = lon.getText().toString();
        Integer refresh = new Integer(0);
        if(!freq.getText().toString().isEmpty())
            refresh = Integer.valueOf(freq.getText().toString());
        if(!latT.isEmpty()){
            if(-90 < Double.parseDouble(latT) && Double.parseDouble(latT) < 90){
                isUpdated = true;
                AppConfig.getInstance().setLatitude(Double.parseDouble(latT));
            } else {
                errorAlreadySent = true;
                Toast.makeText(Settings.this,"Incorrect latitude!", Toast.LENGTH_LONG).show();
            }
        }
        if(!lonT.isEmpty()){
            if(-180 < Double.parseDouble(lonT) && Double.parseDouble(lonT) < 180){
                isUpdated = true;
                AppConfig.getInstance().setLongtitude(Double.parseDouble(lonT));
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
        Log.d("CORRENT/APPCONFIG", AppConfig.getInstance().toString());
        if(isUpdated){
            Toast.makeText(Settings.this,"Settings saved!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(v.getContext(), MainActivity.class);
            startActivity(intent);
        } else if (!errorAlreadySent) {
            // nothing to do here;
            Toast.makeText(Settings.this,"Enter some values!", Toast.LENGTH_LONG).show();
        }
    }
}
