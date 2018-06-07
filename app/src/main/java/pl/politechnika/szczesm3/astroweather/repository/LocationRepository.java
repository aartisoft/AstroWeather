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

    public LocationRepository(Application application) {
        AstroRoomDatabase db = AstroRoomDatabase.getDatabase(application);
        locationDao = db.locationDao();
    }

    public void insert (Location location) {
        new insertAsyncTask(locationDao).execute(location);
    }

    public void deleteAll() {
        new deleteAllLocationsAsyncTask(locationDao).execute();
    }

    public List<Location> getAllLocations() {
        return locationDao.getAllLocations();
    }

    private static class deleteAllLocationsAsyncTask extends AsyncTask<Void, Void, Void> {
        private LocationDao mAsyncTaskDao;

        deleteAllLocationsAsyncTask(LocationDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
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
