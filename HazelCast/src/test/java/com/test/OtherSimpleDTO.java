package com.test;

import java.io.Serializable;

/**
 *
 */
public class OtherSimpleDTO implements Serializable {

    private String name;
    private String value;

    public OtherSimpleDTO(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "OtherSimpleDTO{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
