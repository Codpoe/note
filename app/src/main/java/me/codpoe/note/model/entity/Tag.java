package me.codpoe.note.model.entity;

import io.realm.RealmObject;

/**
 * Created by Codpoe on 2016/12/6.
 */

public class Tag extends RealmObject {

    public String mTag;

    public String getTag() {
        return mTag;
    }
    public void setTag(String tag) {
        mTag = tag;
    }

}
