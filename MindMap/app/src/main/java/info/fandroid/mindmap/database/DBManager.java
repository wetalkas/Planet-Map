package info.fandroid.mindmap.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import info.fandroid.mindmap.database.delete.DBDeleter;
import info.fandroid.mindmap.database.query.DBQuery;
import info.fandroid.mindmap.database.save.DBSaver;
import info.fandroid.mindmap.database.update.DBUpdater;

/**
 * Created by Vitaly on 09.01.2016.
 */
public class DBManager {

    private DBSaver mDbSaver;
    private DBUpdater mDbUpdater;
    private DBQuery mDbQuery;
    private DBDeleter mDbDeleter;

    private static DBManager instance;
    private DBHelper mDBHelper;
    private SQLiteDatabase mDataBase;

    private DBManager() {

    }

    public static DBManager getInstance() {

        if (instance == null) {
            instance = new DBManager();
        }

        return instance;
    }

    public void init(Context context) {
        mDBHelper = new DBHelper(context);
        mDataBase = mDBHelper.getWritableDatabase();

        mDbSaver = new DBSaver(mDataBase);
        mDbUpdater = new DBUpdater(mDataBase);
        mDbQuery = new DBQuery(mDataBase);
        mDbDeleter = new DBDeleter(mDataBase);
    }


    public DBSaver save() {
        return mDbSaver;
    }

    public DBUpdater update() {
        return mDbUpdater;
    }

    public DBQuery query() {
        return mDbQuery;
    }

    public DBDeleter delete() {
        return mDbDeleter;
    }

    public void dropTable() {
        mDBHelper.dropTable();
    }
}
