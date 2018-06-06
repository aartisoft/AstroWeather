package pl.politechnika.szczesm3.astroweather.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import pl.politechnika.szczesm3.astroweather.dao.LocationDao;
import pl.politechnika.szczesm3.astroweather.entity.Location;

@Database(entities = {Location.class}, version = 1, exportSchema = false)
public abstract class AstroRoomDatabase extends RoomDatabase {

    public abstract LocationDao locationDao();
    private static AstroRoomDatabase INSTANCE;

    public static AstroRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AstroRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AstroRoomDatabase.class, "location")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
