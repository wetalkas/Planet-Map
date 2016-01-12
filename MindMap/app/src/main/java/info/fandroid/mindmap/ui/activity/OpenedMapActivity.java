package info.fandroid.mindmap.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import info.fandroid.mindmap.R;
import info.fandroid.mindmap.database.DBManager;
import info.fandroid.mindmap.model.ModelPlanet;
import info.fandroid.mindmap.ui.fragment.ChildPlanetFragment;
import info.fandroid.mindmap.ui.fragment.CreateNewPlanetFragment;
import info.fandroid.mindmap.ui.fragment.EditPlaneFragment;
import info.fandroid.mindmap.util.FragmentManagerHelper;
import info.fandroid.mindmap.util.PlanetAnimatorManager;
import info.fandroid.mindmap.util.PlanetStackManager;
import info.fandroid.mindmap.util.Utils;
import info.fandroid.mindmap.view.PlanetView;


public class OpenedMapActivity extends AppCompatActivity implements CreateNewPlanetFragment.OnCreatePlanetListener {
    private PlanetView mLookedPlanet;

    private String mPath;

    private PlanetStackManager mPlanetStackManager;

    private DBManager mDbManager;

    private FragmentManagerHelper mFragmentManagerHelper;
    private Toolbar mToolbar;

    private AnimatorSet globalAnimatorSet;

    private FloatingActionButton mFab;

