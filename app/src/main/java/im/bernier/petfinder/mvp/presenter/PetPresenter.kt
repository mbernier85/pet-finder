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

import im.bernier.petfinder.Analytics
import im.bernier.petfinder.datasource.Storage
import im.bernier.petfinder.model.Pet
import im.bernier.petfinder.mvp.view.PetView

/**
* Created by Michael Bernier on 2016-07-09.
*/

class PetPresenter : Presenter {

    lateinit var pet: Pet
    private val analytics = Analytics.instance

    private var view: PetView? = null

    fun setView(view: PetView) {
        this.view = view
    }

    override fun onDetach() {

    }

    override fun onAttach() {
        pet = Storage.instance.pet.takeIf { it != null } ?: Pet()

        view?.updateUi(pet)
    }

    fun onImageClick() {
        analytics.track("pet_image_click", pet.toBundle())
        view?.openImageViewer()
    }

    fun phoneClick() {
        analytics.track("pet_phone_click", pet.toBundle())
        view?.openDialer(pet)
    }

    fun emailClick() {
        analytics.track("pet_email_click", pet.toBundle())
        view?.openEmail(pet)
    }

    fun addressClick() {
        analytics.track("pet_address_click", pet.toBundle())
        view?.openMap(pet)
    }
}
