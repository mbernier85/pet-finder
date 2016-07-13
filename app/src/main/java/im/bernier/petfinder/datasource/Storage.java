package im.bernier.petfinder.datasource;

import im.bernier.petfinder.model.Pet;
import im.bernier.petfinder.model.Search;

/**
 * Created by Michael on 2016-07-09.
 */

public class Storage {

    private static Storage instance;

    private Pet pet;
    private Search search;

    public static Storage getInstance() {
        if (instance == null) {
            instance =  new Storage();
        }
        return instance;
    }

    public static void setInstance(Storage instance) {
        Storage.instance = instance;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    private Storage() {

    }
}
