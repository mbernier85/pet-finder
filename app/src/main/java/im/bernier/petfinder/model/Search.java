package im.bernier.petfinder.model;

/**
 * Created by Michael on 2016-07-13.
 */

public class Search {

    private String location;
    private Animal animal;
    private String breed = null;
    private String age = null;
    private String sex = null;

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

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {

        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
