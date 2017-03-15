/*
 *   This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * If it is not possible or desirable to put the notice in a particular
 * file, then You may include the notice in a location (such as a LICENSE
 * file in a relevant directory) where a recipient would be likely to look
 * for such a notice.
 *
 * You may add additional accurate notices of copyright ownership.
 */

package im.bernier.petfinder.model;

import android.os.Bundle;

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

    public Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("pet_search_location", location);
        bundle.putString("pet_search_animal", animal != null ? animal.getName() : null);
        bundle.putString("pet_search_breed", breed);
        bundle.putString("pet_search_age", age);
        bundle.putString("pet_search_sex", sex);
        return bundle;
    }
}
