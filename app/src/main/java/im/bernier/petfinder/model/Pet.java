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

import com.google.firebase.analytics.FirebaseAnalytics;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by Michael on 2016-07-09.
 */

@Root(name = "pet", strict = false)
public class Pet {

    @Element
    private String id;

    @Element
    private String name;

    @Element
    private String age;

    @Element
    private String sex;

    @Element(required = false)
    private String description;

    @Element(required = false)
    private Media media;

    @Element
    private Contact contact;

    @ElementList(required = false)
    private ArrayList<String> breeds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public ArrayList<String> getBreeds() {
        return breeds;
    }

    public void setBreeds(ArrayList<String> breeds) {
        this.breeds = breeds;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBreed() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < breeds.size(); i++) {
            stringBuilder.append(breeds.get(i));
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                ", description='" + description + '\'' +
                ", media=" + media +
                ", contact=" + contact +
                ", breeds=" + breeds +
                '}';
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString("breed", getBreed());
        return bundle;
    }
}
