package im.bernier.petfinder.mvp.view;

import java.util.List;

import im.bernier.petfinder.model.Shelter;

/**
 * Created by Michael on 2016-10-30.
 */

public interface IShelterResultView {
    void showResults(List<Shelter> shelters);
    void showError(String error);
}
