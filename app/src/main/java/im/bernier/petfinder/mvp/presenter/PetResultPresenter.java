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

import im.bernier.petfinder.Analytics;
import im.bernier.petfinder.datasource.Repository;
import im.bernier.petfinder.datasource.Storage;
import im.bernier.petfinder.model.Pet;
import im.bernier.petfinder.model.Search;
import im.bernier.petfinder.model.SearchResult;
import im.bernier.petfinder.mvp.view.ResultView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by Michael on 2016-07-09.
 */

public class PetResultPresenter implements Presenter {

    private ResultView view;
    private Analytics analytics = Analytics.getInstance();

    @Override
    public void onAttach() {
        Search search = Storage.getInstance().getSearch();
        if (search == null) {
            view.doFinish();
            return;
        }
        findPet(search);
    }

    public void setView(ResultView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {

    }

    public void onPetClick(Pet pet) {
        Storage.getInstance().setPet(pet);
        view.openPet(pet);
    }

    private void findPet(Search search) {
        Call<SearchResult> searchResultCall = Repository.getInstance().petFind(search);
        searchResultCall.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                Bundle bundle = new Bundle();
                if (response.isSuccessful() && response.body().getHeader().getStatus().getCode() == 100) {
                    view.updateResults(response.body().getPets());

                    bundle.putInt("number_of_results", response.body().getPets().size());
                    analytics.track("pet_search_result", bundle);
                } else if (response.isSuccessful()) {
                    view.showError(response.body().getHeader().getStatus().getMessage());
                    bundle.putString("error", response.body().getHeader().getStatus().getMessage());
                    analytics.track("pet_search_result", bundle);
                } else {
                    view.showError(response.message());
                    bundle.putString("error", response.message());
                    analytics.track("pet_search_result", bundle);
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                Timber.e(t, "findPet failed");
            }
        });
    }
}
