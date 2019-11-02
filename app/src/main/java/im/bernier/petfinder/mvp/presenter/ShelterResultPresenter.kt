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
import im.bernier.petfinder.model.ShelterResult
import im.bernier.petfinder.model.ShelterSearch
import im.bernier.petfinder.mvp.view.ShelterResultView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/**
 * Created by Michael on 2016-10-30.
 */

class ShelterResultPresenter(private val view: ShelterResultView) : Presenter {
    private val analytics = Analytics

    override fun onAttach() {
        val shelterSearch = Storage.shelterSearch
        if (shelterSearch == null) {
            view.doFinish()
            return
        }
        getShelters(shelterSearch)
    }

    private fun getShelters(shelterSearch: ShelterSearch) {
        view.showProgress()
        Repository.shelterFind(shelterSearch).enqueue(object : Callback<ShelterResult> {
            override fun onResponse(call: Call<ShelterResult>, response: Response<ShelterResult>) {
                val bundle = Bundle()
                if (response.isSuccessful && response.body()?.shelters != null) {
                    view.showResults(response.body()?.shelters.orEmpty())

                    response.body()?.shelters?.size?.let { bundle.putInt("number_of_results", it) }
                    analytics.track("shelter_search_result", bundle)
                } else if (response.isSuccessful) {
                    response.body()?.header?.status?.message?.let { view.showError(it) }
                    bundle.putString("error", response.body()?.header?.status?.message)
                    analytics.track("shelter_search_result", bundle)
                } else {
                    view.showError(response.message())
                    bundle.putString("error", response.message())
                    analytics.track("shelter_search_result", bundle)
                }
            }

            override fun onFailure(call: Call<ShelterResult>, t: Throwable) {
                Timber.e(t)
            }
        })
    }

    override fun onDetach() {

    }
}
