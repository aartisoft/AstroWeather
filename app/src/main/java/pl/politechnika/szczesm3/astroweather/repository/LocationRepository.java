package pl.politechnika.szczesm3.astroweather.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import pl.politechnika.szczesm3.astroweather.dao.LocationDao;
import pl.politechnika.szczesm3.astroweather.database.AstroRoomDatabase;
import pl.politechnika.szczesm3.astroweather.entity.Location;

public class LocationRepository {
    private LocationDao locationDao;
    private List<Location> allLocations;

    public LocationRepository(Application application) {
        AstroRoomDatabase db = AstroRoomDatabase.getDatabase(application);
        locationDao = db.locationDao();
        allLocations = locationDao.getAllLocations();
    }

    public List<Location> getAllLocations() {
        return allLocations;
    }

    public void insert (Location location) {
        new insertAsyncTask(locationDao).execute(location);
    }

    private static class insertAsyncTask extends AsyncTask<Location, Void, Void> {

        private LocationDao asyncTaskDao;

        insertAsyncTask(LocationDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Location... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
