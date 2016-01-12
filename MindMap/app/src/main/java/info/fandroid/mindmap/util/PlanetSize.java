package info.fandroid.mindmap.util;

/**
 * Created by Vitaly on 07.01.2016.
 */
public class PlanetSize {

    private float planetSize;

    private float circleSize;

    private float borderSize;

    public static final int PLANET_ANGLE = 60;

    public static final float DEFAULT_CIRCLE_SIZE_IN_DP = 90;
    public static final float DEFAULT_BORDER_SIZE_IN_DP = 90;

    public static final float DEFAULT_PARENT_CIRCLE_SIZE_IN_DP = 135;
    public static final float DEFAULT_PARENT_BORDER_SIZE_IN_DP = 135;

    public static final float COLLAPSED_PARENT_BORDER_SIZE_IN_DP = 250;

    public PlanetSize() {
    }

    public PlanetSize(float planetSize, float circleSize, float borderSize) {
        this.planetSize = planetSize;
        this.circleSize = circleSize;
        this.borderSize = borderSize;
    }

    public void setPlanetSize(float planetSize, float circleSize, float borderSize) {
        this.planetSize = planetSize;
        this.circleSize = circleSize;
        this.borderSize = borderSize;
    }

    public float getPlanetSize() {
        return planetSize;
    }

    public void setPlanetSize(float planetSize) {
        this.planetSize = planetSize;
    }

    public float getCircleSize() {
        return circleSize;
    }

    public void setCircleSize(float circleSize) {
        this.circleSize = circleSize;
    }

    public float getBorderSize() {
        return borderSize;
    }

    public void setBorderSize(float borderSize) {
        this.borderSize = borderSize;
    }
}
