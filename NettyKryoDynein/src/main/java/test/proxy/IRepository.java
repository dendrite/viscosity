package test.proxy;

import ru.ttk.proxy.IStreet;

/**
 *
 */
public interface IRepository {
    public IStreet getProxy(String id);
}
