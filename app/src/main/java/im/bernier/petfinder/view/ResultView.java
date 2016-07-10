package im.bernier.petfinder.view;

import java.util.ArrayList;

import im.bernier.petfinder.model.Pet;

/**
 * Created by Michael on 2016-07-09.
 */

public interface ResultView {
    void updateResults(ArrayList<Pet> pets);
    void openPet(Pet pet);
}
