package org.learn.storm.ch01.topologies;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;
import org.learn.storm.ch01.bolts.HelloWorldBolt;
import org.learn.storm.ch01.spouts.HelloWorldSpout;

public class HelloWorldTopology {
	public static void main(String[] args) {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("randomHelloWorld", new HelloWorldSpout(), 10);
		builder.setBolt("HelloWorldBolt", new HelloWorldBolt(), 2).shuffleGrouping("randomHelloWorld");

		Config conf = new Config();
		conf.setDebug(true);

		if (args != null && args.length > 0) {
			conf.setNumWorkers(3);
			try {
				StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("HelloWorld", conf, builder.createTopology());
			Utils.sleep(300000);
			cluster.killTopology("HelloWorld");
			cluster.shutdown();
		}
	}
}
