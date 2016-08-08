package im.bernier.petfinder.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by micha on 2016-08-08.
 */

@Root(name = "header", strict = false)
public class ErrorHeader {

    @Element
    private ErrorStatus status;

    public ErrorStatus getStatus() {
        return status;
    }

    public void setStatus(ErrorStatus status) {
        this.status = status;
    }
}
