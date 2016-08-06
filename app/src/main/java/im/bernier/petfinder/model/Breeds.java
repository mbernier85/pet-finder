package im.bernier.petfinder.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by micha on 2016-08-06.
 */

@Root(name = "petfinder", strict = false)
public class Breeds {

    @Attribute()
    @Namespace(reference = "http://www.w3.org/2001/XMLSchema-instance", prefix = "xsi")
    private String noNamespaceSchemaLocation;

    @ElementList
    private ArrayList<String> breeds;

    public ArrayList<String> getBreeds() {
        return breeds;
    }

    public void setBreeds(ArrayList<String> breeds) {
        this.breeds = breeds;
    }

    @Override
    public String toString() {
        return "Breeds{" +
                "breeds=" + breeds +
                '}';
    }
}
