package com.test.jsfbootstrap.controller;

import java.io.Serializable;

/**
 *
 */
public class HouseType implements Serializable {

    private String name;
    private String id;
    private String cssClass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }
}
