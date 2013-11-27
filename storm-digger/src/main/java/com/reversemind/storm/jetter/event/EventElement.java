package com.reversemind.storm.jetter.event;

import java.io.Serializable;

/**
 */
public class EventElement implements Serializable {

    private EventType type;
    private String value;

    public EventElement() {
        this.type = EventType.DEFAULT;
        this.value = "";
    }

    public EventElement(EventType type, String value) {
        this.type = type;
        this.value = value;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "EventElement{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
