package im.bernier.petfinder.model;

/**
 * Created by Michael on 2016-10-30.
 */

public class ShelterSearch {

    private String location;
    private String name;

    public ShelterSearch(String zip, String name) {
        this.location = zip;
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }
}