    private PlanetView rootPlanet;
    private AnimatorSet animatorSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_opened_map);

        mPath = "0";

        mDbManager = DBManager.getInstance();

        PlanetStackManager.getInstance().init();
        mPlanetStackManager = PlanetStackManager.getInstance();

        FragmentManagerHelper.getInstance().init(OpenedMapActivity.this);
        mFragmentManagerHelper = FragmentManagerHelper.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mToolbar.setVisibility(View.GONE);

        rootPlanet = (PlanetView) findViewById(R.id.map_view);

        Bundle mapBundle = getIntent().getExtras();
        if (mapBundle != null) {
            ModelPlanet modelPlanet = ModelPlanet.valueOf(mapBundle);
            Log.d("opened map act", "id " + modelPlanet.getId());

            rootPlanet.setCircleText(modelPlanet.getName());
            rootPlanet.setCircleColor(modelPlanet.getColor());

            rootPlanet.mModelMap = modelPlanet;
        }


        mPlanetStackManager.addView(rootPlanet);
        mLookedPlanet = rootPlanet;


        final List<ModelPlanet> planets = mDbManager.query().planet().getDefaultSelection(rootPlanet.mModelMap.getId(), mPath);

        rootPlanet.setDefaultLookedPlanet(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                for (ModelPlanet planet : planets) {
                    addNewPlanet(planet, false);
                }
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        }).start();

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFab.setVisibility(View.GONE);
                CreateNewPlanetFragment createNewPlanetFragment = new CreateNewPlanetFragment();

                createNewPlanetFragment.setArguments(ModelPlanet.toBundle(rootPlanet.mModelMap));

                mFragmentManagerHelper.setFragment(R.id.frame_container, createNewPlanetFragment, true);

            }
        });
    }



    public void addNewPlanet(final ModelPlanet modelMap, boolean saveToDb) {
        if (mLookedPlanet.mChildCount < 6) {
            final PlanetView childPlanet = new PlanetView(mLookedPlanet.getContext());
            childPlanet.setCircleText(modelMap.getName());
            childPlanet.setCircleColor(modelMap.getColor());

            childPlanet.parentPlanet = mLookedPlanet;
            childPlanet.mModelMap = modelMap;
            mLookedPlanet.addChild(childPlanet);
            mLookedPlanet.childPlanetViews.add(childPlanet);

            mLookedPlanet.setDefaultChildPlanet(childPlanet);
            childPlanet.cvBorder.setVisibility(View.INVISIBLE);

            ValueAnimator valueAnimator = PlanetAnimatorManager.resizeViewWithAnimation(childPlanet.cvCircle, childPlanet,
                    0, childPlanet.getCircleViewSize(), false);
            valueAnimator.setDuration(300);
            valueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    childPlanet.cvBorder.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            valueAnimator.start();

            ObjectAnimator.ofFloat(childPlanet.tvCircleText, "alpha", 0f, 1f).setDuration(500).start();

            childPlanet.mModelMap.setPath(mPath + childPlanet.mPosition);
            childPlanet.mModelMap.setParentId(childPlanet.parentPlanet.mModelMap.getId());
            childPlanet.mModelMap.setRootId(rootPlanet.mModelMap.getId());

            if (saveToDb) {
                mDbManager.save().planet().full(childPlanet.mModelMap);
            }

            childPlanet.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    EditPlaneFragment editPlaneFragment = new EditPlaneFragment();
                    editPlaneFragment.setArguments(ModelPlanet.toBundle(childPlanet.mModelMap));
                    mFragmentManagerHelper.setFragment(R.id.frame_container, editPlaneFragment, true);
                    editPlaneFragment.setOneditPlanetListener(new EditPlaneFragment.OnEditPlanetListener() {
                        @Override
                        public void onBackFromEdit(ModelPlanet model) {
                            childPlanet.mModelMap = modelMap;
                            childPlanet.tvCircleText.setText(model.getName());
                            childPlanet.cvCircle.setColorFilter(model.getColor());
                            onBackPressed();
                        }
                    });

                    return true;
                }
            });


            childPlanet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    /*ChildPlanetFragment childPlanetFragment = new ChildPlanetFragment();
                    childPlanetFragment.setOnPlanetListener(new ChildPlanetFragment.OnPlanetListener() {
                        @Override
                        public void onChild(PlanetView planetView) {

                            mPlanetStackManager.addView(planetView);
                            mPath = childPlanet.mModelMap.getPath();

                            planetView.mModelMap = modelMap;
                            planetView.parentPlanet = mLookedPlanet;

                            final List<ModelPlanet> planets = mDbManager.query().planet().getDefaultSelection(planetView.mModelMap.getId(), childPlanet.mModelMap.getPath());

                            planetView.setDefaultLookedPlanet(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    for (ModelPlanet planet : planets) {
                                        addNewPlanet(planet, false);
                                    }
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {
                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {
                                }
                            });
                            mLookedPlanet = planetView;
                        }
                    });

                    mFragmentManagerHelper.setFragment(R.id.frame_container, childPlanetFragment, true);*/
                    /*RotateAnimation aRotate = new RotateAnimation(0, -270,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

                    aRotate.setStartOffset(0);
                    aRotate.setDuration(500);
                    aRotate.setFillAfter(true);
                    aRotate.setInterpolator(OpenedMapActivity.this, android.R.anim.decelerate_interpolator);

                    mLookedPlanet.startAnimation(aRotate);*/

                    final AnimatorSet animatorSet2;

                    float rotationAngle = Utils.getRotationAngle(childPlanet.mPosition);
                    Log.d("mposition", "pos = " + childPlanet.mPosition);

                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mLookedPlanet, "rotation", rotationAngle);
                    objectAnimator.setDuration(500);
                    objectAnimator.start();

                    ObjectAnimator textStabilisator = ObjectAnimator.ofFloat(mLookedPlanet.tvCircleText, "rotation", -rotationAngle);
                    textStabilisator.setDuration(500);
                    textStabilisator.start();

                    mLookedPlanet.lookedRotationAnimator = textStabilisator;
                    mLookedPlanet.mRotationAngle = rotationAngle;

                    for (PlanetView planetView : mLookedPlanet.childPlanetViews) {
                        ObjectAnimator objectAnimatorr = ObjectAnimator.ofFloat(planetView, "rotation", -rotationAngle);
                        objectAnimatorr.setDuration(500);
                        objectAnimatorr.start();
                        planetView.rotationAnimator = objectAnimatorr;
                    }
                    objectAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });

                    animatorSet = PlanetAnimatorManager.createPlanetClickAnimator(childPlanet);
                    mLookedPlanet.animatorSet = animatorSet;
                    animatorSet.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            ChildPlanetFragment childPlanetFragment = new ChildPlanetFragment();
                            mFragmentManagerHelper.setFragment(R.id.frame_container, childPlanetFragment, true);
                            childPlanetFragment.setOnPlanetListener(new ChildPlanetFragment.OnPlanetListener() {
                                @Override
                                public void onChild(PlanetView planetView) {

                                    mPlanetStackManager.addView(planetView);
                                    mPath = childPlanet.mModelMap.getPath();

                                    planetView.mModelMap = modelMap;
                                    planetView.parentPlanet = mLookedPlanet;

                                    final List<ModelPlanet> planets = mDbManager.query().planet().getDefaultSelection(planetView.mModelMap.getId(), childPlanet.mModelMap.getPath());

                                    Log.d("planewts ", "size " + planets.size());

                                    planetView.setDefaultLookedPlanet(new Animator.AnimatorListener() {
                                        @Override
                                        public void onAnimationStart(Animator animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            for (ModelPlanet planet : planets) {
                                                addNewPlanet(planet, false);
                                            }
                                        }

                                        @Override
                                        public void onAnimationCancel(Animator animation) {

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animator animation) {

                                        }
                                    }).start();
                                    mLookedPlanet = planetView;


                                }
                            });


                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });

                    animatorSet.start();


                }
            });
        } else {
            Toast.makeText(OpenedMapActivity.this, "Field is full", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {

        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        } else {
            finish();
        }

        Fragment myFragment = getFragmentManager().findFragmentByTag(String.valueOf(CreateNewPlanetFragment.class));
        if (myFragment != null && myFragment.isVisible()) {
        } else {
            View parent = mPlanetStackManager.getParentView();


            mPath = Utils.removeLastCharacterFromString(mPath);
            if (parent != null) {

                mLookedPlanet = (PlanetView) parent;

                PlanetAnimatorManager.reversePlanetClickAnimator(mLookedPlanet.animatorSet);
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mLookedPlanet, "rotation", 0);
                objectAnimator.setDuration(500);
                objectAnimator.start();
                mLookedPlanet.lookedRotationAnimator.reverse();
                for (PlanetView planetView : mLookedPlanet.childPlanetViews) {
                    planetView.rotationAnimator.reverse();
                }
            }
        }

        mToolbar.setVisibility(View.GONE);
        mFab.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackFromCreate(ModelPlanet model) {
        mFab.setVisibility(View.VISIBLE);
        mToolbar.setVisibility(View.GONE);
        getFragmentManager().popBackStack();
        addNewPlanet(model, true);
    }
}


