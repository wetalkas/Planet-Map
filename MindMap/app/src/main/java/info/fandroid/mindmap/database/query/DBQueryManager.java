package info.fandroid.mindmap.database.query;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import info.fandroid.mindmap.database.DBHelper;
import info.fandroid.mindmap.database.DBManager;
import info.fandroid.mindmap.model.ModelMap;
import info.fandroid.mindmap.model.ModelPlanet;

/**
 * Created by Vitaly on 09.01.2016.
 */
public class DBQueryManager {

    protected SQLiteDatabase mDatabase;

    protected DBManager mDBManager;

    public DBQueryManager(SQLiteDatabase database) {
        this.mDatabase = database;
        mDBManager = DBManager.getInstance();
    }




    public static ModelMap fillMapModel(Cursor cursor) {

        long id = cursor.getLong(cursor.getColumnIndex(DBHelper.MAP_ID_COLUMN));
        String name = cursor.getString(cursor.getColumnIndex(DBHelper.MAP_NAME_COLUMN));
        String description = cursor.getString(cursor.getColumnIndex(DBHelper.MAP_DESCRIPTION_COLUMN));
        long lastChanged = cursor.getLong(cursor.getColumnIndex(DBHelper.MAP_LAST_CHANGED_COLUMN));
        int planetsCount = cursor.getInt(cursor.getColumnIndex(DBHelper.MAP_PLANETS_COUNT_COLUMN));
        int color = cursor.getInt(cursor.getColumnIndex(DBHelper.MAP_COLOR_COLUMN));
        String subject = cursor.getString(cursor.getColumnIndex(DBHelper.MAP_SUBJECT_COLUMN));

        ModelMap model = new ModelMap();

        model.setId(id);
        model.setName(name);
        model.setDescription(description);
        model.setLastModified(lastChanged);
        model.setPlanetsCount(planetsCount);
        model.setColor(color);
        model.setSubject(ModelMap.Subject.valueOf(subject));

        return model;
    }


    public static ModelPlanet fillPlanetModel(Cursor cursor) {

        long id = cursor.getLong(cursor.getColumnIndex(DBHelper.PLANET_ID_COLUMN));
        long rootId = cursor.getLong(cursor.getColumnIndex(DBHelper.PLANET_ROOT_ID_COLUMN));
        String path = cursor.getString(cursor.getColumnIndex(DBHelper.PLANET_PATH_COLUMN));
        long parentId = cursor.getLong(cursor.getColumnIndex(DBHelper.PLANET_PARENT_ID));
        String name = cursor.getString(cursor.getColumnIndex(DBHelper.PLANET_NAME_COLUMN));
        String description = cursor.getString(cursor.getColumnIndex(DBHelper.PLANET_DESCRIPTION_COLUMN));
        int color = cursor.getInt(cursor.getColumnIndex(DBHelper.PLANET_COLOR_COLUMN));

        ModelPlanet model = new ModelPlanet();

        model.setId(id);
        model.setRootId(rootId);
        model.setPath(path);
        model.setParentId(parentId);
        model.setName(name);
        model.setDescription(description);
        model.setColor(color);

        return model;
    }
}