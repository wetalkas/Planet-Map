package info.fandroid.mindmap.database.update;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import info.fandroid.mindmap.database.DBHelper;
import info.fandroid.mindmap.database.DBManager;

/**
 * Created by Vitaly on 09.01.2016.
 */
public class DBUpdateManager {

    SQLiteDatabase mDatabase;

    protected DBManager mDBManager;

    public DBUpdateManager(SQLiteDatabase database) {
        this.mDatabase = database;
        mDBManager = DBManager.getInstance();
    }

    protected void update(String table, String column, long key, String value) {
        ContentValues cv = new ContentValues();
        cv.put(column, value);
        mDatabase.update(table, cv, getKeyColumn(table) + " = ?", new String[]{Long.toString(key)});
    }

    protected void update (String table, String column, long key, long value) {
        ContentValues cv = new ContentValues();
        cv.put(column, value);
        mDatabase.update(table, cv, getKeyColumn(table) + " = ?", new String[]{Long.toString(key)});
    }

    protected void update (String table, String column, long key, int value) {
        ContentValues cv = new ContentValues();
        cv.put(column, value);
        mDatabase.update(table, cv, getKeyColumn(table) + " = ?", new String[]{Long.toString(key)});
    }

    protected String getKeyColumn(String table) {
        String keyColumn = null;
        if (table.equals(DBHelper.MAP_TABLE)) {
            keyColumn = DBHelper.MAP_ID_COLUMN;
        } else  if (table.equals(DBHelper.PLANET_TABLE)){
            keyColumn = DBHelper.PLANET_ID_COLUMN;
        }
        return keyColumn;
    }
}
