package ru.ttk.camel;

import java.io.Serializable;

/**
 *
 */
public class SamplePojo implements Serializable {

    private String name;
    private int value;

    public SamplePojo() {
    }

    public SamplePojo(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SamplePojo{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
