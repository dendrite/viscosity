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
    static long count = 0;

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
        Utils.sleep(1000);

        Event event = new Event();
        event.setUser(new User(UUID.randomUUID().toString(), "User Name_" + count, new Country("" + (count / 10), "c_name_" + (count / 10L))));
        event.setTimeStamp(System.currentTimeMillis());
        List<EventElement> elements = event.getElements();
        elements.add(new EventElement(EventType.INSTALL, "" + JSONBuilder.dateFormatTillMillis.format(new Date())));
        elements.add(new EventElement(EventType.CHANGED_LEVEL, "" + 1));
        event.setElements(elements);

        System.out.println("SPOUT send:" + JSONBuilder.json(event));
        _collector.emit(new Values(JSONBuilder.json(event)));
    }
}
