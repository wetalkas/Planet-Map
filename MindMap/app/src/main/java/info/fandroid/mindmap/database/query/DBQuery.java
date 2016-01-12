package info.fandroid.mindmap.database.query;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Vitaly on 09.01.2016.
 */
public class DBQuery {
    private DBMapQueryManager mMapQueryManager;
    private DBPlanetQueryManager mPlanetQueryManager;

    public DBQuery(SQLiteDatabase db) {
        mMapQueryManager = new DBMapQueryManager(db);
        mPlanetQueryManager = new DBPlanetQueryManager(db);
    }

    public DBMapQueryManager map() {
        if (mMapQueryManager == null) {

            Log.d("if null", "wtf");
        }
        return mMapQueryManager;
    }

    public DBPlanetQueryManager planet() {
        return mPlanetQueryManager;
    }
}
