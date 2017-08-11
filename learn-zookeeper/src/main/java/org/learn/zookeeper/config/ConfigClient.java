package org.learn.zookeeper.config;

import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;

public class ConfigClient implements NodeCacheListener {

	private NodeCache nodeCache;

	public ConfigClient(NodeCache nodeCache) {
		super();
		this.nodeCache = nodeCache;
		nodeCache.getListenable().addListener(this);
	}

	@Override
	public void nodeChanged() throws Exception {
		System.out.println("客户端 :" + this);
		System.out.println("path : " + nodeCache.getCurrentData().getPath());
		System.out.println("data : " + new String(nodeCache.getCurrentData().getData()));
	}

}
