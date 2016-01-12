package info.fandroid.mindmap.ui.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import info.fandroid.mindmap.R;
import info.fandroid.mindmap.model.ModelMap;
import info.fandroid.mindmap.util.Expander;
import info.fandroid.mindmap.util.ToolbarHelper;
import info.fandroid.mindmap.ui.dialog.ChooseSubjectDialogFragment;
import info.fandroid.mindmap.util.ColorManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateMindMapFragment extends Fragment {

    private AppCompatActivity activity;


    OnCreateFragmentListener mCallback;

    private EditText etMindMapName;
    private EditText etMindMapDescription;
    private TextView tvMindMapSubjectText;
    private TextView tvColorPicker;
    private ImageButton ibMindMapSubjectIcon;
    private GridLayout glColorPickerGrid;
    CircleImageView cvCurrentColor;

    private RelativeLayout rvHelloLayout;

    InputMethodManager imm;

    View currentFocusView;

    private RelativeLayout rvHelloHidden;

    private ModelMap.Subject mSubject;


    public CreateMindMapFragment() {
        // Required empty public constructor
    }

    ModelMap model;




    // Container Activity must implement this interface
    public interface OnCreateFragmentListener {
        void onBackFromCreate(ModelMap model);
    }




    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        try {
            mCallback = (OnCreateFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnCreateFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_mind_map, container, false);

        activity = (AppCompatActivity) getActivity();


        currentFocusView = getActivity().getCurrentFocus();

        model = new ModelMap();
        model.setColor(getResources().getColor(ColorManager.getRandomColor()));



        if (currentFocusView != null) {
            imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        }

        setUI(rootView);




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
        tvMindMapSubjectText = (TextView) rootView.findViewById(R.id.tv_create_mind_map_subject_text);
        ibMindMapSubjectIcon = (ImageButton) rootView.findViewById(R.id.et_create_mind_map_subject_icon);
        rvHelloLayout = (RelativeLayout) rootView.findViewById(R.id.hello_layout);
        rvHelloHidden = (RelativeLayout) rootView.findViewById(R.id.hello_hidden);
        glColorPickerGrid = (GridLayout) rootView.findViewById(R.id.color_grid);
        tvColorPicker = (TextView) rootView.findViewById(R.id.hello_text);
        cvCurrentColor = (CircleImageView) rootView.findViewById(R.id.cv_current_color);

        cvCurrentColor.setColorFilter(model.getColor());

        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        ToolbarHelper.setCreateMindMapToolbar(toolbar);
        toolbar.setTitle("New map");





        ibMindMapSubjectIcon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        ibMindMapSubjectIcon.setColorFilter(getResources().getColor(R.color.colorAccent));
                        break;
                    case MotionEvent.ACTION_UP:

                        final ChooseSubjectDialogFragment dialog = new ChooseSubjectDialogFragment();
                        dialog.show(getFragmentManager(), null);

                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {

                                ibMindMapSubjectIcon.setColorFilter(getResources().getColor(R.color.colorTitleOnWhiteBackground));

                                dialog.setOnChooseSubjectListener(new ChooseSubjectDialogFragment.ChooseSubjectDialogListener() {
                                    @Override
                                    public void onSubjectDialogPositiveClick(ModelMap.Subject subject) {
                                        tvMindMapSubjectText.setText(subject.name());
                                        ibMindMapSubjectIcon.setImageDrawable(subject.getSmallIcon(getActivity()));
                                        CreateMindMapFragment.this.mSubject = subject;
                                    }
                                });
                            }
                        }, 50);


                        break;
                    case MotionEvent.ACTION_CANCEL:
                        ibMindMapSubjectIcon.setColorFilter(getResources().getColor(R.color.colorTitleOnWhiteBackground));
                        break;

                }
                return false;
            }
        });

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



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setName(etMindMapName.getText().toString());
                model.setDescription(etMindMapDescription.getText().toString());
                model.setId(Calendar.getInstance().getTimeInMillis());
                Log.d("create fragment", "id " + model.getId());
                model.setLastModified(Calendar.getInstance().getTimeInMillis());
                if (CreateMindMapFragment.this.mSubject != null) {
                    model.setSubject(CreateMindMapFragment.this.mSubject);
                }

                mCallback.onBackFromCreate(model);
            }
        });

        rvHelloHidden.setVisibility(View.GONE);

        tvColorPicker.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
            if (Expander.isExpanded && rvHelloLayout != null) {
                Expander.collapse(rvHelloLayout);
            }
        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
        }
    }
}
