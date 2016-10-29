package im.bernier.petfinder.mvp.presenter;

import im.bernier.petfinder.datasource.Storage;
import im.bernier.petfinder.model.Pet;
import im.bernier.petfinder.mvp.view.PetView;

/**
 * Created by Michael on 2016-07-09.
 */

public class PetPresenter implements Presenter {

    private PetView view;
    private Pet pet;

    public void setView(PetView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onAttach() {
        pet = Storage.getInstance().getPet();
        view.updateUi(pet);
    }

    public void onImageClick() {
        view.openImageViewer(pet);
    }

    public void phoneClick() {
        view.openDialer(pet);
    }

    public void emailClick() {
        view.openEmail(pet);
    }

    public void addressClick() {
        view.openMap(pet);
    }
}
