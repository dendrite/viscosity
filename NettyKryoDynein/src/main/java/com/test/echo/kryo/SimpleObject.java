package com.test.echo.kryo;

import java.io.Serializable;

/**
 *
 */
public class SimpleObject implements Serializable {

    private String id;
    private String value;
    private SimpleObject2 simpleObject2;

    public SimpleObject(){
    }

    public SimpleObject(String id, String value, SimpleObject2 simpleObject2){
        this.id = id;
        this.id = value;
        this.simpleObject2 = simpleObject2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SimpleObject2 getSimpleObject2() {
        return simpleObject2;
    }

    public void setSimpleObject2(SimpleObject2 simpleObject2) {
        this.simpleObject2 = simpleObject2;
    }

    @Override
    public String toString(){
        return " id:" + this.id + " value:" + value + " simpleObject2:" + simpleObject2;
    }

}
