package info.fandroid.mindmap.database.save;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Calendar;

import info.fandroid.mindmap.database.DBHelper;
import info.fandroid.mindmap.model.ModelMap;
import info.fandroid.mindmap.model.ModelPlanet;

/**
 * Created by Vitaly on 09.01.2016.
 */
public class DBPlanetSaveManager extends DBSaveManager {

    public DBPlanetSaveManager(SQLiteDatabase dp) {
        super(dp);
    }

    public void full(ModelPlanet planet) {
        ContentValues newValues = new ContentValues();

        newValues.put(DBHelper.PLANET_ID_COLUMN, planet.getId());
        newValues.put(DBHelper.PLANET_ROOT_ID_COLUMN, planet.getRootId());
        newValues.put(DBHelper.PLANET_PATH_COLUMN, planet.getPath());
        newValues.put(DBHelper.PLANET_PARENT_ID, planet.getParentId());
        newValues.put(DBHelper.PLANET_NAME_COLUMN, planet.getName());
        newValues.put(DBHelper.PLANET_DESCRIPTION_COLUMN, planet.getDescription());
        newValues.put(DBHelper.PLANET_COLOR_COLUMN, planet.getColor());
        Log.d("db save", "planet root id " + planet.getRootId());

        ModelMap modelMap = mDBManager.query().map().getById(planet.getRootId());
        mDBManager.update().map().planetsCount(planet.getRootId(), modelMap.getPlanetsCount() + 1);
        mDBManager.update().map().lastChanged(planet.getRootId(), Calendar.getInstance().getTimeInMillis());

        mDataBase.insert(DBHelper.PLANET_TABLE, null, newValues);
    }
}
