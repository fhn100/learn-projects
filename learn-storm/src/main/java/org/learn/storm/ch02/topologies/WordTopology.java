package org.learn.storm.ch02.topologies;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;
import org.learn.storm.ch02.bolts.WordCounter;
import org.learn.storm.ch02.bolts.WordNormalizer;
import org.learn.storm.ch02.spouts.WordReader;

public class WordTopology {
	public static void main(String[] args) {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("WordReader", new WordReader());
		builder.setBolt("WordNormalizer", new WordNormalizer()).shuffleGrouping("WordReader");
		builder.setBolt("WordCounter", new WordCounter(), 3).fieldsGrouping("WordNormalizer", new Fields("word"));

		Config conf = new Config();
		conf.setDebug(true);
		conf.put("wordsFile", "src/main/resources/ch01/words.txt");
		conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);

		if (args != null && args.length > 0) {
			conf.setNumWorkers(3);
			try {
				StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("WordTopology", conf, builder.createTopology());
			Utils.sleep(30000);
			cluster.killTopology("WordTopology");
			cluster.shutdown();
		}
	}
}
