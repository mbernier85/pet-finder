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

import im.bernier.petfinder.Analytics
import im.bernier.petfinder.datasource.Repository
import im.bernier.petfinder.datasource.Storage
import im.bernier.petfinder.model.Pet
import im.bernier.petfinder.model.Search
import im.bernier.petfinder.model.SearchResult
import im.bernier.petfinder.mvp.view.ResultView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/**
 * Created by Michael on 2016-07-09.
 */

class PetResultPresenter : Presenter {

    private var view: ResultView? = null
    private val analytics = Analytics

    override fun onAttach() {
        val search = Storage.instance.search
        if (search == null) {
            view?.doFinish()
            return
        }
        findPet(search)
    }

    fun setView(view: ResultView) {
        this.view = view
    }

    override fun onDetach() {

    }

    fun onPetClick(pet: Pet) {
        Storage.instance.pet = pet
        view?.openPet(pet)
    }

    private fun findPet(search: Search) {
        val searchResultCall = Repository.instance.petFind(search)
        searchResultCall.enqueue(object : Callback<SearchResult> {
            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                val bundle = Bundle()
                if (response.isSuccessful && response.body()?.header?.status?.code == 100) {
                    response.body()?.pets?.let { view?.updateResults(it) }

                    response.body()?.pets?.size?.let { bundle.putInt("number_of_results", it) }
                    analytics.track("pet_search_result", bundle)
                } else if (response.isSuccessful) {
                    response.body()?.header?.status?.message?.let { view?.showError(it) }
                    bundle.putString("error", response.body()?.header?.status?.message)
                    analytics.track("pet_search_result", bundle)
                } else {
                    view?.showError(response.message())
                    bundle.putString("error", response.message())
                    analytics.track("pet_search_result", bundle)
                }
            }

            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                Timber.e(t, "findPet failed")
            }
        })
    }
}
