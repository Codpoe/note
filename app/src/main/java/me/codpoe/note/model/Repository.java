package me.codpoe.note.model;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import me.codpoe.note.model.entity.Note;

/**
 * Created by Codpoe on 2016/12/6.
 */

public class Repository {

    private static Repository sRepository;
    private Realm mRealm;

    private Repository() {
        mRealm = Realm.getDefaultInstance();
    }

    public synchronized static Repository getInstance() {
        if (sRepository == null) {
            sRepository = new Repository();
        }
        return sRepository;
    }

    public void insertNote(final Note note) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mRealm.copyToRealmOrUpdate(note);
            }
        });
    }

    public void deleteNoteByCreateTime(final String createTime) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mRealm.where(Note.class).equalTo("createTime", createTime).findAll().deleteFirstFromRealm();
            }
        });
    }

    public void deleteAllNote() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mRealm.where(Note.class).findAll().deleteAllFromRealm();
            }
        });
    }

    public RealmResults<Note> queryNote(boolean done) {
        return mRealm.where(Note.class).equalTo("done", done).findAll();
    }

    public RealmResults<Note> queryNoteSorted(boolean done, String sortField, Sort sortOrder) {
        return mRealm.where(Note.class).equalTo("done", done).findAllSorted(sortField, sortOrder);
    }
    public RealmResults<Note> queryNoteByCreateTime(String createTime) {
        return mRealm.where(Note.class).equalTo("createTime", createTime).findAll();
    }

    public void update(String createTime, final String field, final String newValue) {
        final Note note = mRealm.where(Note.class).equalTo("createTime", createTime).findFirst();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                switch (field) {
                    case "name":
                        note.setName(newValue);
                        break;
                    case "content":
                        note.setContent(newValue);
                        break;
                    case "modifyTime":
                        note.setModifyTime(newValue);
                        break;
                    case "category":
                        note.setCategory(newValue);
                        break;
                    case "tag":
                        note.setTag(newValue);
                        break;
                    case "theme":
                        note.setTheme(newValue);
                        break;
                    case "color":
                        note.setColor(Integer.parseInt(newValue));
                        break;
                    case "done":
                        note.setDone(Boolean.parseBoolean(newValue));
                        break;
                    default:
                        break;
                }
            }
        });
    }

}
