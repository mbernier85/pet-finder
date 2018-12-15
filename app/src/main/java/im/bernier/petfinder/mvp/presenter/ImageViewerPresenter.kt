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

import im.bernier.petfinder.datasource.Storage
import im.bernier.petfinder.mvp.view.ImageViewerView

/**
 * Created by Michael on 2016-07-12.
 */

class ImageViewerPresenter : Presenter {

    private var view: ImageViewerView? = null

    fun setView(view: ImageViewerView) {
        this.view = view
    }

    override fun onAttach() {
        val pet = Storage.pet
        if (pet == null) {
            view!!.doFinish()
            return
        }
        view!!.updateUi(pet)
    }

    override fun onDetach() {

    }
}
