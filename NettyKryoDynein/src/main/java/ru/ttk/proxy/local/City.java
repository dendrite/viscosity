package ru.ttk.proxy.local;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import ru.ttk.proxy.*;

import java.io.Serializable;
import java.net.URI;
import java.util.Date;
import java.util.List;

/**
 *
 *
 */
public class City implements Serializable {

    private String id;
    private String name;
    private Date date;
    private List<House> houses;
    private PStreet street;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public List<House> getHouses() {
        return houses;
    }

    public void setHouses(List<House> houses) {
        this.houses = houses;
    }

    @RemoteLazyProxy
    public PStreet getStreet() {
        IRepository repository = new Repository();
        Object object = repository.getProxy(this.street.getId());

        // select p from City p where p.street.name like ('bla-bla')
        Mapper mapper = new DozerBeanMapper();
        PStreet ppStreet = mapper.map(object, PStreet.class);
        return  ppStreet;

        /*
        select p from City p where p.street.id in(
        select p from Street p where p.name like ('bla-bla')
        )

         */
    }

    public void setStreet(PStreet street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "City{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", houses=" + houses +
                ", street=" + this.getStreet() +
                '}';
    }
}
