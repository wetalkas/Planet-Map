package info.fandroid.mindmap.ui.fragment;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.hdodenhof.circleimageview.CircleImageView;
import info.fandroid.mindmap.R;
import info.fandroid.mindmap.util.PlanetAnimatorManager;
import info.fandroid.mindmap.util.PlanetSize;
import info.fandroid.mindmap.util.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {

    private CircleImageView mOrbit;

    private OnSplashFragmentListener mOnSplashFragmentListener;

    public SplashFragment() {
        // Required empty public constructor
    }



    public interface OnSplashFragmentListener {
        void onSplashEnded();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_splash, container, false);

        mOrbit = (CircleImageView) rootView.findViewById(R.id.splashOrbit);

        float fromHeight = Utils.dpToPixels(PlanetSize.DEFAULT_PARENT_BORDER_SIZE_IN_DP, getActivity());
        float toHeight = Utils.dpToPixels(PlanetSize.COLLAPSED_PARENT_BORDER_SIZE_IN_DP, getActivity());
        ValueAnimator expand = PlanetAnimatorManager.resizeViewWithAnimation(mOrbit, fromHeight, fromHeight*2);
        ValueAnimator collapse = PlanetAnimatorManager.resizeViewWithAnimation(mOrbit, fromHeight*2, toHeight);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(expand).before(collapse);
        animatorSet.setDuration(500);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //getActivity().getFragmentManager().popBackStack();
                mOnSplashFragmentListener.onSplashEnded();
                //SplashFragment.this.getActivity().getFragmentManager().popBackStack();
                //getFragmentManager().popBackStack();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();

        return rootView;
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        try {
            mOnSplashFragmentListener = (OnSplashFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnSplashFragmentListener");
        }
    }


}
