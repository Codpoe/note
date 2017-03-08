package me.codpoe.note.main.snote.todo;

import io.realm.RealmResults;
import io.realm.Sort;
import me.codpoe.note.model.Repository;
import me.codpoe.note.model.entity.Note;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Codpoe on 2016/12/6.
 */

public class TodoPresenter implements TodoContract.Presenter {

    private TodoContract.View mView;
    private Repository mRepository;

    private CompositeSubscription mSubscription;

    public TodoPresenter(TodoContract.View view) {
        mView = view;
        mRepository = Repository.getInstance();
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void insert(Note note) {
        mRepository.insertNote(note);
    }

    @Override
    public void delete(String name) {
        mRepository.deleteNoteByCreateTime(name);
    }

    @Override
    public void query(boolean done, String sortField, Sort sortOrder) {
        mSubscription.add(mRepository.queryNoteSorted(done, sortField, sortOrder).asObservable()
                .subscribe(new Action1<RealmResults<Note>>() {
                    @Override
                    public void call(RealmResults<Note> notes) {
                        if (notes.size() > 0) {
                            mView.showTodo(notes);
                        } else {
                            mView.showNoContent();
                        }
                    }
                }));
    }

    @Override
    public void detach() {
        mSubscription.unsubscribe();
    }
}
