package info.fandroid.mindmap.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Vitaly on 24.11.2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mindmap_database";

    public static final String MAP_TABLE = "map_table";
    public static final String PLANET_TABLE = "planet_table";


    public static final String USER_LOGIN_COLUMN = "user_login";
    public static final String MAP_ID_COLUMN = "map_id";
    public static final String MAP_NAME_COLUMN = "map_name";
    public static final String MAP_DESCRIPTION_COLUMN = "map_description";
    public static final String MAP_LAST_CHANGED_COLUMN = "map_last_changed";
    public static final String MAP_PLANETS_COUNT_COLUMN = "map_planets_count";
    public static final String MAP_COLOR_COLUMN = "map_color";
    public static final String MAP_SUBJECT_COLUMN = "map_subject";


    public static final String PLANET_ID_COLUMN = "planet_id";
    public static final String PLANET_ROOT_ID_COLUMN = "planet_root_id";
    public static final String PLANET_PATH_COLUMN = "planet_path";
    public static final String PLANET_PARENT_ID = "planet_parent_id";
    public static final String PLANET_NAME_COLUMN = "planet_name";
    public static final String PLANET_DESCRIPTION_COLUMN = "planet_description";
    public static final String PLANET_COLOR_COLUMN = "planet_color";


    //Selection for query
    public static final String SELECTION_MAP_ID = MAP_ID_COLUMN + " = ?";
    public static final String SELECTION_PLANET_ID = PLANET_ID_COLUMN + " = ?";
    public static final String SELECTION_PLANET_ROOT_ID = PLANET_ROOT_ID_COLUMN + " = ?";
    public static final String SELECTION_PLANET_PARENT_ID = PLANET_PARENT_ID + " = ?";


    private static final String MAP_CREATE_SCRIPT = "create table "
            + MAP_TABLE + " (" + BaseColumns._ID + " integer primary key autoincrement, "
            + MAP_ID_COLUMN + " long, "
            + MAP_NAME_COLUMN + " text, "
            + MAP_DESCRIPTION_COLUMN + " text, "
            + MAP_LAST_CHANGED_COLUMN + " long, "
            + MAP_PLANETS_COUNT_COLUMN + " integer, "
            + MAP_COLOR_COLUMN + " integer, "
            + MAP_SUBJECT_COLUMN + " text);";


    private static final String PLANET_CREATE_SCRIPT = "create table "
            + PLANET_TABLE + " (" + BaseColumns._ID + " integer primary key autoincrement, "
            + PLANET_ID_COLUMN + " long, "
            + PLANET_ROOT_ID_COLUMN + " long, "
            + PLANET_PATH_COLUMN + " text, "
            + PLANET_PARENT_ID + " long, "
            + PLANET_NAME_COLUMN + " text, "
            + PLANET_DESCRIPTION_COLUMN + " text, "
            + PLANET_COLOR_COLUMN + " integer);";





    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MAP_CREATE_SCRIPT);
        db.execSQL(PLANET_CREATE_SCRIPT);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void dropTable() {
        getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + MAP_TABLE);
        getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + PLANET_TABLE);
        onCreate(getWritableDatabase());
    }



}