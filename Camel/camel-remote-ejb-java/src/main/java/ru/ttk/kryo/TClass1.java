package ru.ttk.kryo;

import java.io.Serializable;

/**
 *
 */
public class TClass1 implements Serializable {

    private long id;
    private long value;
    private String name;

    public TClass1(){

    }

    public TClass1(long id, long value, String name){
        this.id = id;
        this.value = value;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "id:" + id + " value:" + value + " name:" + name;
    }
}
