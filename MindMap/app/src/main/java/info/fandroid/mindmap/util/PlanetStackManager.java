package info.fandroid.mindmap.util;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vitaly on 08.01.2016.
 */
public class PlanetStackManager {

    private static PlanetStackManager instance;


    private List<View> planetStack;


    private PlanetStackManager() {
    }


    public static PlanetStackManager getInstance() {
        if (instance == null) {
            instance = new PlanetStackManager();
        }
        return instance;
    }


    public void init() {
        this.planetStack = new ArrayList<>();
    }


    public void addView(View planet) {
        this.planetStack.add(planet);
    }


    public View getParentView() {
        if (planetStack.size() != 0) {
            planetStack.remove(planetStack.size() - 1);
            if (planetStack.size() != 0) {
                return planetStack.get(planetStack.size() - 1);
            }
        }
        return null;
    }
}
