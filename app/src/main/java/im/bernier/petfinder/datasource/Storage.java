package im.bernier.petfinder.datasource;

import im.bernier.petfinder.model.Pet;
import im.bernier.petfinder.model.Search;
import im.bernier.petfinder.model.ShelterSearch;

/**
 * Created by Michael on 2016-07-09.
 */

public class Storage {

    private static Storage instance;

    private Pet pet;
    private Search search;
    private ShelterSearch shelterSearch;

    public static Storage getInstance() {
        if (instance == null) {
            instance =  new Storage();
        }
        return instance;
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

    public ShelterSearch getShelterSearch() {
        return shelterSearch;
    }

    public void setShelterSearch(ShelterSearch shelterSearch) {
        this.shelterSearch = shelterSearch;
    }

    private Storage() {

    }
}
