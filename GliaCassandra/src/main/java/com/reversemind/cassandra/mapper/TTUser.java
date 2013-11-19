package com.reversemind.cassandra.mapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 */
@Entity
public class TTUser {

    @Id
    @Column(name="id")
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="screenname")
    private String screenName;

    public TTUser() {
    }

    public TTUser(long id, String name, String screenName) {
        this.id = id;
        this.name = name;
        this.screenName = screenName;
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

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @Override
    public String toString() {
        return "TUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", screenName='" + screenName + '\'' +
                '}';
    }
}