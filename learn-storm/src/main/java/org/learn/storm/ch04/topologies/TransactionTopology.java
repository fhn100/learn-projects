package org.learn.storm.ch04.topologies;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;
import org.learn.storm.ch04.bolts.TransactionRandomFailureBolt;
import org.learn.storm.ch04.spouts.TransactionSpout;

public class TransactionTopology {

	public static void main(String[] args) {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("TransactionSpout", new TransactionSpout());
		builder.setBolt("TransactionRandomFailureBolt", new TransactionRandomFailureBolt())
				.shuffleGrouping("TransactionSpout");

		LocalCluster cluster = new LocalCluster();
		Config conf = new Config();
		conf.setDebug(true);
		cluster.submitTopology("TransactionTopology", conf, builder.createTopology());
		while (true) {
			Utils.sleep(1000);
		}
	}
}