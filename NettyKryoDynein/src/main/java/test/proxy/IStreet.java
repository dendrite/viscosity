package test.proxy;

import ru.ttk.proxy.Proxyable;

/**
 *
 *
 */
public interface IStreet extends Proxyable {

    public String getId();

    public void setId(String id);
    public String getName();
    public void setName(String name);
}
