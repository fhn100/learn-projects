package org.learn.storm.ch01.bolts;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

public class HelloWorldBolt extends BaseRichBolt {

	private static final long serialVersionUID = -1339786553283881242L;

	private int myCount = 0;

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map conf, TopologyContext context, OutputCollector collector) {

	}

	@Override
	public void execute(Tuple tuple) {
		String sentence = tuple.getStringByField("sentence");
		if ("Hello World".equals(sentence)) {
			myCount++;
			System.out.println("Found a Hello World! My count is now:" + myCount);
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {

	}

}
