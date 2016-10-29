package im.bernier.petfinder.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by Michael on 2016-10-29.
 */

@Root(name = "petfinder", strict = false)
public class ShelterResult {
    @Attribute()
    @Namespace(reference = "http://www.w3.org/2001/XMLSchema-instance", prefix = "xsi")
    private String noNamespaceSchemaLocation;

    @ElementList(required = false)
    private ArrayList<Shelter> shelters;

    @Element
    private ErrorHeader header;

    public ErrorHeader getHeader() {
        return header;
    }

    public void setHeader(ErrorHeader header) {
        this.header = header;
    }

    public ArrayList<Shelter> getShelters() {
        return shelters;
    }

    public void setShelters(ArrayList<Shelter> shelters) {
        this.shelters = shelters;
    }

    @Override
    public String toString() {
        return "ShelterResult{" +
                "shelters=" + shelters +
                '}';
    }
}
