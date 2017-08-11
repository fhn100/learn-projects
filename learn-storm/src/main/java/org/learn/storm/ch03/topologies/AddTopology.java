package org.learn.storm.ch03.topologies;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.drpc.LinearDRPCTopologyBuilder;
import org.learn.storm.ch03.bolts.AdderBolt;

@SuppressWarnings("deprecation")
public class AddTopology {
	public static void main(String[] args) {

		LocalDRPC drpc = new LocalDRPC();

		LinearDRPCTopologyBuilder builder = new LinearDRPCTopologyBuilder("add");
		builder.addBolt(new AdderBolt(), 2);
		
		Config conf = new Config();
		conf.setDebug(true);

		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("drpcder-topology", conf, builder.createLocalTopology(drpc));
		String result = drpc.execute("add", "1+-1");
		System.out.println("计算成功1+-1成功，结果是:" + result);
		result = drpc.execute("add", "1+1+5+10");
		System.out.println("计算成功1+1+5+10成功，结果是:" + result);

		cluster.shutdown();
		drpc.shutdown();
	}
}
