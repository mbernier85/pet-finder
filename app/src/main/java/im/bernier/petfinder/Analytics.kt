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

package im.bernier.petfinder

import android.os.Bundle

import com.google.android.gms.location.places.Place
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * Created by Michael on 2017-02-07.
 */

object Analytics {
    private var firebaseAnalytics: FirebaseAnalytics? = null

    fun track(key: String, bundle: Bundle? = null) {
        firebaseAnalytics?.logEvent(key, bundle)
    }

    fun track(key: String) {
        track(key, null)
    }

    fun init(firebaseAnalytics: FirebaseAnalytics) {
        this.firebaseAnalytics = firebaseAnalytics
    }

    fun placeToBundle(place: Place): Bundle {
        val bundle = Bundle()
        bundle.putString("place_address", place.address.toString())
        bundle.putString("place_name", place.name.toString())
        return bundle
    }
}
