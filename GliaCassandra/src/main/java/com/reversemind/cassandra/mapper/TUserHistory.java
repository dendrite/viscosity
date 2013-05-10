package com.reversemind.cassandra.mapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 */
@Entity
public class TUserHistory {

    @Id
    @Column(name="id")
    private long id;

    @Column(name="user")
    private TTUser user;

    @Column(name="timestamp")
    private long timestamp;

    public TUserHistory() {
    }

    public TUserHistory(long id, TTUser user, long timestamp) {
        this.id = id;
        this.user = user;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TTUser getUser() {
        return user;
    }

    public void setUser(TTUser user) {
        this.user = user;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "TUserHistory{" +
                "id=" + id +
                ", user=" + user +
                ", timestamp=" + timestamp +
                '}';
    }
}
