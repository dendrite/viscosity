package com.reversemind;

import java.io.Serializable;
import java.util.Date;

/**
 * Date: 5/14/13
 * Time: 4:54 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class User implements Serializable {

    private long id;
    private String screenName;
    private Date createdAt;
    private String URL;
    private int statuses;

    public User() {
    }

    public User(long id, String screenName, Date createdAt, String URL, int statuses) {
        this.id = id;
        this.screenName = screenName;
        this.createdAt = createdAt;
        this.URL = URL;
        this.statuses = statuses;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public int getStatuses() {
        return statuses;
    }

    public void setStatuses(int statuses) {
        this.statuses = statuses;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", screenName='" + screenName + '\'' +
                ", createdAt=" + createdAt +
                ", URL='" + URL + '\'' +
                ", statuses=" + statuses +
                '}';
    }
}
