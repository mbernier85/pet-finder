package im.bernier.petfinder.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 2016-07-09.
 */

@Root(name = "petfinder", strict = false)
public class SearchResult {

    @Attribute()
    @Namespace(reference = "http://www.w3.org/2001/XMLSchema-instance", prefix = "xsi")
    private String noNamespaceSchemaLocation;

    @ElementList(required = false)
    private ArrayList<Pet> pets;

    @Element
    private ErrorHeader header;

    public ErrorHeader getHeader() {
        return header;
    }

    public void setHeader(ErrorHeader header) {
        this.header = header;
    }

    public ArrayList<Pet> getPets() {
        return pets;
    }

    public void setPets(ArrayList<Pet> pets) {
        this.pets = pets;
    }
}
