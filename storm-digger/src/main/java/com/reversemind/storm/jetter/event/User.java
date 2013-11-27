package com.reversemind.storm.jetter.event;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;

/**
 *
 */
public class User implements Serializable {

    private String id;
    private String name;
//    @JsonIgnore
    private Country country;

    public User() {
        this.id = "-1";
        this.name = null;
        this.country = new Country();
    }

    public User(String id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", country=" + country +
                '}';
    }
}
