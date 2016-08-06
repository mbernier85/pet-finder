package im.bernier.petfinder.view;

import java.util.ArrayList;

/**
 * Created by Michael on 2016-07-12.
 */

public interface SearchView {
    void setAnimalsSpinner(String[] animals);
    void showResults();
    void updateBreeds(ArrayList<String> breeds);
}
