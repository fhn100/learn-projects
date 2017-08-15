package eric.learn.zookeekper.cache;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.learn.zookeeper.config.ConfigClient;
import org.learn.zookeeper.config.ConfigServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:/spring.xml")
public class CacheTest {

	@Autowired
	private ApplicationContext context;
	@Autowired
	private ConfigServer configServer;

	@Test
	public void testSyncConfig() {
		try {
			// 模拟创建两个客户端
			context.getBean(ConfigClient.class);
			context.getBean(ConfigClient.class);
			TimeUnit.SECONDS.sleep(1);
			configServer.syncConfig("测试1");
			TimeUnit.SECONDS.sleep(1);
			configServer.syncConfig("测试2");
			TimeUnit.MINUTES.sleep(10);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
