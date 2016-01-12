package info.fandroid.mindmap.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.fandroid.mindmap.R;
import info.fandroid.mindmap.controller.adapter.MindMapListAdapter;
import info.fandroid.mindmap.database.DBManager;
import info.fandroid.mindmap.model.ModelMap;
import info.fandroid.mindmap.util.FragmentManagerHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class MindMapListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MindMapListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    FragmentManagerHelper fragmentManagerHelper;

    private DBManager mDbManager;



    public MindMapListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_mind_map_list, container, false);

        fragmentManagerHelper = FragmentManagerHelper.getInstance();

        mDbManager = DBManager.getInstance();
        List<ModelMap> maps = mDbManager.query().map().getDefaultSelection();
        setUI(rootView);

        mAdapter.addMapList(maps);

        return rootView;
    }

    private void setUI(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_mind_map_list);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MindMapListAdapter();
        mRecyclerView.setAdapter(mAdapter);


    }

    public void addNewMindMap(ModelMap model) {
        mAdapter.addMindMap(model);
    }




}
