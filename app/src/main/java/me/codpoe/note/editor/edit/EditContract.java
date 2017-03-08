package me.codpoe.note.editor.edit;

import me.codpoe.note.model.entity.Note;

/**
 * Created by Codpoe on 2016/12/7.
 */

public interface EditContract {

    interface View {
        void showNote(Note note);
        void showMsg(String msg);
    }

    interface Presenter {
        void loadNote(String createTime);
        void updateNote(String createTime, String field, String newValue);
        void insertNote(Note note);
        void detach();
    }

}
