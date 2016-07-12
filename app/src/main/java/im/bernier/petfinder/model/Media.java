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
