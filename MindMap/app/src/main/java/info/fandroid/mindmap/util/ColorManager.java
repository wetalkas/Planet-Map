package info.fandroid.mindmap.util;

import java.util.Random;

import info.fandroid.mindmap.R;

/**
 * Created by Vitaly on 29.12.2015.
 */
public class ColorManager {

    public static final int[] color = {
            R.color.planet_color_1_1, R.color.planet_color_2_1, R.color.planet_color_3_1,
            R.color.planet_color_4_1, R.color.planet_color_5_1, R.color.planet_color_1_2,
            R.color.planet_color_2_2, R.color.planet_color_3_2, R.color.planet_color_4_2,
            R.color.planet_color_5_2, R.color.planet_color_1_3, R.color.planet_color_2_3,
            R.color.planet_color_3_3, R.color.planet_color_4_3, R.color.planet_color_5_3};



    public static int getRandomColor() {
        Random rand = new Random();
        return color[rand.nextInt(color.length - 1)];
    }

}
