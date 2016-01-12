package info.fandroid.mindmap.database.update;

import android.database.sqlite.SQLiteDatabase;

import info.fandroid.mindmap.database.DBHelper;
import info.fandroid.mindmap.model.ModelPlanet;

/**
 * Created by Vitaly on 09.01.2016.
 */
public class DBPlanetUpdateManager extends DBUpdateManager {

    public DBPlanetUpdateManager(SQLiteDatabase database) {
        super(database);
    }

    public void id(long id, long newId) {
        update(DBHelper.PLANET_TABLE, DBHelper.PLANET_ID_COLUMN, id, newId);
    }

    public void rootId(long id, long newRootId) {
        update(DBHelper.PLANET_TABLE, DBHelper.PLANET_ROOT_ID_COLUMN, id, newRootId);
    }

    public void path(long id, String path) {
        update(DBHelper.PLANET_TABLE, DBHelper.PLANET_PATH_COLUMN, id, path);
    }

    public void name(long id, String name) {
        update(DBHelper.PLANET_TABLE, DBHelper.PLANET_NAME_COLUMN, id, name);
    }

    public void description(long id, String description) {
        update(DBHelper.PLANET_TABLE, DBHelper.PLANET_DESCRIPTION_COLUMN, id, description);
    }

    public void color(long id, int color) {
        update(DBHelper.PLANET_TABLE, DBHelper.PLANET_COLOR_COLUMN, id, color);
    }


    public void full(long id, ModelPlanet planet) {
        name(id, planet.getName());
        description(id, planet.getDescription());
        color(id, planet.getColor());
    }


    public void reallyFull(long id, ModelPlanet planet) {
        full(id, planet);

        path(id, planet.getPath());
        rootId(id, planet.getRootId());
        id(id, planet.getId());
    }

}