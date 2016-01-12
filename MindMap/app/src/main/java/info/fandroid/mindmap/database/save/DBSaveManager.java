package info.fandroid.mindmap.database.save;

import android.database.sqlite.SQLiteDatabase;

import info.fandroid.mindmap.database.DBManager;

/**
 * Created by Vitaly on 09.01.2016.
 */
public class DBSaveManager {

    protected SQLiteDatabase mDataBase;

    protected DBManager mDBManager;

    public DBSaveManager(SQLiteDatabase dp) {
        mDataBase = dp;
        mDBManager = DBManager.getInstance();
    }



}
