package me.codpoe.note.editor;

import me.codpoe.note.model.Repository;

/**
 * Created by Codpoe on 2016/12/11.
 */

public class EditorPresenter implements EditorContract.Presenter {

    private EditorContract.View mView;
    private Repository mRepository;

    public EditorPresenter(EditorContract.View view) {
        mView = view;
        mRepository = Repository.getInstance();
    }

    @Override
    public void loadNote(String createTiem) {

    }

    @Override
    public void updateNote(String createTime, String field, String newValue) {
        mRepository.update(createTime, field, newValue);
    }
}
