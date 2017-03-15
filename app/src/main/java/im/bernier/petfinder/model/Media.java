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

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Michael on 2016-07-09.
 */

@Root(name = "media", strict = false)
public class Media {

    @ElementList(required = false)
    private ArrayList<Photo> photos;

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    public ArrayList<Photo> getHiResPhotos() {
        ArrayList<Photo> hiRes = new ArrayList<>();
        if (photos != null) {
            for (Photo photo : photos) {
                if (photo.getSize().equalsIgnoreCase("x")) {
                    hiRes.add(photo);
                }
            }
        }
        return hiRes;
    }

    public String getThumbnail() {
        if (photos != null) {
            for (Photo photo : photos) {
                if (photo.getSize().equalsIgnoreCase("pn")) {
                    return photo.getValue();
                }
            }
        }
        return null;
    }
}
