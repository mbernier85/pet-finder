package im.bernier.petfinder.mvp.view;

import im.bernier.petfinder.model.Pet;

/**
 * Created by Michael on 2016-07-09.
 */

public interface PetView {
    void updateUi(Pet pet);
    void openImageViewer(Pet pet);
    void openEmail(Pet pet);
    void openDialer(Pet pet);
    void openMap(Pet pet);
}
