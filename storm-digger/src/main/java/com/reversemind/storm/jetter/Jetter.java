package com.reversemind.storm.jetter;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import com.reversemind.storm.jetter.bolt.EventBolt;
import com.reversemind.storm.jetter.bolt.EventJSONBolt;
import com.reversemind.storm.jetter.spout.HTTPSpout;
import com.reversemind.storm.wordcount.RandomSentenceSpout;

/**
 */
public class Jetter {

    public static final String SPOUT_EVENT_TUPLE_NAME = "event";
    public static final String BOLT_EVENT_JSON_TUPLE_NAME = "eventJSON";
    public static final String BOLT_EVENT_TUPLE_NAME = "eventObject";

    public static void main(String... args) throws InterruptedException {

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("spout", new HTTPSpout(50L), 1);
        builder.setBolt("EVENT", new EventJSONBolt(), 1).shuffleGrouping("spout");
        builder.setBolt("count", new EventBolt(), 1).shuffleGrouping("EVENT");

        Config conf = new Config();
        conf.setDebug(true);

        conf.setMaxTaskParallelism(1);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("jetter-cassandra", conf, builder.createTopology());

        Thread.sleep(30000);

        cluster.shutdown();
    }

}
