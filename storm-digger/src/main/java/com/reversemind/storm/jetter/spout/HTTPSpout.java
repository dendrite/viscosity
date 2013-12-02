package com.reversemind.storm.jetter.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import com.reversemind.storm.jetter.JSONBuilder;
import com.reversemind.storm.jetter.Jetter;
import com.reversemind.storm.jetter.event.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 */
public class HTTPSpout extends BaseRichSpout {

    SpoutOutputCollector _collector;
    static long count = 1;
    static long timestamp = 0;
    private String userId = UUID.randomUUID().toString();

    private Long timeOut = 1000L;

    public HTTPSpout(Long timeOut){
        this.timeOut = timeOut;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields(Jetter.SPOUT_EVENT_TUPLE_NAME));
    }

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        _collector = spoutOutputCollector;
    }

    @Override
    public void nextTuple() {
        count++;
        Event event = new Event();
        event.setUser(new User(this.getUserId(), "User Name_" + count, new Country("" + (count / 10), "c_name_" + (count / 10L))));
        event.setTimeStamp(this.getTime());
        List<EventElement> elements = event.getElements();
        elements.add(new EventElement(EventType.INSTALL, "" + JSONBuilder.dateFormatTillMillis.format(new Date())));
        elements.add(new EventElement(EventType.CHANGED_LEVEL, "" + 1));
        event.setElements(elements);

        System.out.println("SPOUT send:" + JSONBuilder.json(event));
        Utils.sleep(timeOut);
        _collector.emit(new Values(JSONBuilder.json(event)));
    }

    private Long getTime(){
        if(timestamp % 10 == 0){
            timestamp = System.currentTimeMillis();
        }
        System.out.println("TIME:" + timestamp + " " + new Date(timestamp));
        return timestamp;
    }
    private String getUserId(){
        if(count % 10 == 0){
            userId = UUID.randomUUID().toString();
        }
        userId = UUID.randomUUID().toString();
        System.out.println("Return userId:" + userId);

        return userId;
    }
}
