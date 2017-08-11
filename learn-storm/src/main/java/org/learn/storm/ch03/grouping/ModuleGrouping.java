package org.learn.storm.ch03.grouping;

import java.util.ArrayList;
import java.util.List;

import org.apache.storm.generated.GlobalStreamId;
import org.apache.storm.grouping.CustomStreamGrouping;
import org.apache.storm.task.WorkerTopologyContext;

public class ModuleGrouping implements CustomStreamGrouping {

	private static final long serialVersionUID = -2854395391904657718L;

	private List<Integer> tasks;

	@Override
	public void prepare(WorkerTopologyContext context, GlobalStreamId stream, List<Integer> tasks) {
		this.tasks = tasks;
	}

	@Override
	public List<Integer> chooseTasks(int taskId, List<Object> values) {
		// 指定第一个bolt接收数据元组
		List<Integer> boltIds = new ArrayList<Integer>();
		boltIds.add(tasks.get(0));
		return boltIds;
	}

}
