package me.codpoe.note.model.entity;

import io.realm.RealmObject;

/**
 * Created by Codpoe on 2016/12/6.
 */

public class Category extends RealmObject {

    public String mCategory;

    public String getCategory() {
        return mCategory;
    }
    public void setCategory(String category) {
        mCategory = category;
    }

}
