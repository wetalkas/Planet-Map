package info.fandroid.mindmap.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import info.fandroid.mindmap.R;
import info.fandroid.mindmap.database.DBHelper;
import info.fandroid.mindmap.util.ColorManager;

/**
 * Created by Vitaly on 24.11.2015.
 */
public class ModelMap {

    private long id;
    private String name;
    private String description;
    private int color;
    private Subject subject;
    private int planetsCount;
    private long lastModified;

    public ModelMap() {
        this.subject = Subject.Other;
        this.planetsCount = 1;
    }


    public enum Subject {
        Work, Home, Me, Education, Travel, Art, Other;

        public Drawable getSmallIcon(Context context) {
            Drawable drawable;
            switch (this) {
                case Home:
                    drawable = context.getResources().getDrawable(R.drawable.ic_home_black_24dp);
                    return drawable;
                case Work:
                    drawable = context.getResources().getDrawable(R.drawable.ic_work_black_24dp);
                    return drawable;
                case Other:
                    drawable = context.getResources().getDrawable(R.drawable.ic_extension_black_24dp);
                    return drawable;
                case Me:
                    drawable = context.getResources().getDrawable(R.drawable.ic_accessibility_black_24dp);
                    return drawable;
                case Education:
                    drawable = context.getResources().getDrawable(R.drawable.ic_school_black_24dp);
                    return drawable;
                case Travel:
                    drawable = context.getResources().getDrawable(R.drawable.ic_room_black_24dp);
                    return drawable;
                case Art:
                    drawable = context.getResources().getDrawable(R.drawable.ic_theaters_black_24dp);
                    return drawable;
                default:
                    return null;
            }
        }


        public Drawable getBigIcon(Context context) {
            Drawable drawable;
            switch (this) {
                case Home:
                    drawable = context.getResources().getDrawable(R.drawable.ic_home_black_36dp);
                    return drawable;
                case Work:
                    drawable = context.getResources().getDrawable(R.drawable.ic_work_black_36dp);
                    return drawable;
                case Other:
                    drawable = context.getResources().getDrawable(R.drawable.ic_extension_black_36dp);
                    return drawable;
                case Me:
                    drawable = context.getResources().getDrawable(R.drawable.ic_accessibility_black_36dp);
                    return drawable;
                case Education:
                    drawable = context.getResources().getDrawable(R.drawable.ic_school_black_36dp);
                    return drawable;
                case Travel:
                    drawable = context.getResources().getDrawable(R.drawable.ic_room_black_36dp);
                    return drawable;
                case Art:
                    drawable = context.getResources().getDrawable(R.drawable.ic_theaters_black_36dp);
                    return drawable;
                default:
                    return null;
            }

        }

    }


    public static Bundle toBundle(ModelMap map) {
        Bundle bundle = new Bundle();

        bundle.putLong(DBHelper.MAP_ID_COLUMN, map.getId());
        bundle.putString(DBHelper.MAP_NAME_COLUMN, map.getName());
        bundle.putString(DBHelper.MAP_DESCRIPTION_COLUMN, map.getDescription());
        bundle.putLong(DBHelper.MAP_LAST_CHANGED_COLUMN, map.getLastModified());
        bundle.putInt(DBHelper.MAP_PLANETS_COUNT_COLUMN, map.getPlanetsCount());
        bundle.putInt(DBHelper.MAP_COLOR_COLUMN, map.getColor());
        bundle.putString(DBHelper.MAP_SUBJECT_COLUMN, map.getSubject().name());

        return bundle;
    }


    public static ModelMap valueOf(Bundle bundle) {
        ModelMap map = new ModelMap();

        map.setId(bundle.getLong(DBHelper.MAP_ID_COLUMN));
        map.setName(bundle.getString(DBHelper.MAP_NAME_COLUMN));
        map.setDescription(bundle.getString(DBHelper.MAP_DESCRIPTION_COLUMN));
        map.setLastModified(bundle.getLong(DBHelper.MAP_LAST_CHANGED_COLUMN));
        map.setPlanetsCount(bundle.getInt(DBHelper.MAP_PLANETS_COUNT_COLUMN));
        map.setColor(bundle.getInt(DBHelper.MAP_COLOR_COLUMN));
        map.setSubject(Subject.valueOf(bundle.getString(DBHelper.MAP_SUBJECT_COLUMN)));

        return map;
    }

    public static int parseColorGrid(int position) {
        return ColorManager.color[position];
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getPlanetsCount() {
        return planetsCount;
    }

    public void setPlanetsCount(int planetsCount) {
        this.planetsCount = planetsCount;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
