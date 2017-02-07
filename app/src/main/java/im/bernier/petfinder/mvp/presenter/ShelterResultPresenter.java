package im.bernier.petfinder.mvp.presenter;

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
                if (response.isSuccessful() && response.body().getShelters() != null) {
                    view.showResults(response.body().getShelters());
                } else if (response.isSuccessful()) {
                    view.showError(response.body().getHeader().getStatus().getMessage());
                } else {
                    view.showError(response.message());
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
