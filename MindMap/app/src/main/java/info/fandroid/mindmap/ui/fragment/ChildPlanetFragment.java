package info.fandroid.mindmap.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import info.fandroid.mindmap.R;
import info.fandroid.mindmap.view.PlanetView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChildPlanetFragment extends Fragment {

    public CircleImageView cvParent;
    public TextView mTvParentText;

    public OnPlanetListener onPlanetListener;

    public ViewGroup rootView;


    public ChildPlanetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_child_planet, container, false);

        PlanetView planetView = (PlanetView) rootView.findViewById(R.id.child_planet);
        cvParent = (CircleImageView) rootView.findViewById(R.id.parent_planet);
        mTvParentText = (TextView) rootView.findViewById(R.id.tv_parent);

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);

        cvParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        if (onPlanetListener != null) {
            onPlanetListener.onChild(planetView);
        }

        planetView.setCircleText(planetView.mModelMap.getName());
        planetView.setCircleColor(planetView.mModelMap.getColor());

        mTvParentText.setText(planetView.parentPlanet.mModelMap.getName());
        cvParent.setColorFilter(planetView.parentPlanet.mModelMap.getColor());

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public interface OnPlanetListener {
        void onChild(PlanetView planetView);

    }



    public void setOnPlanetListener(OnPlanetListener onPlanetListener) {
        this.onPlanetListener = onPlanetListener;
    }
}
