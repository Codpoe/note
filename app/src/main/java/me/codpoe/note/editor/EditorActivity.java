package me.codpoe.note.editor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.codpoe.library.ArrowView;
import me.codpoe.note.R;
import me.codpoe.note.editor.edit.EditFragment;
import me.codpoe.note.editor.preview.PreviewFragment;
import me.codpoe.note.main.ViewPagerAdapter;
import me.codpoe.note.model.entity.Note;

public class EditorActivity extends AppCompatActivity implements EditorContract.View {

    private static final String EXTRA_CREATE_TIME = "create_time";
    private static final String EXTRA_THEME = "theme";
    private static final String EDITOR = "editor";
    private static final String EDITOR_THEME = "editor_theme";

    @BindView(R.id.editor_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.editor_pager)
    ViewPager mPager;
    @BindView(R.id.modify_tv)
    TextView mModifyTv;
    @BindView(R.id.arrow_view)
    ArrowView mArrowView;
    @BindView(R.id.theme_rv)
    RecyclerView mThemeRv;

    private EditorContract.Presenter mPresenter;
    private BottomSheetBehavior mBottomSheet;
    private View mBottomSheetView;
    private String mCreateTime;
    private EditFragment mEditFragment;
    private PreviewFragment mPreviewFragment;
    String[] mThemes = new String[] {
            "light",
            "dark",
            "green",
            "cyan",
            "pink",
            "purple",
            "wood",
            "paper"
    };
    private String mCurrentTheme;
    private int mCurrentPage = 0; // current view pager
    private int mCurrentSelected; // current position of the theme recyclerview in the bottom sheet

    public static Intent newIntent(Context context, String createTime, String theme) {
        Intent intent = new Intent(context, EditorActivity.class);
        intent.putExtra(EXTRA_CREATE_TIME, createTime);
        intent.putExtra(EXTRA_THEME, theme);
        return intent;
    }

    private void parseIntent() {
        mCreateTime = getIntent().getStringExtra(EXTRA_CREATE_TIME);
        mCurrentTheme = getIntent().getStringExtra(EXTRA_THEME);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        ButterKnife.bind(this);
        parseIntent();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        mBottomSheetView = findViewById(R.id.bottom_sheet);
        mPresenter = new EditorPresenter(this);

        setTheme(mCurrentTheme);
        setupToolbar();
        setupViewPager();
        setupBottomSheet();
        setupThemeRv();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter = null;
    }

    private void setupToolbar() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setupViewPager() {
        mEditFragment = EditFragment.newInstance(mCreateTime, mCurrentTheme);
        mPreviewFragment = PreviewFragment.newInstance(mCreateTime, mCurrentTheme);
        final ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        pagerAdapter.addFragment(mEditFragment, getString(R.string.edit));
        pagerAdapter.addFragment(mPreviewFragment, getString(R.string.preview));
        mPager.setAdapter(pagerAdapter);

        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mBottomSheet.setHideable(false);
                        mBottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        break;
                    case 1:
                        mBottomSheet.setHideable(true);
                        mBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
                        break;
                }
                if (mCurrentPage != position) {
                    mCurrentPage = position;
                    mToolbar.setTitle(pagerAdapter.getPageTitle(position));
                }
            }
        });

    }

    private void setupBottomSheet() {
        mBottomSheet = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        mBottomSheet.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                mArrowView.setOffset(slideOffset);
            }
        });
        mArrowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBottomSheet.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    mBottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
    }

    private void setupThemeRv() {
        String[] themeNames = new String[] {
                getString(R.string.light_theme),
                getString(R.string.dark_theme),
                getString(R.string.green_theme),
                getString(R.string.cyan_theme),
                getString(R.string.pink_theme),
                getString(R.string.purple_theme),
                getString(R.string.wood_theme),
                getString(R.string.paper_theme)
        };
        int[] themeImgs = new int[] {
                R.color.theme_light_bg,
                R.color.theme_dark_bg,
                R.color.theme_green_edit_bg,
                R.color.theme_cyan_edit_bg,
                R.color.theme_pink_edit_bg,
                R.color.theme_purple_edit_bg,
                R.drawable.wood,
                R.drawable.paper
        };
        final ThemeRvAdapter themeRvAdapter = new ThemeRvAdapter(this, themeNames, themeImgs, mCurrentSelected);
        mThemeRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mThemeRv.setAdapter(themeRvAdapter);
        themeRvAdapter.setOnItemClickListener(new ThemeRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!mCurrentTheme.equals(mThemes[position])) {
                    mCurrentTheme = mThemes[position];
                    mCurrentSelected = position;
                    mPresenter.updateNote(mCreateTime, "theme", mCurrentTheme);
                    setTheme(mCurrentTheme);
                    mEditFragment.setTheme(mCurrentTheme);
                    mPreviewFragment.changeTheme(mCurrentTheme);
                }
            }
        });
    }

    @Override
    public void handleNote(Note note) {

    }

    private void setTheme(String theme) {
        switch (theme) {
            case "light":
                mCurrentSelected = 0;
                setThemeByColors(R.color.theme_light_status_bar, R.color.theme_light_bg);
                break;
            case "dark":
                mCurrentSelected = 1;
                setThemeByColors(R.color.theme_dark_status_bar, R.color.theme_dark_bg);
                break;
            case "green":
                mCurrentSelected = 2;
                setThemeByColors(R.color.theme_green_status_bar, R.color.theme_green_bg);
                break;
            case "cyan":
                mCurrentSelected = 3;
                setThemeByColors(R.color.theme_cyan_status_bar, R.color.theme_cyan_bg);
                break;
            case "pink":
                mCurrentSelected = 4;
                setThemeByColors(R.color.theme_pink_status_bar, R.color.theme_pink_bg);
                break;
            case "purple":
                mCurrentSelected = 5;
                setThemeByColors(R.color.theme_purple_status_bar, R.color.theme_purple_bg);
                break;
            case "wood":
                mCurrentSelected = 6;
                setThemeByColors(R.color.theme_wood_status_bar, R.color.theme_wood_bg);
                break;
            case "paper":
                mCurrentSelected = 7;
                setThemeByColors(R.color.theme_paper_status_bar, R.color.theme_paper_bg);
                break;
        }
    }

    private void setThemeByColors(int statusBarColor, int backgroundColor) {
        getWindow().setStatusBarColor(getResources().getColor(statusBarColor));
        mToolbar.setBackgroundColor(getResources().getColor(backgroundColor));
        mBottomSheetView.setBackgroundColor(getResources().getColor(backgroundColor));
    }
}
