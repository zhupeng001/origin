package wonder.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import wonder.util.StringUtil;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JedisClusterFactory implements FactoryBean<JedisCluster>, InitializingBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(JedisClusterFactory.class);
	private JedisPoolConfig jedisPoolConfig;
	private JedisCluster jedisCluster;
	private int connectionTimeout = 2000;
	private int soTimeout = 3000;//返回值的超时时间
	private int maxRedirections = 5;//出现异常最大重试次数
	private String jedisClusterNodes;
	private String password;

	public void afterPropertiesSet() throws Exception {
		if (StringUtil.isNullStr(jedisClusterNodes)) {
			throw new NullPointerException("jedisClusterNodes is null.");
		}
		Set<HostAndPort> haps = new HashSet<HostAndPort>();
		String[] nodes = jedisClusterNodes.split(",");
		for (String node : nodes) {
			String[] arr = node.split(":");
			if (arr.length != 2) {
				throw new ParseException("node address error !", node.length() - 1);
			}
			haps.add(new HostAndPort(arr[0], Integer.valueOf(arr[1])));
		}
		jedisPoolConfig.setBlockWhenExhausted(false);
		jedisPoolConfig.setTimeBetweenEvictionRunsMillis(1000);
//		jedisPoolConfig.setNumTestsPerEvictionRun(300);
		jedisPoolConfig.setTestWhileIdle(true);
		jedisPoolConfig.setTestOnReturn(false);
		jedisPoolConfig.setTestOnBorrow(false);
		if ("".equals(StringUtil.null2Str(password))) {
			jedisCluster = new JedisCluster(haps, connectionTimeout, soTimeout, maxRedirections, jedisPoolConfig);
		} else {
			jedisCluster = new JedisCluster(haps, connectionTimeout, soTimeout, maxRedirections, password, jedisPoolConfig);
		}
	}


	public JedisCluster getObject() throws Exception {
		return jedisCluster;
	}


	public Class<?> getObjectType() {
		return (this.jedisCluster != null ? this.jedisCluster.getClass() : JedisCluster.class);
	}


	public boolean isSingleton() {
		return true;
	}

	public JedisPoolConfig getJedisPoolConfig() {
		return jedisPoolConfig;
	}

	public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
		this.jedisPoolConfig = jedisPoolConfig;
	}

	public JedisCluster getJedisCluster() {
		return jedisCluster;
	}

	public void setJedisCluster(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getSoTimeout() {
		return soTimeout;
	}

	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}

	public int getMaxRedirections() {
		return maxRedirections;
	}

	public void setMaxRedirections(int maxRedirections) {
		this.maxRedirections = maxRedirections;
	}

	public String getJedisClusterNodes() {
		return jedisClusterNodes;
	}

	public void setJedisClusterNodes(String jedisClusterNodes) {
		this.jedisClusterNodes = jedisClusterNodes;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}






	// 获取集群key所在的主机节点。
	public Map<String, JedisPool> nodeMap = null;

	private void initClusterInfo() {
		nodeMap = jedisCluster.getClusterNodes();
	}





}
