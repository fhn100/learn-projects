package org.learn.storm.ch01.spouts;

import java.util.Map;
import java.util.Random;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

public class HelloWorldSpout extends BaseRichSpout {

	private static final long serialVersionUID = 8119674620756641386L;

	private static final int MAX_RANDOM = 10;
	private int referenceRandom;
	private SpoutOutputCollector collector;

	public HelloWorldSpout() {
		super();
		Random random = new Random();
		referenceRandom = random.nextInt(MAX_RANDOM);
	}

	@Override
	public void nextTuple() {
		Utils.sleep(100);
		Random random = new Random();
		int instanceRandom = random.nextInt(MAX_RANDOM);
		if (instanceRandom == referenceRandom) {
			collector.emit(new Values("Hello World"));
		} else {
			collector.emit(new Values("Other Random World"));
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("sentence"));
	}

}
