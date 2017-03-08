package me.codpoe.note.main.snote.todo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Sort;
import me.codpoe.note.R;
import me.codpoe.note.editor.EditorActivity;
import me.codpoe.note.main.snote.SnoteRvAdapter;
import me.codpoe.note.model.entity.Note;

public class TodoFragment extends Fragment implements TodoContract.View,
        SnoteRvAdapter.OnItemClickListener {

    @BindView(R.id.todo_rv)
    RecyclerView mTodoRv;
    @BindView(R.id.add_fab)
    FloatingActionButton mAddFab;

    private TodoContract.Presenter mPresenter;
    private List<Note> mNoteList;
    private SnoteRvAdapter mRvAdapter;
    private View mNoContentView;

    public TodoFragment() {
        // Required empty public constructor
    }

    public static TodoFragment newInstance() {
        return new TodoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_todo, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new TodoPresenter(this);

        setupFab();

        mNoteList = new ArrayList<>();
        mRvAdapter = new SnoteRvAdapter(getContext(), mNoteList);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mTodoRv.setLayoutManager(layoutManager);
        mTodoRv.setAdapter(mRvAdapter);
        mRvAdapter.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        Note note1 = new Note();
//        note1.setCreateTime(String.valueOf(System.currentTimeMillis()));
//        note1.setModifyTime(String.valueOf(System.currentTimeMillis()));
//        note1.setName("测试标题-5");
//        note1.setContent("测试内容\n第二行\n3");
//        note1.setTheme("purple");
//        note1.setDone(false);
//        mPresenter.insert(note1);
//
//        Note note2 = new Note();
//        note2.setCreateTime(String.valueOf(System.currentTimeMillis()));
//        note2.setModifyTime(String.valueOf(System.currentTimeMillis()));
//        note2.setName("测试标题-6");
//        note2.setContent("测试内容-2\n第二行\n3\n4\n5\n6");
//        note2.setTheme("wood");
//        note2.setDone(false);
//        mPresenter.insert(note2);

        Log.d("test", Math.random() + "");

        mPresenter.query(false, "createTime", Sort.DESCENDING);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detach();
        mPresenter = null;
    }

    @Override
    public void showTodo(List<Note> noteList) {
        mNoteList.clear();
        mNoteList.addAll(noteList);
        mRvAdapter.notifyDataSetChanged();
        if (mNoContentView != null) {
            mNoContentView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showNoContent() {
        if (mNoContentView == null) {
            ViewStub viewStub = (ViewStub) getView().findViewById(R.id.no_content_stub);
            mNoContentView = viewStub.inflate();
        }
        mNoContentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMsg(String msg) {

    }

    private void setupFab() {
        mAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String createTime = String.valueOf(System.currentTimeMillis());
                Note note = new Note();
                note.setCreateTime(createTime);
                note.setModifyTime(createTime);
                startActivity(EditorActivity.newIntent(getContext(), note.getCreateTime(), note.getTheme()));
                mPresenter.insert(note);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        startActivity(EditorActivity.newIntent(
                getContext(), mNoteList.get(position).getCreateTime(), mNoteList.get(position).getTheme()));
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
