package com.reversemind.storm.jetter;

import com.reversemind.storm.jetter.event.Event;
import com.reversemind.storm.jetter.event.EventElement;
import com.reversemind.storm.jetter.event.EventType;
import com.reversemind.storm.jetter.event.User;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class JSONBuilder {

    public static SimpleDateFormat dateFormatTillMillis = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    public static String json(Object object) {
        if (object != null) {
            ObjectMapper mapper = new ObjectMapper();
            String _result = "";
            try {
                _result = mapper.writeValueAsString(object);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return _result;
            }
        }
        return "";
    }

    public static <T> T object(String string, Class<T> clazz){
        if(string != null && string.length()>0){
            ObjectMapper mapper = new ObjectMapper();
            Object _result = null;
            try {
                _result = mapper.readValue(string, clazz);
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                return (T)_result;
            }
        }else{
            return null;
        }
    }

    public static void main(String... args) {
        User user = new User();
        System.out.println(JSONBuilder.json(user));

        user.setName("User Name");

        Event event = new Event();
        event.setUser(user);
        event.setTimeStamp(System.currentTimeMillis());
        List<EventElement> elements = event.getElements();
        elements.add(new EventElement(EventType.INSTALL, "" + dateFormatTillMillis.format(new Date())));
        elements.add(new EventElement(EventType.CHANGED_LEVEL, "" + 1));
        event.setElements(elements);

        System.out.println("EVENT:" + JSONBuilder.json(event));

        String json = JSONBuilder.json(event);
        Event eventResult = JSONBuilder.object(json, Event.class);
        System.out.println("eventResult:" + eventResult);
    }

}
