package me.codpoe.note.editor.preview;

/**
 * Created by Codpoe on 2016/12/8.
 */

public interface PreviewContract {

    interface View {
        void showMarkdown(String content);
        void showMsg(String msg);
    }

    interface Presenter {
        void loadNote(String createTime);
        void detach();
    }
}
