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

package im.bernier.petfinder.mvp.presenter

import android.os.Bundle

import com.google.android.gms.location.places.Place

import im.bernier.petfinder.Analytics
import im.bernier.petfinder.R
import im.bernier.petfinder.datasource.Storage
import im.bernier.petfinder.model.ShelterSearch
import im.bernier.petfinder.mvp.view.ShelterSearchView

/**
 * Created by Michael on 2016-10-29.
 */

class ShelterSearchPresenter(private val view: ShelterSearchView) : Presenter {
    private val analytics = Analytics.instance

    fun submit(location: String, name: String) {
        if (location.length > 3) {
            Storage.instance.shelterSearch = ShelterSearch(location, name)
            view.openShelter()

            val bundle = Bundle()
            bundle.putString("location", location)
            bundle.putString("shelter_name", name)
            analytics.track("search_shelter_click", bundle)
        } else {
            view.showError(R.string.empty_zip_error)
        }
    }

    fun placeSelected(place: Place) {
        analytics.track("shelter_place_selected", Analytics.instance.placeToBundle(place))
    }

    override fun onAttach() {

    }

    override fun onDetach() {

    }
}
