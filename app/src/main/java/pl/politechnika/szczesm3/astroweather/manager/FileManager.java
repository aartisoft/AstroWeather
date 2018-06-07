package pl.politechnika.szczesm3.astroweather.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import pl.politechnika.szczesm3.astroweather.config.AppConfig;
import pl.politechnika.szczesm3.astroweather.data.Channel;

public class FileManager {

    private Context context;

    public boolean exists(String fileName){
        File file = new File(context.getFilesDir(), fileName);
        return file.exists();
    }

    public FileManager(Context context) {
        this.context = context;
    }

    public JSONObject readForecastFromFile(String fileName) {
        try {
            File file = new File(context.getFilesDir(), fileName);
            FileInputStream fis = new FileInputStream(file);
            byte[] jsonBytes = new byte[(int)file.length()];
            fis.read(jsonBytes);
            fis.close();
            String jsonData = new String(jsonBytes, "UTF-8");
            JSONObject data = new JSONObject(jsonData);

            return data;
        } catch (Exception e) {
            return null;
        }
    }

    public void saveForecastToFile(String fileName, JSONObject json) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(json.toString().getBytes());
            fos.close();
        } catch (Exception e) {
            Log.d("EXCEPTION: ", e.getMessage());
        }
    }

    public boolean isForecastUpToDate(String fileName) {
        File file = new File(context.getFilesDir(), fileName);
        return file.exists() && System.currentTimeMillis() - file.lastModified() < AppConfig.MAX_WEATHER_INFO_AGE;
    }

}
