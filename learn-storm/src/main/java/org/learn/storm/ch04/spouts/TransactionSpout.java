package org.learn.storm.ch04.spouts;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionSpout extends BaseRichSpout {

	private static final long serialVersionUID = -9151620271329494511L;

	private Logger logger = LoggerFactory.getLogger(TransactionSpout.class);

	private static final Integer MAX_FAILS = 2;
	Map<Integer, String> messages;
	Map<Integer, Integer> transactionFailureCount;
	Map<Integer, String> toSend;
	private SpoutOutputCollector collector;

	@Override
	public void ack(Object msgId) {
		messages.remove(msgId);
		logger.info("Message fully processed [" + msgId + "]");
	}

	@Override
	public void close() {

	}

	@Override
	public void fail(Object msgId) {
		if (!transactionFailureCount.containsKey(msgId)) {
			throw new RuntimeException("错误, 没有找到 元组[" + msgId + "]");
		}
		Integer transactionId = (Integer) msgId;

		// Get the transactions fail
		Integer failures = transactionFailureCount.get(transactionId) + 1;
		if (failures >= MAX_FAILS) {
			// If exceeds the max fails will go down the topology
			throw new RuntimeException(
					"错误, transaction id [" + transactionId + "] 已失败太多次了 [" + failures + "]");
		}
		// If not exceeds the max fails we save the new fails quantity and
		// re-send the message
		transactionFailureCount.put(transactionId, failures);
		toSend.put(transactionId, messages.get(transactionId));
		logger.info("重发消息 [" + msgId + "]");
	}

	@Override
	public void nextTuple() {
		if (!toSend.isEmpty()) {
			for (Map.Entry<Integer, String> transactionEntry : toSend.entrySet()) {
				Integer transactionId = transactionEntry.getKey();
				String transactionMessage = transactionEntry.getValue();
				collector.emit(new Values(transactionMessage), transactionId);
			}
			/*
			 * The nextTuple, ack and fail methods run in the same loop, so we
			 * can considerate the clear method atomic
			 */
			toSend.clear();
		}
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		Random random = new Random();
		messages = new HashMap<Integer, String>();
		toSend = new HashMap<Integer, String>();
		transactionFailureCount = new HashMap<Integer, Integer>();
		for (int i = 0; i < 100; i++) {
			messages.put(i, "transaction_" + random.nextInt());
			transactionFailureCount.put(i, 0);
		}
		toSend.putAll(messages);
		this.collector = collector;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("transactionMessage"));
	}

}
