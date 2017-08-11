package org.learn.storm.ch03.topologies;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;
import org.learn.storm.ch02.bolts.WordNormalizer;
import org.learn.storm.ch02.spouts.WordReader;
import org.learn.storm.ch03.bolts.WordCounter;
import org.learn.storm.ch03.spouts.SignalsSpout;

public class WordTopology1 {
	/**
	 * 1）shuffleGrouping（随机分组）
	 * 
	 * 2）fieldsGrouping（按照字段分组，在这里即是同一个单词只能发送给一个Bolt）
	 * 
	 * 3）allGrouping（广播发送，即每一个Tuple，每一个Bolt都会收到）
	 * 
	 * 4）globalGrouping（全局分组，将Tuple分配到task id值最低的task里面）
	 * 
	 * 5）noneGrouping（随机分派）
	 * 
	 * 6）directGrouping（直接分组，指定Tuple与Bolt的对应发送关系）
	 * 
	 * 7）localOrShuffleGrouping
	 * 
	 * 8）customGrouping （自定义的Grouping）
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("WordReader", new WordReader());
		builder.setSpout("SignalsSpout", new SignalsSpout());
		builder.setBolt("WordNormalizer", new WordNormalizer())
				// 随机数据流组
				.shuffleGrouping("WordReader");
		builder.setBolt("WordCounter", new WordCounter(), 3)
				// 域数据流组
				.fieldsGrouping("WordNormalizer", new Fields("word"))
				// 全部数据流组
				.allGrouping("SignalsSpout", "signals");

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
