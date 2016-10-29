package im.bernier.petfinder.mvp.presenter;

import im.bernier.petfinder.datasource.Repository;
import im.bernier.petfinder.model.ShelterResult;
import im.bernier.petfinder.mvp.view.IShelterSearchView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by Michael on 2016-10-29.
 */

public class ShelterSearchPresenter implements Presenter {

    private IShelterSearchView view;

    public ShelterSearchPresenter() {
    }

    public void setView(IShelterSearchView view) {
        this.view = view;
    }

    public void submit(String location, String name) {

        Repository.getInstance().shelterFind(location, name).enqueue(new Callback<ShelterResult>() {
            @Override
            public void onResponse(Call<ShelterResult> call, Response<ShelterResult> response) {
                if (response.isSuccessful()) {
                    Timber.d(response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ShelterResult> call, Throwable t) {
                Timber.e(t, "");
            }
        });
    }

    @Override
    public void onAttach() {

    }

    @Override
    public void onDetach() {

    }
}
