package im.bernier.petfinder.mvp.presenter;

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

    @Override
    public void onAttach() {
        Search search = Storage.getInstance().getSearch();
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
                if (response.isSuccessful() && response.body().getHeader().getStatus().getCode() == 100) {
                    view.updateResults(response.body().getPets());
                } else {
                    view.showError(response.body().getHeader().getStatus().getMessage());
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                Timber.e(t, "findPet failed");
            }
        });
    }
}
