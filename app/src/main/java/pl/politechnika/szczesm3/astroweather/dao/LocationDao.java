package pl.politechnika.szczesm3.astroweather.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import pl.politechnika.szczesm3.astroweather.entity.Location;

@Dao
public interface LocationDao {

    @Insert
    void insert(Location location);

    @Query("DELETE FROM location")
    void deleteAll();

    @Query("SELECT * FROM location ORDER BY name ASC")
    List<Location> getAllLocations();

    @Query("SELECT * FROM location WHERE id IN (:id) LIMIT 1")
    Location getById(int id);
}
