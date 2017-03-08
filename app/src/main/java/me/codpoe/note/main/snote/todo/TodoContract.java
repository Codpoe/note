package me.codpoe.note.main.snote.todo;

import java.util.List;

import io.realm.Sort;
import me.codpoe.note.model.entity.Note;

/**
 * Created by Codpoe on 2016/12/6.
 */

public interface TodoContract {

    interface View {
        void showTodo(List<Note> noteList);
        void showNoContent();
        void showMsg(String msg);
    }

    interface Presenter {
        void insert(Note note);
        void delete(String name);
        void query(boolean done, String sortField, Sort sortOrder);
        void detach();
    }

}
