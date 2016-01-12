package info.fandroid.mindmap.database.save;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Vitaly on 09.01.2016.
 */
public class DBSaver {
    private DBMapSaveManager mMapSaveManager;
    private DBPlanetSaveManager mPlanetSaveManager;

    public DBSaver(SQLiteDatabase db) {
        mMapSaveManager = new DBMapSaveManager(db);
        mPlanetSaveManager = new DBPlanetSaveManager(db);
    }

    public DBMapSaveManager map() {
        return mMapSaveManager;
    }

    public DBPlanetSaveManager planet() {
        return mPlanetSaveManager;
    }
}
