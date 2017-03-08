package me.codpoe.note.app;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Codpoe on 2016/12/6.
 */

public class NoteApplication extends Application {

    private static final String DB_NAME = "note.realm";

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder().name(DB_NAME).build());
    }
}
