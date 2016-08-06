package im.bernier.petfinder.presenter;

import java.util.ArrayList;

import im.bernier.petfinder.datasource.Repository;
import im.bernier.petfinder.datasource.Storage;
import im.bernier.petfinder.model.Breeds;
import im.bernier.petfinder.model.Search;
import im.bernier.petfinder.view.SearchView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by Michael on 2016-07-12.
 */

public class SearchPresenter implements Presenter {

    private SearchView view;
    private final String[] animals = new String[]{"cat", "dog", "rabbit", "smallfurry", "horse", "bird", "reptile", "pig", "barnyard"};

    public void setView(SearchView view) {
        this.view = view;
    }

    @Override
    public void onAttach() {
        view.setAnimalsSpinner(animals);
    }

    @Override
    public void onDetach() {

    }

    public void search(Search search) {
        Storage.getInstance().setSearch(search);
        view.showResults();
    }

    public void loadBreed(String animal) {
        Call<Breeds> call = Repository.getInstance().loadBreeds(animal);
        call.enqueue(new Callback<Breeds>() {
            @Override
            public void onResponse(Call<Breeds> call, Response<Breeds> response) {
                view.updateBreeds(response.body().getBreeds());
            }

            @Override
            public void onFailure(Call<Breeds> call, Throwable t) {

            }
        });
    }

}
