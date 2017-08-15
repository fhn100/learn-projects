package org.learn.storm.ch02.bolts;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

public class WordCounter extends BaseRichBolt {

	private static final long serialVersionUID = -4610119190874261335L;

	private Integer id;
	private String name;
	private Map<String, Integer> counters;
	private OutputCollector collector;

	@Override
	public void cleanup() {
		System.out.println("-- 单词数 【" + name + "-" + id + "】 --");
		for (Map.Entry<String, Integer> entry : counters.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
		this.counters = new HashMap<String, Integer>();
		this.collector = collector;
		this.name = context.getThisComponentId();
		this.id = context.getThisTaskId();
	}

	@Override
	public void execute(Tuple input) {
		String word = input.getStringByField("word");
		/**
		 * 如果单词尚不存在于map，我们就创建一个，如果已在，我们就为它加1
		 */
		if (!counters.containsKey(word)) {
			counters.put(word, 1);
		} else {
			Integer c = counters.get(word) + 1;
			counters.put(word, c);
		}
		// 对元组作为应答
		collector.ack(input);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {

	}
}
