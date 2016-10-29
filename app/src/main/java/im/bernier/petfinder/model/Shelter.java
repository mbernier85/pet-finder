package im.bernier.petfinder.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Michael on 2016-10-29.
 */

@Root(name = "shelter", strict = false)
public class Shelter {

    @Element
    private String id;

    @Element
    private String name;

    @Element(required = false)
    private String address1;

    @Element(required = false)
    private String address2;

    @Element
    private String city;

    @Element
    private String state;

    @Element
    private String country;

    @Element
    private String zip;

    @Element
    private double latitude;

    @Element
    private double longitude;

    @Element(required = false)
    private String phone;

    @Element
    private String email;

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

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @Override
    public String toString() {
        return "Shelter{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                '}';
    }
}
