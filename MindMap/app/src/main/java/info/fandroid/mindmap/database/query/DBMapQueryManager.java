package info.fandroid.mindmap.database.query;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import info.fandroid.mindmap.database.DBHelper;
import info.fandroid.mindmap.model.ModelMap;

/**
 * Created by Vitaly on 09.01.2016.
 */
public class DBMapQueryManager extends DBQueryManager {

    public DBMapQueryManager(SQLiteDatabase database) {
        super(database);
    }


    public ModelMap getById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.MAP_TABLE, null, DBHelper.SELECTION_MAP_ID,
                new String[]{Long.toString(id)}, null, null, null);

        ModelMap modelMap = null;

        if (cursor.moveToFirst()) {
            modelMap = fillMapModel(cursor);
        }
        cursor.close();

        return modelMap;
    }

    public List<ModelMap> getSelection(String selection, String[] selectionArgs, String orderBy) {
        Cursor cursor = mDatabase.query(DBHelper.MAP_TABLE, null, selection, selectionArgs,
                null, null, orderBy);

        List<ModelMap> maps = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                ModelMap mapModel = fillMapModel(cursor);
                maps.add(mapModel);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return maps;
    }


    public List<ModelMap> getDefaultSelection() {
        List<ModelMap> maps = new ArrayList<>();

        maps.addAll(getSelection(null, null, DBHelper.MAP_ID_COLUMN));

        return maps;
    }


}
