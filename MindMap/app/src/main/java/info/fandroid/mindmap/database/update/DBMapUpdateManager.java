package info.fandroid.mindmap.database.update;

import android.database.sqlite.SQLiteDatabase;

import info.fandroid.mindmap.database.DBHelper;
import info.fandroid.mindmap.model.ModelMap;

/**
 * Created by Vitaly on 09.01.2016.
 */
public class DBMapUpdateManager extends DBUpdateManager{


    public DBMapUpdateManager(SQLiteDatabase database) {
        super(database);
    }

    public void id(long id, long newId) {
        update(DBHelper.MAP_TABLE, DBHelper.MAP_ID_COLUMN, id, newId);
    }

    public void name(long id, String name) {
        update(DBHelper.MAP_TABLE, DBHelper.MAP_NAME_COLUMN, id, name);
    }

    public void description(long id, String description) {
        update(DBHelper.MAP_TABLE, DBHelper.MAP_DESCRIPTION_COLUMN, id, description);
    }

    public void lastChanged(long id, long lastChanged) {
        update(DBHelper.MAP_TABLE, DBHelper.MAP_LAST_CHANGED_COLUMN, id, lastChanged);
    }

    public void planetsCount(long id, int planetsCount) {
        update(DBHelper.MAP_TABLE, DBHelper.MAP_PLANETS_COUNT_COLUMN, id, planetsCount);
    }

    public void color(long id, int color) {
        update(DBHelper.MAP_TABLE, DBHelper.MAP_COLOR_COLUMN, id, color);
    }

    public void subject(long id, String subject) {
        update(DBHelper.MAP_TABLE, DBHelper.MAP_SUBJECT_COLUMN, id, subject);
    }

    public void full(long id, ModelMap map) {
        name(id, map.getName());
        description(id, map.getDescription());
        lastChanged(id, map.getLastModified());
        color(id, map.getColor());
        subject(id, map.getSubject().name());
    }

    public void reallyFull(long id, ModelMap map) {
        full(id, map);

        id(id, map.getId());
    }

}
