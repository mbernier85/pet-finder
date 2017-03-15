/*
 *   This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * If it is not possible or desirable to put the notice in a particular
 * file, then You may include the notice in a location (such as a LICENSE
 * file in a relevant directory) where a recipient would be likely to look
 * for such a notice.
 *
 * You may add additional accurate notices of copyright ownership.
 */

package im.bernier.petfinder;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.android.gms.location.places.Place;
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

    public static Bundle PlaceToBundle(Place place) {
        Bundle bundle = new Bundle();
        bundle.putString("place_address", place.getAddress().toString());
        bundle.putString("place_name", place.getName().toString());
        return bundle;
    }

    private Analytics() {

    }

}
