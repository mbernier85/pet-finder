package im.bernier.petfinder.presenter;

import im.bernier.petfinder.view.SearchView;

/**
 * Created by Michael on 2016-07-12.
 */

public class SearchPresenter implements Presenter {

    private SearchView view;

    public void setView(SearchView view) {
        this.view = view;
    }

    @Override
    public void onAttach() {

    }

    @Override
    public void onDetach() {

    }
}
