package me.codpoe.note.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.codpoe.note.R;
import me.codpoe.note.main.snote.SnoteFragment;
import me.codpoe.note.util.ActivityUtil;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_MEMO_FRAG = "memo_frag";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.frag_container)
    FrameLayout mFragContainer;
    @BindView(R.id.coor_lay)
    CoordinatorLayout mCoorLay;
    @BindView(R.id.drawer_nav)
    NavigationView mDrawerNav;
    @BindView(R.id.drawer_Lay)
    DrawerLayout mDrawerLay;

    SnoteFragment mSnoteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        // drawer toggle
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLay, mToolbar, 0, 0) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerToggle.syncState();
        mDrawerLay.addDrawerListener(drawerToggle);

        // drawer nav
        setupDrawerNav();

        // set up fragment
        mSnoteFragment = (SnoteFragment) getSupportFragmentManager().findFragmentByTag(TAG_MEMO_FRAG);
        if (mSnoteFragment == null) {
            mSnoteFragment = SnoteFragment.newInstance();
            ActivityUtil.addFragmentToActivity(
                    getSupportFragmentManager(), R.id.frag_container, mSnoteFragment, TAG_MEMO_FRAG);
        }

    }

    private void setupDrawerNav() {
        mDrawerNav.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.memo:
                                // TODO: 2016/12/6
                                break;
                            case R.id.note:
                                // TODO: 2016/12/6
                                break;
                            default:
                                break;
                        }
                        item.setChecked(true);
                        mDrawerLay.closeDrawers();
                        return true;
                    }
                }
        );
    }
}
