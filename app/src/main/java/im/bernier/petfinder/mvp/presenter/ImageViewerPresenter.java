package im.bernier.petfinder.mvp.presenter;

import im.bernier.petfinder.datasource.Storage;
import im.bernier.petfinder.model.Pet;
import im.bernier.petfinder.mvp.view.IImageViewerView;

/**
 * Created by Michael on 2016-07-12.
 */

public class ImageViewerPresenter implements Presenter {

    private IImageViewerView view;

    public void setView(IImageViewerView view) {
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
