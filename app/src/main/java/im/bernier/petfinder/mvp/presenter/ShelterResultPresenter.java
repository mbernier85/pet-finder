package im.bernier.petfinder.mvp.presenter;

import android.os.Bundle;

import im.bernier.petfinder.Analytics;
import im.bernier.petfinder.datasource.Repository;
import im.bernier.petfinder.datasource.Storage;
import im.bernier.petfinder.model.ShelterResult;
import im.bernier.petfinder.model.ShelterSearch;
import im.bernier.petfinder.mvp.view.ShelterResultView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by Michael on 2016-10-30.
 */

public class ShelterResultPresenter implements Presenter {

    private ShelterResultView view;
    private Analytics analytics = Analytics.getInstance();

    public ShelterResultPresenter(ShelterResultView view) {
        this.view = view;
    }

    @Override
    public void onAttach() {
        ShelterSearch shelterSearch = Storage.getInstance().getShelterSearch();
        getShelters(shelterSearch);
    }

    private void getShelters(ShelterSearch shelterSearch) {
        view.showProgress();
        Repository.getInstance().shelterFind(shelterSearch).enqueue(new Callback<ShelterResult>() {
            @Override
            public void onResponse(Call<ShelterResult> call, Response<ShelterResult> response) {
                Bundle bundle = new Bundle();
                if (response.isSuccessful() && response.body().getShelters() != null) {
                    view.showResults(response.body().getShelters());

                    bundle.putInt("number_of_results", response.body().getShelters().size());
                    analytics.track("shelter_search_result", bundle);
                } else if (response.isSuccessful()) {
                    view.showError(response.body().getHeader().getStatus().getMessage());
                    bundle.putString("error", response.body().getHeader().getStatus().getMessage());
                    analytics.track("shelter_search_result", bundle);
                } else {
                    view.showError(response.message());
                    bundle.putString("error", response.message());
                    analytics.track("shelter_search_result", bundle);
                }
            }

            @Override
            public void onFailure(Call<ShelterResult> call, Throwable t) {
                Timber.e(t);
            }
        });
    }

    @Override
    public void onDetach() {

    }
}
