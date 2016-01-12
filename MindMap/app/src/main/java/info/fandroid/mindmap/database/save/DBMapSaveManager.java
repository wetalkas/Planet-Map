package info.fandroid.mindmap.database.save;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import info.fandroid.mindmap.database.DBHelper;
import info.fandroid.mindmap.model.ModelMap;

/**
 * Created by Vitaly on 09.01.2016.
 */
public class DBMapSaveManager extends DBSaveManager {

    public DBMapSaveManager(SQLiteDatabase dp) {
        super(dp);
    }

    public void full(ModelMap map) {
        ContentValues newValues = new ContentValues();

        newValues.put(DBHelper.MAP_ID_COLUMN, map.getId());
        newValues.put(DBHelper.MAP_NAME_COLUMN, map.getName());
        newValues.put(DBHelper.MAP_DESCRIPTION_COLUMN, map.getDescription());
        newValues.put(DBHelper.MAP_LAST_CHANGED_COLUMN, map.getLastModified());
        newValues.put(DBHelper.MAP_PLANETS_COUNT_COLUMN, map.getPlanetsCount());
        newValues.put(DBHelper.MAP_COLOR_COLUMN, map.getColor());
        newValues.put(DBHelper.MAP_SUBJECT_COLUMN, map.getSubject().name());

        mDataBase.insert(DBHelper.MAP_TABLE, null, newValues);
    }
}
