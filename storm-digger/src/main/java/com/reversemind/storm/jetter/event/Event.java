package com.reversemind.storm.jetter.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class Event implements Serializable {

    private Long timeStamp;
    private User user;
    private List<EventElement> elements;

    public Event() {
        this.timeStamp = new Date().getTime();
        this.user = new User();
        this.elements = new ArrayList<EventElement>();
    }

    public Event(Long timeStamp, User user) {
        this.timeStamp = timeStamp;
        this.user = user;
        this.elements = new ArrayList<EventElement>();
    }

    public Event(Long timeStamp, User user, List<EventElement> elements) {
        this.timeStamp = timeStamp;
        this.user = user;
        this.elements = elements;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<EventElement> getElements() {
        return elements;
    }

    public void setElements(List<EventElement> elements) {
        this.elements = elements;
    }

    @Override
    public String toString() {
        return "Event{" +
                "timeStamp=" + timeStamp +
                ", user=" + user +
                ", elements=" + elements +
                '}';
    }
}
