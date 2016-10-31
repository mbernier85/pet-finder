package im.bernier.petfinder.mvp.presenter;

import im.bernier.petfinder.datasource.Storage;
import im.bernier.petfinder.model.ShelterSearch;
import im.bernier.petfinder.mvp.view.IShelterSearchView;

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
        Storage.getInstance().setShelterSearch(new ShelterSearch(location, name));
        view.openShelter();
    }

    @Override
    public void onAttach() {

    }

    @Override
    public void onDetach() {

    }
}
