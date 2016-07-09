package im.bernier.petfinder.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

/**
 * Created by micha on 2016-07-09.
 */

@Root(name = "photo", strict = false)
public class Photo {

    @Attribute
    private int id;

    @Attribute
    private String size;

    @Text
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", size='" + size + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
