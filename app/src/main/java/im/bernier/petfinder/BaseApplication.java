package im.bernier.petfinder;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by Michael on 2016-07-09.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
