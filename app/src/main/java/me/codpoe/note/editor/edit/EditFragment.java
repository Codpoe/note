package me.codpoe.note.editor.edit;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.codpoe.note.R;
import me.codpoe.note.editor.preview.PreviewFragment;
import me.codpoe.note.model.entity.Note;
import me.codpoe.note.util.TimeUtil;

public class EditFragment extends Fragment implements EditContract.View {

    private static final String ARG_CREATE_TIME = "create_time";
    private static final String ARG_CURRENT_THEME = "current_theme";

    @BindView(R.id.name_edit)
    EditText mNameEdit;
    @BindView(R.id.content_edit)
    EditText mContentEdit;
    @BindView(R.id.scroll_view)
    NestedScrollView mScrollView;

    private TextView mModifyTv;
    private TextView mLightTv;
    private TextView mDarkTv;

    private EditContract.Presenter mPresenter;
    private String mCreateTime;
    private String mCurrentTheme;
    private boolean mShouldUpdateContent = true;
    private boolean mShouldUpdateModify = false;
    private PreviewFragment mPreviewFragment;

    public EditFragment() {

    }

    public static EditFragment newInstance(String createTime, String currentTheme) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CREATE_TIME, createTime);
        args.putString(ARG_CURRENT_THEME, currentTheme);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCreateTime = getArguments().getString(ARG_CREATE_TIME);
            mCurrentTheme = getArguments().getString(ARG_CURRENT_THEME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        ButterKnife.bind(this, view);

        mModifyTv = (TextView) getActivity().findViewById(R.id.modify_tv);
        mPreviewFragment = (PreviewFragment) getFragmentManager().getFragments().get(1);
        mPresenter = new EditPresenter(this);

        setTheme(mCurrentTheme);
        setupEditText();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPresenter.loadNote(mCreateTime);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detach();
        mPresenter = null;
    }

    @Override
    public void showNote(Note note) {
        if (mShouldUpdateContent) {
            mShouldUpdateContent = false;
            mModifyTv.setText(String.format(getResources().getString(R.string.modify_at),
                    TimeUtil.formatModifyTime(note.getModifyTime())));
            mNameEdit.setText(note.getName());
            mContentEdit.setText(note.getContent());
        } else if (!mShouldUpdateModify){
            mShouldUpdateModify = true;
        } else {
            mModifyTv.setText(String.format(getResources().getString(R.string.modify_at),
                    TimeUtil.formatModifyTime(note.getModifyTime())));
        }
    }

    @Override
    public void showMsg(String msg) {

    }

    private void setupEditText() {

        mNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("text", "here");
                mPresenter.updateNote(mCreateTime, "name", editable.toString());
                mPresenter.updateNote(mCreateTime, "modifyTime", String.valueOf(System.currentTimeMillis()));
            }
        });

        mContentEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPreviewFragment.showMarkdown(editable.toString());
                mPresenter.updateNote(mCreateTime, "content", editable.toString());
                mPresenter.updateNote(mCreateTime, "modifyTime", String.valueOf(System.currentTimeMillis()));
            }
        });
    }

    public void setTheme(String theme) {
        mCurrentTheme = theme;
        switch (theme) {
            case "light":
                setThemeByColors(R.color.theme_light_bg, R.color.theme_light_text);
                break;
            case "dark":
                setThemeByColors(R.color.theme_dark_bg, R.color.theme_dark_text);
                break;
            case "wood":
                setThemeByColors(R.color.theme_wood_bg, R.color.theme_light_text);
                break;
            case "paper":
                setThemeByColors(R.color.theme_paper_edit_bg, R.color.theme_light_text);
                break;
            case "pink":
                setThemeByColors(R.color.theme_pink_edit_bg, R.color.theme_light_text);
                break;
            case "purple":
                setThemeByColors(R.color.theme_purple_edit_bg, R.color.theme_light_text);
                break;
            case "green":
                setThemeByColors(R.color.theme_green_edit_bg, R.color.theme_light_text);
                break;
            case "cyan":
                setThemeByColors(R.color.theme_cyan_edit_bg, R.color.theme_light_text);
                break;
        }
    }

    private void setThemeByColors(int backgroundColor, int textColor) {
        mScrollView.setBackgroundColor(getResources().getColor(backgroundColor));
        mNameEdit.setTextColor(getResources().getColor(textColor));
        mContentEdit.setTextColor(getResources().getColor(textColor));
    }

    private void setThemeByImg(int backgroundImg, int textColor) {
        Glide.with(this)
                .load(backgroundImg)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        mScrollView.setBackground(resource);
                    }
                });
        mNameEdit.setTextColor(getResources().getColor(textColor));
        mContentEdit.setTextColor(getResources().getColor(textColor));
    }

}
