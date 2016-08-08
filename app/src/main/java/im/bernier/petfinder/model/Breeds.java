package im.bernier.petfinder.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by Michael on 2016-08-06.
 */

@Root(name = "petfinder", strict = false)
public class Breeds {

    @Attribute()
    @Namespace(reference = "http://www.w3.org/2001/XMLSchema-instance", prefix = "xsi")
    private String noNamespaceSchemaLocation;

    @ElementList(required = false)
    private ArrayList<String> breeds;

    @Element
    private ErrorHeader header;

    public ArrayList<String> getBreeds() {
        return breeds;
    }

    public void setBreeds(ArrayList<String> breeds) {
        this.breeds = breeds;
    }

    public ErrorHeader getHeader() {
        return header;
    }

    public void setHeader(ErrorHeader header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return "Breeds{" +
                "breeds=" + breeds +
                '}';
    }
}
