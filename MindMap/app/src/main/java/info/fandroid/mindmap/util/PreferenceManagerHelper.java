package info.fandroid.mindmap.util;

import android.content.Context;
import android.content.SharedPreferences;

import info.fandroid.mindmap.R;

/**
 * Created by Vitaly on 24.11.2015.
 */
public class PreferenceManagerHelper {

    private static PreferenceManagerHelper instance;

    private Context mContext;


    private PreferenceManagerHelper() {

    }

    public static PreferenceManagerHelper getInstance() {

        if (instance == null) {
            instance = new PreferenceManagerHelper();
        }

        return instance;
    }


    public void init(Context context){
        this.mContext = context;
    }


    public String getString(String key) {

        return mContext.getSharedPreferences(mContext.getString(R.string.app_name), Context.MODE_PRIVATE).getString(key, "");

    }

    public void putString(String key, String value) {
        SharedPreferences preferences = mContext.getSharedPreferences(mContext.getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(key, value);
        editor.apply();
    }


    public boolean getBoolean(String key) {
        return mContext.getSharedPreferences(mContext.getString(R.string.app_name), Context.MODE_PRIVATE).getBoolean(key, false);
    }


    public void putBoolean(String key, boolean value) {
        SharedPreferences preferences = mContext.getSharedPreferences(mContext.getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(key, value);
        editor.apply();
    }

}
