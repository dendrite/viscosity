package ru.ttk.kryo;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class TClass2 implements Serializable {

    private long id;
    private String name;
    private List<TClass1> list;

    public TClass2(){

    }

    public TClass2(long id, String name, List<TClass1> list){
        this.id = id;
        this.name = name;
        this.list = list;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TClass1> getList() {
        return list;
    }

    public void setList(List<TClass1> list) {
        this.list = list;
    }

    @Override
    public String toString(){
        return "id:" + id + " name:" + name + "\n\t\t list:" + this.list;
    }
}
