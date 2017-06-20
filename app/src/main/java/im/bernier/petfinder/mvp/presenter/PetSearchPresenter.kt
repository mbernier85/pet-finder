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

import com.google.android.gms.location.places.Place

import java.util.ArrayList

import im.bernier.petfinder.Analytics
import im.bernier.petfinder.R
import im.bernier.petfinder.datasource.Repository
import im.bernier.petfinder.datasource.Storage
import im.bernier.petfinder.model.Breeds
import im.bernier.petfinder.model.Search
import im.bernier.petfinder.mvp.view.PetSearchView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/**
 * Created by Michael on 2016-07-12.
 */

class PetSearchPresenter : Presenter {

    private var view: PetSearchView? = null
    private val analytics = Analytics.instance
    private val animals = arrayOf("cat", "dog", "rabbit", "smallfurry", "horse", "bird", "reptile", "pig", "barnyard")

    fun setView(view: PetSearchView) {
        this.view = view
    }

    override fun onAttach() {
        view?.setAnimalsSpinner(animals)
    }

    override fun onDetach() {

    }

    fun search(search: Search) {
        if (search.location.orEmpty().length > 4) {
            Storage.instance.search = search
            view?.showResults()
            analytics.track("pet_search_click", search.bundle)
        } else {
            view?.showError(R.string.empty_zip_error)
        }
    }

    fun placeSelected(place: Place) {
        analytics.track("shelter_place_selected", Analytics.instance.placeToBundle(place))
    }

    fun loadBreed(animal: String?) {
        if (animal == null) {
            view?.updateBreeds(ArrayList<String>())
        } else {
            val call = Repository.instance.loadBreeds(animal)
            call.enqueue(object : Callback<Breeds> {
                override fun onResponse(call: Call<Breeds>, response: Response<Breeds>) {
                    if (response.isSuccessful && response.body()?.header?.status?.code == 100) {
                        view?.updateBreeds(response.body()?.breeds)
                    } else if (response.body()?.header?.status?.code != 200) {
                        view?.showError(response.body()?.header?.status?.message)
                    }
                }

                override fun onFailure(call: Call<Breeds>, t: Throwable) {
                    Timber.e(t)
                }
            })
        }
    }

}
