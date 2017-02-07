package im.bernier.petfinder.mvp.presenter;

import android.os.Bundle;

import im.bernier.petfinder.Analytics;
import im.bernier.petfinder.datasource.Storage;
import im.bernier.petfinder.model.ShelterSearch;
import im.bernier.petfinder.mvp.view.ShelterSearchView;

/**
 * Created by Michael on 2016-10-29.
 */

public class ShelterSearchPresenter implements Presenter {

    private ShelterSearchView view;
    private Analytics analytics = Analytics.getInstance();

    public ShelterSearchPresenter(ShelterSearchView view) {
        this.view = view;
    }

    public void submit(String location, String name) {
        if (location.length() > 3) {
            Storage.getInstance().setShelterSearch(new ShelterSearch(location, name));
            view.openShelter();

            Bundle bundle = new Bundle();
            bundle.putString("location", location);
            bundle.putString("shelter_name", name);
            analytics.track("search_shelter_click",bundle );
        } else {
            view.showLocationEmpty();
        }
    }

    @Override
    public void onAttach() {

    }

    @Override
    public void onDetach() {

    }
}
