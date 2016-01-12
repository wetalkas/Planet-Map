package info.fandroid.mindmap.database.update;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Vitaly on 09.01.2016.
 */
public class DBUpdater {
    private DBMapUpdateManager mMapUpdateManager;
    private DBPlanetUpdateManager mPlanetUpdateManager;

    public DBUpdater(SQLiteDatabase db) {
        mMapUpdateManager = new DBMapUpdateManager(db);
        mPlanetUpdateManager = new DBPlanetUpdateManager(db);
    }

    public DBMapUpdateManager map() {
        return mMapUpdateManager;
    }

    public DBPlanetUpdateManager planet() {
        return mPlanetUpdateManager;
    }
}
