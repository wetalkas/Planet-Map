package info.fandroid.mindmap.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import info.fandroid.mindmap.R;
import info.fandroid.mindmap.model.ModelPlanet;
import info.fandroid.mindmap.util.PlanetAnimatorManager;
import info.fandroid.mindmap.util.Utils;
import info.fandroid.mindmap.util.ViewSizeManager;
import info.fandroid.mindmap.util.PlanetSize;

/**
 * Created by Vitaly on 15.12.2015.
 */
public class PlanetView extends RelativeLayout {


    public PlanetSize mPlanetSize;

    public int mChildCount;

    public int mAngle;
    public int mPosition;
    public float mRotationAngle;

    public AnimatorSet animatorSet;
    public ObjectAnimator rotationAnimator;
    public ObjectAnimator lookedRotationAnimator;
    public PlanetView parentPlanet;
    public ModelPlanet mModelMap;

    public TextView tvCircleText;
    public CircleImageView cvCircle;
    public CircleImageView cvBorder;

    public CircleImageView cvPlanet;

    public List<PlanetView> childPlanetViews;

    public PlanetView(Context context) {
        super(context);
        initializeViews(context);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(0, 0);
    }

    public PlanetView(Context context, PlanetSize planetSize) {
        super(context);
        this.mPlanetSize = planetSize;
        initializeViews(context);
    }

