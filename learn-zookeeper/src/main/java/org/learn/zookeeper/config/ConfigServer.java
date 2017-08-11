package org.learn.zookeeper.config;

import org.apache.curator.framework.CuratorFramework;

public class ConfigServer {

	private String path;

	private CuratorFramework client;

	public ConfigServer(CuratorFramework client, String path) {
		super();
		this.client = client;
		this.path = path;
		try {
			client.checkExists().creatingParentContainersIfNeeded().forPath(path);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void syncConfig(String data) {
		try {
			client.setData().forPath(path, data.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
