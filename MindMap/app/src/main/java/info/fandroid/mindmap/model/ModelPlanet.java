package info.fandroid.mindmap.model;

import android.os.Bundle;

import info.fandroid.mindmap.database.DBHelper;

/**
 * Created by Vitaly on 09.01.2016.
 */
public class ModelPlanet {

    private long id;
    private long rootId;
    private String path;
    private long parentId;
    private String name;
    private String description;
    private int color;

    public ModelPlanet() {

    }

    public ModelPlanet(long root_id, String name, String description, int color) {
        this.rootId = root_id;
        this.name = name;
        this.description = description;
        this.color = color;
    }


    public static Bundle toBundle(ModelPlanet planet) {
        Bundle bundle = new Bundle();

        bundle.putLong(DBHelper.PLANET_ID_COLUMN, planet.getId());
        bundle.putLong(DBHelper.PLANET_ROOT_ID_COLUMN, planet.getRootId());
        bundle.putString(DBHelper.PLANET_PATH_COLUMN, planet.getPath());
        bundle.putString(DBHelper.PLANET_NAME_COLUMN, planet.getName());
        bundle.putString(DBHelper.PLANET_DESCRIPTION_COLUMN, planet.getDescription());
        bundle.putInt(DBHelper.PLANET_COLOR_COLUMN, planet.getColor());

        return bundle;
    }


    public static Bundle mapToBundle(ModelMap map) {
        Bundle bundle = new Bundle();

        bundle.putLong(DBHelper.PLANET_ID_COLUMN, map.getId());
        bundle.putLong(DBHelper.PLANET_ROOT_ID_COLUMN, 0);
        bundle.putString(DBHelper.PLANET_PATH_COLUMN, "");
        bundle.putString(DBHelper.PLANET_NAME_COLUMN, map.getName());
        bundle.putString(DBHelper.PLANET_DESCRIPTION_COLUMN, map.getDescription());
        bundle.putInt(DBHelper.PLANET_COLOR_COLUMN, map.getColor());

        return bundle;
    }


    public static ModelPlanet valueOf(Bundle bundle) {
        ModelPlanet planet = new ModelPlanet();

        planet.setId(bundle.getLong(DBHelper.PLANET_ID_COLUMN));
        planet.setRootId(bundle.getLong(DBHelper.PLANET_ROOT_ID_COLUMN));
        planet.setPath(bundle.getString(DBHelper.PLANET_PATH_COLUMN));
        planet.setName(bundle.getString(DBHelper.PLANET_NAME_COLUMN));
        planet.setDescription(bundle.getString(DBHelper.PLANET_DESCRIPTION_COLUMN));
        planet.setColor(bundle.getInt(DBHelper.PLANET_COLOR_COLUMN));

        return planet;
    }




    public long getRootId() {
        return rootId;
    }

    public void setRootId(long root_id) {
        this.rootId = root_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
}
