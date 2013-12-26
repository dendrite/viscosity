package com.test;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class SimpleDTO implements Serializable {

    private Long id;
    private String name;
    private Object someValue;
    private List<OtherSimpleDTO> list;

    public SimpleDTO(Long id, String name, Object someValue, List<OtherSimpleDTO> list) {
        this.id = id;
        this.name = name;
        this.someValue = someValue;
        this.list = list;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getSomeValue() {
        return someValue;
    }

    public void setSomeValue(Object someValue) {
        this.someValue = someValue;
    }

    public List<OtherSimpleDTO> getList() {
        return list;
    }

    public void setList(List<OtherSimpleDTO> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "SimpleDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", someValue=" + someValue +
                ", list=" + list +
                '}';
    }
}
