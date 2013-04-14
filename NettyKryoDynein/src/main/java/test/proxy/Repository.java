package test.proxy;

import ru.ttk.proxy.IRepository;
import ru.ttk.proxy.IStreet;
import ru.ttk.proxy.remote.Street;

/**
 *
 *
 */
public class Repository implements IRepository {

    @Override
    public IStreet getProxy(String id) {
        // find(.class,id);
        // select * from bla_bla where id = :id
        IStreet street = new Street();
        street.setId(id);
        street.setName("Тверская");
        return (IStreet)street;
    }
}
