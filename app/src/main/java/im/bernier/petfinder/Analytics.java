package im.bernier.petfinder;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by Michael on 2017-02-07.
 */

public class Analytics {

    private static Analytics instance;

    private FirebaseAnalytics firebaseAnalytics;

    public static @NonNull Analytics getInstance() {
        if (instance == null) {
            instance = new Analytics();
        }
        return instance;
    }

    public void track(@NonNull String key) {
        track(key, new Bundle());
    }

    public void track(@NonNull String key, @NonNull Bundle bundle) {
        if (firebaseAnalytics == null) {
            throw new RuntimeException("Analytics not initialized");
        }
        firebaseAnalytics.logEvent(key, bundle);
    }

    void init(@NonNull FirebaseAnalytics firebaseAnalytics) {
        this.firebaseAnalytics = firebaseAnalytics;
    }

    private Analytics() {

    }

}
