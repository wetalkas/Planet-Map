package info.fandroid.mindmap.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import info.fandroid.mindmap.R;
import info.fandroid.mindmap.database.DBManager;
import info.fandroid.mindmap.model.ModelMap;
import info.fandroid.mindmap.model.ModelPlanet;
import info.fandroid.mindmap.util.ColorManager;
import info.fandroid.mindmap.util.Expander;
import info.fandroid.mindmap.util.ToolbarHelper;

/**
 * Created by Vitaly on 12.01.2016.
 */
public class EditPlaneFragment extends Fragment {


    public EditPlaneFragment() {
        // Required empty public constructor
    }


    private AppCompatActivity activity;


    OnEditPlanetListener mCallback;

    private EditText etMindMapName;
    private EditText etMindMapDescription;

    private RelativeLayout rvHelloLayout;

    private ModelPlanet mParentModelPlanet;

    private GridLayout glColorPickerGrid;
    CircleImageView cvCurrentColor;

    private TextView tvColorPicker;

    ModelPlanet model;

    InputMethodManager imm;

    View currentFocusView;

    DBManager mDbManager;


    private RelativeLayout rvHelloHidden;


    // Container Activity must implement this interface
    public interface OnEditPlanetListener {
        void onBackFromEdit(ModelPlanet model);
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_new_planet, container, false);

        activity = (AppCompatActivity) getActivity();

        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        if (toolbar.getVisibility() != View.VISIBLE) {
            toolbar.setVisibility(View.VISIBLE);
        }

        mDbManager = DBManager.getInstance();
        model = new ModelPlanet();
        model.setColor(getResources().getColor(ColorManager.getRandomColor()));

        currentFocusView = getActivity().getCurrentFocus();

        Bundle args = getArguments();

        model = ModelPlanet.valueOf(args);



        if (currentFocusView != null) {
            imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        }

        setUI(rootView);

        Bundle parentModelPlanetBundle = getArguments();
        mParentModelPlanet = ModelPlanet.valueOf(parentModelPlanetBundle);





        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        etMindMapName.requestFocus();
        InputMethodManager keyboard = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.showSoftInput(etMindMapName, 0);
    }

    private void setUI(View rootView) {
        setHasOptionsMenu(true);

        etMindMapName = (EditText) rootView.findViewById(R.id.et_create_mind_map_name);
        etMindMapDescription = (EditText) rootView.findViewById(R.id.et_create_mind_map_description);
        rvHelloLayout = (RelativeLayout) rootView.findViewById(R.id.hello_layout);
        rvHelloHidden = (RelativeLayout) rootView.findViewById(R.id.hello_hidden);
        glColorPickerGrid = (GridLayout) rootView.findViewById(R.id.color_grid);
        tvColorPicker = (TextView) rootView.findViewById(R.id.hello_text);
        cvCurrentColor = (CircleImageView) rootView.findViewById(R.id.cv_current_color);

        cvCurrentColor.setColorFilter(model.getColor());

        etMindMapName.setText(model.getName());
        etMindMapDescription.setText(model.getDescription());
        cvCurrentColor.setColorFilter(model.getColor());

        for (int i = 0; i < glColorPickerGrid.getChildCount(); i++) {
            View child = glColorPickerGrid.getChildAt(i);

            final int finalI = i;
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    model.setColor(getResources().getColor(ModelMap.parseColorGrid(finalI)));
                    cvCurrentColor.setColorFilter(model.getColor());
                }
            });



        }

        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        ToolbarHelper.setCreateMindMapToolbar(toolbar);
        toolbar.setTitle("Edit satellite");


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imm != null && imm.isAcceptingText()) {

                    imm.hideSoftInputFromWindow(currentFocusView.getWindowToken(), 0);
                }

                model.setName(etMindMapName.getText().toString());
                model.setDescription(etMindMapDescription.getText().toString());

                mDbManager.update().planet().full(model.getId(), model);

                currentFocusView = getActivity().getCurrentFocus();



                if (currentFocusView != null) {
                    imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                }

                if (imm != null && imm.isAcceptingText()) {
                    imm.hideSoftInputFromWindow(currentFocusView.getWindowToken(), 0);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mCallback != null) {
                                mCallback.onBackFromEdit(model);
                            }

                        }
                    }, 100);
                }

            }
        });

        rvHelloHidden.setVisibility(View.GONE);

        rvHelloLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rvHelloHidden.getVisibility() == View.GONE) {

                    if (imm != null && imm.isAcceptingText()) {

                        imm.hideSoftInputFromWindow(currentFocusView.getWindowToken(), 0);

                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {

                                Expander.expand(rvHelloHidden);

                            }
                        }, 100);

                    } else {
                        Expander.expand(rvHelloHidden);
                    }
                } else {
                    Expander.collapse(rvHelloHidden);
                }
            }
        });

        etMindMapName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Expander.collapse(rvHelloHidden);

                }
            }
        });



        etMindMapDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Expander.collapse(rvHelloHidden);

                }
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_create_mind_map, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setOneditPlanetListener(OnEditPlanetListener mCallback) {
        this.mCallback = mCallback;
    }
}
