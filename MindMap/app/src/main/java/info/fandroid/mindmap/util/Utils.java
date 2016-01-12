package info.fandroid.mindmap.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PointF;
import android.util.TypedValue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Vitaly on 27.12.2015.
 */
public class Utils {

    public static String getDateWithCurrentLocale(long initialDate, Context context) {
        Locale currentLocale = context.getResources().getConfiguration().locale;

        Date date = new Date(initialDate); // *1000 is to convert seconds to milliseconds

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        SimpleDateFormat sdf;

        if (calendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR) &&
                calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)) {
            sdf = new SimpleDateFormat("H:mm", currentLocale);

        } else if (calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)) {
            sdf = new SimpleDateFormat("d MMM", currentLocale);

        } else {
            sdf = new SimpleDateFormat("dd.MM.yy", currentLocale);
        }



        return sdf.format(date);
    }


    public static float dpToPixels(float dps, Context context) {
        Resources r = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics());
    }


    public static PointF getPointOnCircleBorder(float angleInDegrees, float radius, PointF origin) {


        float x = (float)(radius * Math.cos(angleInDegrees * Math.PI / 180F)) + origin.x;
        float y = (float)(radius * Math.sin(angleInDegrees * Math.PI / 180F)) + origin.y;


        return new PointF(x, y);
    }


    public static int parseAngleToPlanetPosition(int angle) {
        int position;

        switch (angle) {
            case -90:
                position = 1;
                break;
            case -30:
                position = 2;
                break;
            case 30:
                position = 3;
                break;
            case 90:
                position = 4;
                break;
            case 150:
                position = 5;
                break;
            case 210:
                position = 6;
                break;
            default:
                position = 0;
                break;
        }

        return position;
    }

    public static float getRotationAngle(int position) {
        float angle;

        switch (position) {
            case 1:
                angle = 143.2f;
                break;
            case 2:
                angle = 83.2f;
                break;
            case 3:
                angle = 23.2f;
                break;
            case 4:
                angle = -36.8f;
                break;
            case 5:
                angle = -96.8f;
                break;
            case 6:
                angle = -156.8f;
                break;
            default:
                angle = 0;
                break;
        }

        return angle;
    }

    public static String removeLastCharacterFromString(String str) {
        if (str.length() > 0) {
            str = str.substring(0, str.length()-1);
        }
        return str;
    }

}
