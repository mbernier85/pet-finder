package im.bernier.petfinder.model;

/**
 * Created by Michael on 2016-07-12.
 */

public class Animal {
    private String name;
    private String key;

    public Animal(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return name;
    }
}
