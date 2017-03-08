package me.codpoe.note.editor.preview;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.codpoe.note.R;
import me.codpoe.note.widget.MarkdownView;

public class PreviewFragment extends Fragment implements PreviewContract.View {

    private static final String ARG_CREATE_TIME = "create_time";
    private static final String ARG_CURRENT_THEME = "current_theme";

    @BindView(R.id.markdown_view)
    MarkdownView mMarkdownView;

    private String mContent;
    private String mCreateTime;
    private String mCurrentTheme;

    public PreviewFragment() {
        // Required empty public constructor
    }

    public static PreviewFragment newInstance(String createTime, String currentTheme) {
        PreviewFragment fragment = new PreviewFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_preview, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (mContent != null) {
            mMarkdownView.parse(mContent, mCurrentTheme);
        }
    }

    @Override
    public void showMarkdown(String content) {
        mContent = content;
        if (mMarkdownView != null) {
            mMarkdownView.parse(mContent, mCurrentTheme);
        }
    }

    @Override
    public void showMsg(String msg) {

    }

    public void changeTheme(String theme) {
        mCurrentTheme = theme;
        mMarkdownView.changeTheme(mCurrentTheme);
    }
}
