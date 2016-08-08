package im.bernier.petfinder.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by micha on 2016-08-08.
 */

@Root(name = "status", strict = false)
public class ErrorStatus {
    @Element(required = false)
    private String message;

    @Element()
    private int code;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
