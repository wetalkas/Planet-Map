package info.fandroid.mindmap.util;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.Toolbar;

import info.fandroid.mindmap.R;

/**
 * Created by Vitaly on 27.12.2015.
 */
public class ToolbarHelper {
    public static void setMainToolbar(Toolbar toolbar) {
        Context context = toolbar.getContext();

        toolbar.setTitle(toolbar.getContext().getString(R.string.app_name));

        int titleTextColor;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            titleTextColor = context.getColor(R.color.colorWhiteBackground);
        } else {
            titleTextColor = context.getResources().getColor(R.color.colorWhiteBackground);
        }

        toolbar.setTitleTextColor(titleTextColor);

        toolbar.setBackgroundResource(R.color.colorPrimary);

        if (toolbar.getNavigationIcon() != null) {
            toolbar.setNavigationIcon(null);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(4);
        }
    }


    public static void setCreateMindMapToolbar(Toolbar toolbar) {
        Context context = toolbar.getContext();

        toolbar.setTitle(toolbar.getContext().getString(R.string.app_name));

        int titleTextColor;
        int accentColor;
        Drawable doneIcon;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            titleTextColor = context.getColor(R.color.colorTitleOnWhiteBackground);
            accentColor = context.getColor(R.color.colorAccent);
            doneIcon = context.getDrawable(R.drawable.ic_done_black_24dp);
        } else {
            titleTextColor = context.getResources().getColor(R.color.colorTitleOnWhiteBackground);
            accentColor = context.getResources().getColor(R.color.colorAccent);
            doneIcon = context.getResources().getDrawable(R.drawable.ic_done_black_24dp);
        }

        toolbar.setTitleTextColor(titleTextColor);

        toolbar.setBackgroundResource(R.color.colorWhiteBackground);

        if (doneIcon != null) {
            doneIcon.setColorFilter(accentColor, PorterDuff.Mode.SRC_IN);
            toolbar.setNavigationIcon(doneIcon);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(0);
        }
    }
}
