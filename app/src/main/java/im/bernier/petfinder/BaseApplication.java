package im.bernier.petfinder;

import android.app.Application;

import com.google.firebase.analytics.FirebaseAnalytics;

import timber.log.Timber;

/**
 * Created by Michael on 2016-07-09.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        Analytics.getInstance().init(FirebaseAnalytics.getInstance(this));
    }
}
