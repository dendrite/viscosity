package com.reversemind.storm.jetter.bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.reversemind.storm.jetter.JSONBuilder;
import com.reversemind.storm.jetter.Jetter;
import com.reversemind.storm.jetter.event.Event;

/**
 *
 */
public class EventJSONBolt extends BaseBasicBolt {

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        System.out.println("SOURCE = " + tuple.getSourceComponent() + "=" + tuple.getSourceStreamId());
        System.out.println("tuple string:" + tuple.getString(0));

        String eventJson = tuple.getString(0);

        if (eventJson != null && eventJson.length() > 0) {
            Event event = JSONBuilder.object(eventJson, Event.class);
            if (event != null) {
                basicOutputCollector.emit(new Values(event));
            } else {
                basicOutputCollector.emit(new Values(null));
            }
        } else {
            basicOutputCollector.emit(new Values(null));
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields(Jetter.BOLT_EVENT_JSON_TUPLE_NAME));
    }
}
