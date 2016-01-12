package info.fandroid.mindmap.util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.view.View;

import java.util.List;

import info.fandroid.mindmap.view.PlanetView;

/**
 * Created by Vitaly on 05.01.2016.
 */
public class PlanetAnimatorManager {

    AnimatorSet planetClickAnimation;

    static float circleHeight;
    static float circleHeightTo;
    static float borderHeight;
    static float borderHeightTo;



    public PlanetAnimatorManager() {
        planetClickAnimation = new AnimatorSet();
    }



    public static AnimatorSet createPlanetClickAnimator(PlanetView planetView) {

        circleHeight =  Utils.dpToPixels(135, planetView.getContext());
        circleHeightTo =  Utils.dpToPixels(135 * 4.5f, planetView.getContext());
        borderHeight =  Utils.dpToPixels(135 * 2, planetView.getContext());
        borderHeightTo =  Utils.dpToPixels(135 * 2 * 3.82f, planetView.getContext());

        PlanetView parent = (PlanetView) planetView.getParent();
        ValueAnimator animator5 = resizeViewWithAnimation(parent.cvCircle, parent,
                circleHeight, circleHeightTo, false);
        ValueAnimator animator6 = resizeViewWithAnimation(parent.cvBorder, parent,
                borderHeight, borderHeightTo, false);
        ValueAnimator animator2 = resizeViewWithAnimation(planetView.cvCircle, planetView,
                planetView.cvCircle.getHeight(),
                planetView.cvCircle.getHeight() * 1.5f, false);

        ObjectAnimator animatornew = ObjectAnimator.ofFloat(planetView, "translationX", planetView.getX(), -200);

        ObjectAnimator scaleCircleX = ObjectAnimator.ofFloat(planetView.getParent(), "scaleX", 4.25f);
        ObjectAnimator scaleCircleY = ObjectAnimator.ofFloat(planetView.getParent(), "scaleY", 4.25f);

        ObjectAnimator scaleBorderX = ObjectAnimator.ofFloat(parent.cvCircle, "scaleX", 1.1f);
        ObjectAnimator scaleBorderY = ObjectAnimator.ofFloat(parent.cvCircle, "scaleY", 1.1f);

        ObjectAnimator scaleChildX = ObjectAnimator.ofFloat(planetView, "scaleX", 0.3529f);
        ObjectAnimator scaleChildY = ObjectAnimator.ofFloat(planetView, "scaleY", 0.3529f);

        ObjectAnimator scaleChildTX = ObjectAnimator.ofFloat(planetView.cvCircle, "scaleX", 0.3529f);
        ObjectAnimator scaleChildTY = ObjectAnimator.ofFloat(planetView.cvCircle, "scaleY", 0.3529f);

        ObjectAnimator scaleChildBX = ObjectAnimator.ofFloat(planetView.cvBorder, "scaleX", 0.3529f);
        ObjectAnimator scaleChildBY = ObjectAnimator.ofFloat(planetView.cvBorder, "scaleY", 0.3529f);

        ValueAnimator valueAnimator = planetStabilisation(planetView, parent,
                borderHeight, borderHeight/3.82f);


        float translation = 1.8f;

        //ValueAnimator valueAnimator1 = resizeViewWithAnimation(planetView.cvCircle, planetView, )


        ObjectAnimator animator3 = ObjectAnimator.ofFloat(parent, "translationY", -((View) (planetView.getParent())).getHeight()/0.8f);
        ObjectAnimator animator = ObjectAnimator.ofFloat(parent, "translationX", -((View) (planetView.getParent())).getHeight()/1.07f);

        AnimatorSet set = new AnimatorSet();
        set.play(scaleCircleX).with(scaleCircleY).with(animator).with(animator3).with(scaleChildX)
                .with(scaleChildY).with(scaleChildBX).with(scaleChildBY).with(scaleBorderX)
                .with(scaleBorderY);
        set.setDuration(500);

        return set;
    }



    public static void reversePlanetClickAnimator(AnimatorSet animatorSet) {
        if (animatorSet != null) {
            List<Animator> animators = animatorSet.getChildAnimations();
            for (Animator animator : animators) {
                if (animator instanceof ObjectAnimator) {
                    ObjectAnimator objectAnimator = (ObjectAnimator) animator;
                    objectAnimator.reverse();
                } else  if (animator instanceof ValueAnimator) {
                    ValueAnimator valueAnimator = (ValueAnimator) animator;
                    valueAnimator.reverse();
                }
            }
        }
    }

    public static ValueAnimator resizeViewWithAnimation(final View view, final View parent, final float fromHeight, float toHeight, final boolean offset) {

        final PointF parentOrigin = new PointF(parent.getX(), parent.getY());
        ValueAnimator anim = ValueAnimator.ofFloat(fromHeight, toHeight);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float val = (float) valueAnimator.getAnimatedValue();

                if (offset) {
                    float offset = val / 2 - fromHeight / 2;

                    parent.setX(parentOrigin.x - offset);
                    parent.setY(parentOrigin.y - offset);
                }

                ViewSizeManager.setViewSizeInPx(view, val, val);
            }
        });

        return anim;

    }

    public static ValueAnimator resizeViewWithAnimation(final View view, final float fromHeight, float toHeight) {

        ValueAnimator anim = ValueAnimator.ofFloat(fromHeight, toHeight);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float val = (float) valueAnimator.getAnimatedValue();

                ViewSizeManager.setViewSizeInPx(view, val, val);
            }
        });

        return anim;

    }



    public static ValueAnimator resizeViewWithAnimationOut(final View view, final PlanetView parent, final float fromHeight, float toHeight, final boolean offset) {

        final PointF parentOrigin = new PointF(parent.getX(), parent.getY());
        final float defSizeInPx = Utils.dpToPixels(PlanetSize.DEFAULT_CIRCLE_SIZE_IN_DP, view.getContext());

        ValueAnimator anim = ValueAnimator.ofFloat(fromHeight, toHeight);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float val = (float) valueAnimator.getAnimatedValue() + defSizeInPx;
                int height = (int) (fromHeight + defSizeInPx)/2;

                if (offset) {
                    float offset = val / 2 - fromHeight / 2;

                    parent.setX(parentOrigin.x - offset);
                    parent.setY(parentOrigin.y - offset);
                }

                ViewSizeManager.setViewSizeInPx(view, val, val);
            }
        });

        return anim;

    }

    public static ValueAnimator planetStabilisation(final View view, final PlanetView parent, final float fromRadius, float toRadius) {

        ValueAnimator anim = ValueAnimator.ofFloat(fromRadius, toRadius);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float val = (float) valueAnimator.getAnimatedValue();
                final PointF parentOrigin = new PointF(parent.cvBorder.getX() + val/2,
                        parent.cvBorder.getY() + val/2);
                PointF pointF = Utils.getPointOnCircleBorder(-90, val/2, parentOrigin);

                view.setX(pointF.x - view.getWidth()/2);
                view.setY(pointF.y - view.getHeight()/2);
            }
        });

        return anim;
    }


}
