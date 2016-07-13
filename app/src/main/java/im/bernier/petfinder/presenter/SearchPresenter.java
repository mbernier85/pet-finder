package im.bernier.petfinder.presenter;

import java.util.ArrayList;

import im.bernier.petfinder.datasource.Storage;
import im.bernier.petfinder.model.Search;
import im.bernier.petfinder.view.SearchView;

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
}
