package info.fandroid.mindmap.util;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;

/**
 * Created by Vitaly on 24.11.2015.
 */
public class FragmentManagerHelper {

    private static FragmentManagerHelper instance;

    private FragmentManager fragmentManager;


    private FragmentManagerHelper() {

    }

    public static FragmentManagerHelper getInstance() {

        if (instance == null) {
            instance = new FragmentManagerHelper();
        }

        return instance;
    }


    public void init(Activity activity){
        fragmentManager = activity.getFragmentManager();
    }


    public void setFragment(int containerViewId, Fragment fragment, boolean addToBackStack) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerViewId, fragment, String.valueOf(fragment.getClass()));

        Log.d("tag", "class name " + String.valueOf(fragment.getClass()));
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
    }


    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }
}
