package im.bernier.petfinder.model;

/**
 * Created by Michael on 2016-07-13.
 */

public class Search {

    private String location;
    private Animal animal;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
}
