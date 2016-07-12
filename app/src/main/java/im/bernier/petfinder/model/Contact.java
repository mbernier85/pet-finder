package im.bernier.petfinder.model;

import android.text.TextUtils;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Michael on 2016-07-12.
 */

@Root(name = "contact", strict = false)
public class Contact {

    @Element(required = false)
    private String name;

    @Element(required = false)
    private String address1;

    @Element(required = false)
    private String address2;

    @Element(required = false)
    private String city;

    @Element(required = false)
    private String state;

    @Element(required = false)
    private String zip;

    @Element(required = false)
    private String phone;

    @Element(required = false)
    private String email;

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getAddress() {
        String address = "";
        if (!TextUtils.isEmpty(address1)) {
            address += address1 + ", ";
        }
        if (!TextUtils.isEmpty(address2)) {
            address += address2 + ", ";
        }

        address += city + ", " + state + ", " + zip;
        return address;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
