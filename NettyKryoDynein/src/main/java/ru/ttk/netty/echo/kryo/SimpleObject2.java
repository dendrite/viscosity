package ru.ttk.netty.echo.kryo;

import java.io.Serializable;

/**
 */
public class SimpleObject2 implements Serializable {

    private String id;
    private long value;

    public SimpleObject2(){

    }

    public SimpleObject2(String id, long value){
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString(){
        return " id:" + id + " value:" + value;
    }
}
