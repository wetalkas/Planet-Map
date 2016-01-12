package info.fandroid.mindmap.ui;

import info.fandroid.mindmap.database.DBManager;

/**
 * Created by Vitaly on 09.01.2016.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.getInstance().init(getApplicationContext());
    }


}
