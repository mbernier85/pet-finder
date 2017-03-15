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

import android.os.Bundle;

import com.google.android.gms.location.places.Place;

import im.bernier.petfinder.Analytics;
import im.bernier.petfinder.R;
import im.bernier.petfinder.datasource.Storage;
import im.bernier.petfinder.model.ShelterSearch;
import im.bernier.petfinder.mvp.view.ShelterSearchView;

/**
 * Created by Michael on 2016-10-29.
 */

public class ShelterSearchPresenter implements Presenter {

    private ShelterSearchView view;
    private Analytics analytics = Analytics.getInstance();

    public ShelterSearchPresenter(ShelterSearchView view) {
        this.view = view;
    }

    public void submit(String location, String name) {
        if (location.length() > 3) {
            Storage.getInstance().setShelterSearch(new ShelterSearch(location, name));
            view.openShelter();

            Bundle bundle = new Bundle();
            bundle.putString("location", location);
            bundle.putString("shelter_name", name);
            analytics.track("search_shelter_click",bundle );
        } else {
            view.showError(R.string.empty_zip_error);
        }
    }

    public void placeSelected(Place place) {
        analytics.track("shelter_place_selected", Analytics.PlaceToBundle(place));
    }

    @Override
    public void onAttach() {

    }

    @Override
    public void onDetach() {

    }
}
