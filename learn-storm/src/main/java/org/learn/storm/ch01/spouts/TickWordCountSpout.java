package org.learn.storm.ch01.spouts;

import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

public class TickWordCountSpout extends BaseRichSpout {

	private static final long serialVersionUID = -2474127696248055861L;

	private SpoutOutputCollector collector;
	private String[] sentences = { "a", "b", "c" };
	private int index = 0;

	@SuppressWarnings("rawtypes")
	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
	}

	/**
	 * 每毫秒射出一个字符
	 */
	@Override
	public void nextTuple() {
		collector.emit(new Values(sentences[index]));
		index++;
		if (index>=sentences.length) {
			index = 0;
		}
		Utils.sleep(1);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}

}
