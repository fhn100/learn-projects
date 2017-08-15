package org.learn.storm.ch01.topologies;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.learn.storm.ch01.bolts.TickWordCountBolt;
import org.learn.storm.ch01.spouts.TickWordCountSpout;

public class TickWordCountTopology {
	public static void main(String[] args) {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("TickWordCountSpout", new TickWordCountSpout());
		builder.setBolt("TickWordCountBolt", new TickWordCountBolt(), 3).fieldsGrouping("TickWordCountSpout",
				new Fields("word"));

		Config conf = new Config();
		// 设置一个全局的Topology发送tick心跳时间，测试优先级
		conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, 7);
		conf.setDebug(false);
		if (args != null && args.length > 0) {
			conf.setNumWorkers(3);
			try {
				StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			conf.setMaxTaskParallelism(3);
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("TickWordCountTopology", conf, builder.createTopology());
		}
	}
}
