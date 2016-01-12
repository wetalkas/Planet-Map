package info.fandroid.mindmap.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Vitaly on 05.01.2016.
 */
public class ViewSizeManager {

    public static void setViewSizeInDp(View view, float newHeightInDp, float newWidthInDp) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        final int height = (int) Utils.dpToPixels(newHeightInDp, view.getContext());
        final int width = (int) Utils.dpToPixels(newWidthInDp, view.getContext());


        layoutParams.height = height;
        layoutParams.width = width;
        view.requestLayout();

    }


    public static void setViewSizeInPx(View view, float newHeightInDp, float newWidthInDp) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();


        layoutParams.height = (int) newHeightInDp;
        layoutParams.width = (int) newWidthInDp;
        view.requestLayout();

    }

    public static void setViewMarginInPx(View view, float newLeftMargin, float newTopMargin) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();


        layoutParams.leftMargin = (int) newLeftMargin;
        layoutParams.topMargin = (int) newTopMargin;
        view.requestLayout();

    }




}
