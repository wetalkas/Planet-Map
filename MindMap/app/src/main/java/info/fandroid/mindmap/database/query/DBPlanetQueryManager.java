package info.fandroid.mindmap.database.query;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import info.fandroid.mindmap.database.DBHelper;
import info.fandroid.mindmap.model.ModelPlanet;

/**
 * Created by Vitaly on 09.01.2016.
 */
public class DBPlanetQueryManager extends DBQueryManager {

    public DBPlanetQueryManager(SQLiteDatabase database) {
        super(database);
    }

    public ModelPlanet getById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.PLANET_TABLE, null, DBHelper.MAP_ID_COLUMN,
                new String[]{Long.toString(id)}, null, null, null);

        ModelPlanet planetModel = null;

        if (cursor.moveToFirst()) {
            planetModel = fillPlanetModel(cursor);
        }
        cursor.close();

        return planetModel;
    }


    public List<ModelPlanet> getSelection(String selection, String[] selectionArgs, String orderBy) {
        Cursor cursor = mDatabase.query(DBHelper.PLANET_TABLE, null, selection, selectionArgs,
                null, null, orderBy);

        List<ModelPlanet> planets = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                ModelPlanet planetModel = fillPlanetModel(cursor);
                planets.add(planetModel);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return planets;
    }

    public List<ModelPlanet> getDefaultSelection(long parentId, String parentPath) {
        int pathLength = parentPath.length() + 1;

        List<ModelPlanet> planets = getSelection(DBHelper.SELECTION_PLANET_PARENT_ID,
                new String[]{Long.toString(parentId)}, DBHelper.PLANET_PATH_COLUMN);
        Log.d("length selection", "planets size " + planets.size());

        if (planets.size() != 0) {
            for (ModelPlanet planet : planets) {
                Log.d("length selection", "mPath " + planet.getPath());
            }
        }

        return planets;
    }
}
