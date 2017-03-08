package me.codpoe.note.editor.edit;

import io.realm.RealmResults;
import me.codpoe.note.model.Repository;
import me.codpoe.note.model.entity.Note;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Codpoe on 2016/12/7.
 */

public class EditPresenter implements EditContract.Presenter {

    private EditContract.View mView;
    private Repository mRepository;
    private CompositeSubscription mSubscription;

    public EditPresenter(EditContract.View view) {
        mView = view;
        mRepository = Repository.getInstance();
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void loadNote(String createTime) {
        mSubscription.add(mRepository.queryNoteByCreateTime(createTime).asObservable()
                .subscribe(new Action1<RealmResults<Note>>() {
                    @Override
                    public void call(RealmResults<Note> notes) {
                        mView.showNote(notes.get(0));
                    }
                }));
    }

    @Override
    public void updateNote(String createTime, String field, String newValue) {
        mRepository.update(createTime, field, newValue);
    }

    @Override
    public void insertNote(Note note) {
        mRepository.insertNote(note);
    }

    @Override
    public void detach() {
        mSubscription.unsubscribe();
    }
}
