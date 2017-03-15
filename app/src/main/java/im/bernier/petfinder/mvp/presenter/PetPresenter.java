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

import im.bernier.petfinder.Analytics;
import im.bernier.petfinder.datasource.Storage;
import im.bernier.petfinder.model.Pet;
import im.bernier.petfinder.mvp.view.PetView;

/**
 * Created by Michael on 2016-07-09.
 */

public class PetPresenter implements Presenter {

    private PetView view;
    private Pet pet;
    private Analytics analytics = Analytics.getInstance();

    public void setView(PetView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onAttach() {
        pet = Storage.getInstance().getPet();
        view.updateUi(pet);
    }

    public void onImageClick() {
        view.openImageViewer(pet);
        analytics.track("pet_image_click", pet.toBundle());
    }

    public void phoneClick() {
        view.openDialer(pet);
        analytics.track("pet_phone_click", pet.toBundle());
    }

    public void emailClick() {
        view.openEmail(pet);
        analytics.track("pet_email_click", pet.toBundle());
    }

    public void addressClick() {
        view.openMap(pet);
        analytics.track("pet_address_click", pet.toBundle());
    }
}
