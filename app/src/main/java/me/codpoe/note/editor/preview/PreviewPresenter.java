package me.codpoe.note.editor.preview;

import io.realm.RealmResults;
import me.codpoe.note.model.Repository;
import me.codpoe.note.model.entity.Note;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Codpoe on 2016/12/8.
 */

public class PreviewPresenter implements PreviewContract.Presenter {

    private PreviewContract.View mView;
    private Repository mRepository;
    private CompositeSubscription mSubscription;

    public PreviewPresenter(PreviewContract.View view) {
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
                        mView.showMarkdown(notes.get(0).getContent());
                    }
                }));
    }

    @Override
    public void detach() {
        mSubscription.unsubscribe();
    }
}
