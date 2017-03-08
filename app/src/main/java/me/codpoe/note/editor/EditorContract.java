package me.codpoe.note.editor;

import me.codpoe.note.model.entity.Note;

/**
 * Created by Codpoe on 2016/12/11.
 */

public interface EditorContract {

    interface View {
        void handleNote(Note note);
    }

    interface Presenter {
        void loadNote(String createTiem);
        void updateNote(String createTime, String field, String newValue);
    }

}