    public PlanetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);

        setAttributes(context, attrs);
    }

    private void initializeViews(Context context) {
        inflate(context, R.layout.view_planet, this);

        childPlanetViews = new ArrayList<>();
        animatorSet = new AnimatorSet();

        setClipChildren(false);

        if (mPlanetSize == null) {
            this.mPlanetSize = new PlanetSize();
        }

        mModelMap = new ModelPlanet();

        mAngle = -150;

        tvCircleText = (TextView) findViewById(R.id.tvPlanetViewText);
        cvCircle = (CircleImageView) findViewById(R.id.cv_planet_view_circle);
        cvBorder = (CircleImageView) findViewById(R.id.border);
        cvPlanet = (CircleImageView) findViewById(R.id.planet);
    }

    public void setAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PlanetView);

        tvCircleText.setText(typedArray.getString(R.styleable.PlanetView_circle_text));

        cvCircle.setColorFilter(typedArray.getColor(R.styleable.PlanetView_circle_color,
                getResources().getColor(R.color.colorAccent)));

        

        typedArray.recycle();
    }




    public void setCircleText(CharSequence text) {
        tvCircleText.setText(text);
    }

    public void setCircleColor(int color) {
        cvCircle.setColorFilter(color);
    }





    public void setPos(float newX, float newY) {
        this.setX(newX);
        this.setY(newY);
    }



    public AnimatorSet setDefaultLookedPlanet(Animator.AnimatorListener animatorListener) {
        setCircleViewSize(true, PlanetSize.DEFAULT_PARENT_CIRCLE_SIZE_IN_DP);
        setBorderViewSize(true, PlanetSize.DEFAULT_PARENT_BORDER_SIZE_IN_DP);
        setPlanetViewSize(true, PlanetSize.DEFAULT_PARENT_BORDER_SIZE_IN_DP);

        ValueAnimator anim = PlanetAnimatorManager.resizeViewWithAnimation(cvBorder, this,
                mPlanetSize.getCircleSize(), mPlanetSize.getCircleSize() * 1.851851f, false);



        ValueAnimator anim2 = PlanetAnimatorManager.resizeViewWithAnimationOut(cvPlanet, this,
                mPlanetSize.getCircleSize(), mPlanetSize.getCircleSize() * 1.851851f, false);



        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.setStartDelay(100);
        animatorSet.play(anim).with(anim2);
        if (animatorListener != null) {
            animatorSet.addListener(animatorListener);
        }


        return animatorSet;
    }

    public void setDefaultLookedPlanetFragment() {
        setCircleViewSize(true, PlanetSize.DEFAULT_PARENT_CIRCLE_SIZE_IN_DP);
        setBorderViewSize(true, PlanetSize.DEFAULT_PARENT_CIRCLE_SIZE_IN_DP*2);
        setPlanetViewSize(true, PlanetSize.DEFAULT_PARENT_CIRCLE_SIZE_IN_DP*2);

        ValueAnimator anim = PlanetAnimatorManager.resizeViewWithAnimation(cvBorder, this,
                mPlanetSize.getCircleSize(), mPlanetSize.getCircleSize() * 2, false);



        ValueAnimator anim2 = PlanetAnimatorManager.resizeViewWithAnimationOut(cvPlanet, this,
                mPlanetSize.getCircleSize(), mPlanetSize.getCircleSize() * 2, false);



        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1);
        animatorSet.play(anim).with(anim2);
        /*if (animatorListener != null) {
            animatorSet.addListener(animatorListener);
        }


        return animatorSet;*/
    }

    public void setDefaultChildPlanet(PlanetView childPlanet) {
        childPlanet.setCircleViewSize(true, PlanetSize.DEFAULT_CIRCLE_SIZE_IN_DP);
        childPlanet.setBorderViewSize(true, PlanetSize.DEFAULT_BORDER_SIZE_IN_DP);

        PointF pointF = Utils.getPointOnCircleBorder(mAngle,
                Utils.dpToPixels(PlanetSize.COLLAPSED_PARENT_BORDER_SIZE_IN_DP / 2, getContext()),
                new PointF(cvBorder.getX() + cvBorder.getHeight() / 2,
                        cvBorder.getY() + cvBorder.getHeight() / 2));

        childPlanet.setX(pointF.x - childPlanet.mPlanetSize.getCircleSize() / 2);
        childPlanet.setY(pointF.y - childPlanet.mPlanetSize.getCircleSize() / 2);

        int position = Utils.parseAngleToPlanetPosition(mAngle);
        childPlanet.mPosition = position;
        Log.d("planet view", "position " + position);
    }

    public void addChild(PlanetView child) {
        mChildCount++;
        mAngle += PlanetSize.PLANET_ANGLE;
        this.addView(child);

    }


    public void setPlanetViewSize(boolean inDp, float planetSize, float circleSize, float borderSize) {
        if (inDp) {
            ViewSizeManager.setViewSizeInDp(this, planetSize, planetSize);
            ViewSizeManager.setViewSizeInDp(this.cvCircle, circleSize, circleSize);
            ViewSizeManager.setViewSizeInDp(this.cvBorder, borderSize, borderSize);

            float planetSizeInPx = Utils.dpToPixels(planetSize, this.getContext());
            float circleSizeInPx = Utils.dpToPixels(circleSize, this.getContext());
            float borderSizeInPx = Utils.dpToPixels(borderSize, this.getContext());

            mPlanetSize.setPlanetSize(planetSizeInPx, circleSizeInPx, borderSizeInPx);
        } else {
            ViewSizeManager.setViewSizeInPx(this, planetSize, planetSize);
            ViewSizeManager.setViewSizeInPx(this.cvCircle, circleSize, circleSize);
            ViewSizeManager.setViewSizeInPx(this.cvBorder, borderSize, borderSize);

            mPlanetSize.setPlanetSize(planetSize, circleSize, borderSize);
        }
    }

    public void setPlanetViewSize(boolean inDp, float planetSize) {
        if (inDp) {
            ViewSizeManager.setViewSizeInDp(this.cvPlanet, planetSize, planetSize);



            float planetSizeInPx = Utils.dpToPixels(planetSize, this.getContext());

            mPlanetSize.setPlanetSize(planetSizeInPx);
        } else {
            ViewSizeManager.setViewSizeInPx(this.cvPlanet, planetSize, planetSize);

            mPlanetSize.setPlanetSize(planetSize);
        }
    }


    public void setCircleViewSize(boolean inDp, float circleSize) {
        if (inDp) {
            ViewSizeManager.setViewSizeInDp(this.cvCircle, circleSize, circleSize);

            float circleSizeInPx = Utils.dpToPixels(circleSize, this.getContext());

            mPlanetSize.setCircleSize(circleSizeInPx);
        } else {
            ViewSizeManager.setViewSizeInPx(this.cvCircle, circleSize, circleSize);

            mPlanetSize.setCircleSize(circleSize);
        }
    }


    public void setBorderViewSize(boolean inDp, float borderSize) {
        if (inDp) {
            ViewSizeManager.setViewSizeInDp(this.cvBorder, borderSize, borderSize);

            float borderSizeInPx = Utils.dpToPixels(borderSize, this.getContext());

            mPlanetSize.setBorderSize(borderSizeInPx);
        } else {
            ViewSizeManager.setViewSizeInPx(this.cvBorder, borderSize, borderSize);

            mPlanetSize.setBorderSize(borderSize);
        }
    }


    public float getCircleViewSize() {
        return mPlanetSize.getCircleSize();
    }

    public float getBorderViewSize() {
        return mPlanetSize.getBorderSize();
    }


    public String getCircleText() {
        return tvCircleText.getText().toString();
    }


    public int getCircleColor() {
        return cvCircle.getFillColor();
    }




}
