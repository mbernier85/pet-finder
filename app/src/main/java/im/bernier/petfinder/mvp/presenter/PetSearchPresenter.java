package im.bernier.petfinder.mvp.presenter;

import java.util.ArrayList;

import im.bernier.petfinder.R;
import im.bernier.petfinder.datasource.Repository;
import im.bernier.petfinder.datasource.Storage;
import im.bernier.petfinder.model.Breeds;
import im.bernier.petfinder.model.Search;
import im.bernier.petfinder.mvp.view.PetSearchView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by Michael on 2016-07-12.
 */

public class PetSearchPresenter implements Presenter {

    private PetSearchView view;
    private final String[] animals = new String[]{"cat", "dog", "rabbit", "smallfurry", "horse", "bird", "reptile", "pig", "barnyard"};

    public void setView(PetSearchView view) {
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
        if (search.getLocation().length() > 4) {
            Storage.getInstance().setSearch(search);
            view.showResults();
        } else {
            view.showError(R.string.empty_zip_error);
        }
    }

    public void loadBreed(String animal) {
        if (animal == null ) {
            view.updateBreeds(new ArrayList<String>());
        } else {
            Call<Breeds> call = Repository.getInstance().loadBreeds(animal);
            call.enqueue(new Callback<Breeds>() {
                @Override
                public void onResponse(Call<Breeds> call, Response<Breeds> response) {
                    if (response.isSuccessful() && response.body().getHeader().getStatus().getCode() == 100) {
                        view.updateBreeds(response.body().getBreeds());
                    } else if (response.body().getHeader().getStatus().getCode() != 200){
                        view.showError(response.body().getHeader().getStatus().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<Breeds> call, Throwable t) {
                    Timber.e(t);
                }
            });
        }
    }

}
