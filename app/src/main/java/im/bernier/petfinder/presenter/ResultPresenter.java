package im.bernier.petfinder.presenter;

import im.bernier.petfinder.datasource.Repository;
import im.bernier.petfinder.datasource.Storage;
import im.bernier.petfinder.model.Pet;
import im.bernier.petfinder.model.Search;
import im.bernier.petfinder.model.SearchResult;
import im.bernier.petfinder.view.ResultView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by Michael on 2016-07-09.
 */

public class ResultPresenter implements Presenter {

    private ResultView view;
    private Search search;

    @Override
    public void onAttach() {
        search = Storage.getInstance().getSearch();
        findPet(search.getLocation(), search.getAnimal().getKey());
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

    private void findPet(String location, String animal) {
        Call<SearchResult> searchResultCall = Repository.getInstance().petFind(location, animal);
        searchResultCall.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                view.updateResults(response.body().getPets());
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                Timber.e(t, "findPet failed");
            }
        });
    }
}
