package test.proxy.remote;

import ru.ttk.proxy.IStreet;
import ru.ttk.proxy.Proxyable;

import java.io.Serializable;

/**
 *
 */
public class Street implements Serializable, Proxyable, IStreet {

    private String id;
    private String name;

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

    @Override
    public String toString() {
        return "Street{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
