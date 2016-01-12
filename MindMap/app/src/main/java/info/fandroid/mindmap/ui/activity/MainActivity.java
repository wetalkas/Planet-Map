package info.fandroid.mindmap.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import info.fandroid.mindmap.R;
import info.fandroid.mindmap.database.DBManager;
import info.fandroid.mindmap.model.ModelMap;
import info.fandroid.mindmap.model.ModelPlanet;
import info.fandroid.mindmap.ui.fragment.CreateMindMapFragment;
import info.fandroid.mindmap.ui.fragment.MindMapListFragment;
import info.fandroid.mindmap.ui.fragment.SplashFragment;
import info.fandroid.mindmap.util.FragmentManagerHelper;
import info.fandroid.mindmap.util.ToolbarHelper;

public class MainActivity extends AppCompatActivity implements CreateMindMapFragment.OnCreateFragmentListener
        , SplashFragment.OnSplashFragmentListener {

    private FragmentManagerHelper mFragmentManagerHelper;
    private FloatingActionButton mFab;

    private Toolbar mToolbar;

    private Snackbar mSnackBarBackPress;

    private boolean doubleBackToExitPressedOnce;


    private MindMapListFragment mMindMapListFragment;

    private DBManager mDbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbManager = DBManager.getInstance();

        setUI();

        doubleBackToExitPressedOnce = false;

        FragmentManagerHelper.getInstance().init(this);
        mFragmentManagerHelper = FragmentManagerHelper.getInstance();

        SplashFragment splashFragment = new SplashFragment();


        mMindMapListFragment = new MindMapListFragment();
        //mFragmentManagerHelper.setFragment(R.id.relative_container, mMindMapListFragment, false);

        mFragmentManagerHelper.setFragment(R.id.relative_container, splashFragment, false);


    }

    @Override
    protected void onResume() {
        super.onResume();
        FragmentManagerHelper.getInstance().init(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            mDbManager.dropTable();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        } else {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;

            mSnackBarBackPress = Snackbar.make(mFab, getString(R.string.back_press_again), Snackbar.LENGTH_LONG);

            mSnackBarBackPress.show();
        }

        checkUI();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void setUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        ToolbarHelper.setMainToolbar(mToolbar);

        setSupportActionBar(mToolbar);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateMindMapFragment fragment = new CreateMindMapFragment();
                if (mSnackBarBackPress != null && mSnackBarBackPress.isShown()) {
                    mSnackBarBackPress.dismiss();
                }
                mFragmentManagerHelper.setFragment(R.id.relative_container, fragment, true);
                mFab.hide();
            }
        });

        mFab.hide();
        mToolbar.setVisibility(View.GONE);

    }


    public void checkUI() {

            ToolbarHelper.setMainToolbar(mToolbar);

        if (!mFab.isShown()) {
            mFab.show();
        }
    }

    @Override
    public void onBackFromCreate(ModelMap model) {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                onBackPressed();
            }
        }, 100);

        mMindMapListFragment.addNewMindMap(model);

        mDbManager.save().map().full(model);

        Intent intent = new Intent(this, OpenedMapActivity.class);

        intent.putExtras(ModelPlanet.mapToBundle(model));
        startActivity(intent);
        overridePendingTransition(0, 0);
    }


    @Override
    public void onSplashEnded() {
        mFragmentManagerHelper.getFragmentManager().popBackStackImmediate();
        mFragmentManagerHelper.setFragment(R.id.relative_container, mMindMapListFragment, false);
        mFab.show();
        mToolbar.setVisibility(View.VISIBLE);
    }


}
