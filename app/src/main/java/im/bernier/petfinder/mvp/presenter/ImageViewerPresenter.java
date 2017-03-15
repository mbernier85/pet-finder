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

package im.bernier.petfinder.mvp.presenter;

import im.bernier.petfinder.datasource.Storage;
import im.bernier.petfinder.model.Pet;
import im.bernier.petfinder.mvp.view.ImageViewerView;

/**
 * Created by Michael on 2016-07-12.
 */

public class ImageViewerPresenter implements Presenter {

    private ImageViewerView view;

    public void setView(ImageViewerView view) {
        this.view = view;
    }

    @Override
    public void onAttach() {
        Pet pet = Storage.getInstance().getPet();
        view.updateUi(pet);
    }

    @Override
    public void onDetach() {

    }
}
