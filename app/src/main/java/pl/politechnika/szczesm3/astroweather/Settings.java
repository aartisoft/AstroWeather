package pl.politechnika.szczesm3.astroweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;

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
        DecimalFormat df = new DecimalFormat("#.######");
        df.setRoundingMode(RoundingMode.UP);
        String latT = lat.getText().toString();
        String lonT = lon.getText().toString();
        Integer refresh = Integer.valueOf(freq.getText().toString());
        if(!latT.isEmpty()){
            if(df.format(latT).matches(LATITUDE_PATTERN)){
                isUpdated = true;
                AppConfig.getInstance().setLatitude(Float.parseFloat(latT));
            } else {
                Toast.makeText(Settings.this,"Incorrect latitude!", Toast.LENGTH_SHORT).show();
            }
        }
        if(!lonT.isEmpty()){
            if(df.format(lonT).matches(LONGITUDE_PATTERN)){
                isUpdated = true;
                AppConfig.getInstance().setLongtitude(Float.parseFloat(lonT));
            } else {
                Toast.makeText(Settings.this,"Incorrect longitude!", Toast.LENGTH_SHORT).show();

            }
        }
        if(refresh > 0){
            isUpdated = true;
            AppConfig.getInstance().setRefreshInterval(refresh);
        }
        //TODO: remove
        System.out.println(AppConfig.getInstance());
        if(isUpdated){
            Toast.makeText(Settings.this,"Settings saved!", Toast.LENGTH_SHORT).show();
        } else {
            // nothing to do here;
        }
    }
}
