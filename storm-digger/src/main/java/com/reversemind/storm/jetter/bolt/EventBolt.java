package com.reversemind.storm.jetter.bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.reversemind.storm.jetter.JSONBuilder;
import com.reversemind.storm.jetter.Jetter;
import com.reversemind.storm.jetter.cassandra.WriteEvents;
import com.reversemind.storm.jetter.event.Event;

/**
 *
 */
public class EventBolt extends BaseBasicBolt {

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        System.out.println("SOURCE = " + tuple.getSourceComponent() + "=" + tuple.getSourceStreamId());
        System.out.println("tuple Value:" + tuple.getValue(0));

        Object object = tuple.getValue(0);

        if(object != null){
            if(object instanceof Event){
                try {
                    System.out.println("GET EVENT OBJECT FROM JSON:" + ((Event)object));
                    WriteEvents.insertRow((Event)object);
                    basicOutputCollector.emit(new Values(object));
                } catch (ConnectionException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("Object is not Event");
            }
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields(Jetter.BOLT_EVENT_TUPLE_NAME));
    }
}