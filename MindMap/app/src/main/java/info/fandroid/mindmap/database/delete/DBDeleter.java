package info.fandroid.mindmap.database.delete;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import info.fandroid.mindmap.database.DBHelper;
import info.fandroid.mindmap.database.DBManager;

/**
 * Created by Vitaly on 09.01.2016.
 */
public class DBDeleter {
    private SQLiteDatabase mDataBase;

    protected DBManager mDBManager;

    public DBDeleter(SQLiteDatabase db) {
        mDataBase = db;
        mDBManager = DBManager.getInstance();
    }

    public void map(long id) {
        mDataBase.delete(DBHelper.MAP_TABLE, DBHelper.SELECTION_MAP_ID, new String[]{Long.toString(id)});
        int delete = mDataBase.delete(DBHelper.PLANET_TABLE, DBHelper.SELECTION_PLANET_ROOT_ID, new String[]{Long.toString(id)});
        Log.d("deleted", "size = " + delete);

    }

    public void planet(long id) {
        mDataBase.delete(DBHelper.PLANET_TABLE, DBHelper.SELECTION_PLANET_ID, new String[]{Long.toString(id)});

        mDataBase.delete(DBHelper.PLANET_TABLE, DBHelper.SELECTION_PLANET_PARENT_ID, new String[]{Long.toString(id)});
    }

    public void planetsByRootId(long rootId) {
        mDataBase.delete(DBHelper.PLANET_TABLE, DBHelper.SELECTION_PLANET_ROOT_ID, new String[]{Long.toString(rootId)});
    }

    public void planetsByParentId(long parentId) {
        mDataBase.delete(DBHelper.PLANET_TABLE, DBHelper.SELECTION_PLANET_PARENT_ID, new String[]{Long.toString(parentId)});
    }

}
