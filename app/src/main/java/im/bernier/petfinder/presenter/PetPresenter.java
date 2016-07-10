package im.bernier.petfinder.presenter;

import im.bernier.petfinder.datasource.Storage;
import im.bernier.petfinder.model.Pet;
import im.bernier.petfinder.view.PetView;

/**
 * Created by Michael on 2016-07-09.
 */

public class PetPresenter implements Presenter {

    private PetView view;

    public void setView(PetView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onAttach() {
        Pet pet = Storage.getInstance().getPet();
        view.updateUi(pet);
    }
}
