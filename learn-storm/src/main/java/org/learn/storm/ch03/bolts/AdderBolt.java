package org.learn.storm.ch03.bolts;

import java.security.InvalidParameterException;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class AdderBolt extends BaseRichBolt {

	private static final long serialVersionUID = -7936996622099581968L;

	private OutputCollector collector;

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		String[] numbers = input.getString(1).split("\\+");
		Integer added = 0;
		if (numbers.length < 2) {
			throw new InvalidParameterException("Should be at least 2 numbers");
		}
		for (String num : numbers) {
			added += Integer.parseInt(num);
		}
		collector.emit(new Values(input.getValue(0), added));
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("id", "result"));
	}

}
