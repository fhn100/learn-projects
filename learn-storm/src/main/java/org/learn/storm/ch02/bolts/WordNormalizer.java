package org.learn.storm.ch02.bolts;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class WordNormalizer extends BaseRichBolt {

	private static final long serialVersionUID = -3865931811064356316L;

	private Integer id;
	private OutputCollector collector;

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
		id = context.getThisTaskId();
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		String line = input.getStringByField("line");
		System.out.println("taskId为" + id + "的bolt接收到元组数据[" + line + "]");
		String[] words = line.split(" ");
		for (String word : words) {
			word = word.trim();
			if (!word.isEmpty()) {
				word = word.toLowerCase();
				// 发布这个单词
				collector.emit(new Values(word));
			}
		}
		// 对元组做出应答
		collector.ack(input);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}

}
