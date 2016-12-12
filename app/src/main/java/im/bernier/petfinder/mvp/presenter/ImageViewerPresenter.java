package im.bernier.petfinder.mvp.presenter;

import im.bernier.petfinder.datasource.Storage;
import im.bernier.petfinder.model.Pet;
import im.bernier.petfinder.mvp.view.ImageViewerView;

/**
 * Created by Michael on 2016-07-12.
 */

public class ImageViewerPresenter implements Presenter {

    private ImageViewerView view;

    public void setView(ImageViewerView view) {
        this.view = view;
    }

    @Override
    public void onAttach() {
        Pet pet = Storage.getInstance().getPet();
        view.updateUi(pet);
    }

    @Override
    public void onDetach() {

    }
}
