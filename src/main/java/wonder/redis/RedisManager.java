package wonder.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.*;
import redis.clients.util.JedisClusterCRC16;
import wonder.base.ScoreMember;

import java.util.*;

public class RedisManager {
	protected final Log log = LogFactory.getLog(getClass());

	private JedisCluster jedisCluster;

	public JedisCluster getJedis() {
		return jedisCluster;
	}

	public String echo(String string) {
		String result = getJedis().echo(string);
		return result;
	}

	/**
	 * <p>
	 * 通过key给field设置指定的值,如果key不存在,则先创建
	 * </p>
	 * 
	 * @param key
	 * @param field
	 *            字段
	 * @param value
	 * @return 如果存在返回0 异常返回null
	 */
	public Long hset(String key, String field, String value) {
		Long result = getJedis().hset(key, field, value);
		return result;
	}

	/**
	 * <p>
	 * 通过key给field设置指定的值,如果key不存在则先创建,如果field已经存在,返回0
	 * </p>
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public Long hsetnx(String key, String field, String value) {
		Long result = getJedis().hsetnx(key, field, value);
		return result;
	}

	/**
	 * <p>
	 * 通过key同时设置 hash的多个field
	 * </p>
	 * 
	 * @param key
	 * @param hash
	 * @return 返回OK 异常返回null
	 */
	public String hmset(String key, Map<String, String> hash) {
		String result = getJedis().hmset(key, hash);
		return result;
	}

	/**
	 * <p>
	 * 通过key 和 field 获取指定的 value
	 * </p>
	 * 
	 * @param key
	 * @param field
	 * @return 没有返回null
	 */
	public String hget(String key, String field) {
		String result = getJedis().hget(key, field);
		return result;
	}

	/**
	 * <p>
	 * 通过key 和 fields 获取指定的value 如果没有对应的value则返回null
	 * </p>
	 * 
	 * @param key
	 * @param fields
	 *            可以使 一个String 也可以是 String数组
	 * @return
	 */
	public List<String> hmget(String key, String... fields) {
		List<String> result = getJedis().hmget(key, fields);
		return result;
	}

	/**
	 * <p>
	 * 通过key获取所有的field和value
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, String> hgetAll(String key) {
		Map<String, String> result = getJedis().hgetAll(key);
		return result;
	}

	public Long hdel(String key, String field) {
		Long result = getJedis().hdel(key, field);
		return result;
	}

	public Long hlen(String key) {
		Long result = getJedis().hlen(key);
		return result;
	}

	public Boolean hexists(String key, String field) {

		Boolean result = getJedis().hexists(key, field);

		return result;
	}

	public Set<String> hkeys(String key) {

		Set<String> result = getJedis().hkeys(key);

		return result;
	}

	public List<String> hvals(String key) {

		List<String> result = getJedis().hvals(key);

		return result;
	}

	/**
	 * <p>
	 * 判断key是否存在
	 * </p>
	 * 
	 * @param key
	 * @return true OR false
	 */
	public boolean exists(String key) {
		boolean result = false;
		try {
			result = getJedis().exists(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return result;
	}

	/**
	 * <p>
	 * 删除指定的key,也可以传入一个包含key的数组
	 * </p>
	 * 
	 * @param key
	 *            一个key 也可以使 string 数组
	 * @return 返回删除成功的个数
	 */
	public Long del(String key) {
		Long result = 0L;
		try {
			result = getJedis().del(key);
		} catch (Exception e) {
			e.printStackTrace();
			result = 0L;
		} finally {

		}
		return result;
	}

	public List<String> sort(String key) {
		List<String> result = getJedis().sort(key);
		return result;
	}

	public Long move(String key, int dbIndex) {
		Long result = getJedis().move(key, dbIndex);
		return result;
	}

	public String type(String key) {
		String result = getJedis().type(key);
		return result;
	}

	public Long expire(String key, int seconds) {
		Long result = getJedis().expire(key, seconds);
		return result;
	}

	public Long expireAt(String key, Long unixTime) {
		Long result = getJedis().expireAt(key, unixTime);
		return result;
	}
	
	public void persist(String key){
		getJedis().persist(key);
	}

	public Long lpush(String key, String value) {

		Long result = getJedis().lpush(key, value);

		return result;
	}

	public Long rpush(String key, String value) {

		Long result = getJedis().rpush(key, value);

		return result;
	}

	public Long llen(String key) {

		Long result = getJedis().llen(key);

		return result;
	}

	public List<String> lrange(String key, long start, long end) {

		List<String> result = getJedis().lrange(key, start, end);

		return result;
	}

	public String ltrim(String key, long start, long end) {

		String result = getJedis().ltrim(key, start, end);

		return result;
	}

	public Long lpushx(String key, String value) {

		Long result = getJedis().lpushx(key, value);

		return result;
	}

	public Long rpushx(String key, String value) {

		Long result = getJedis().rpushx(key, value);

		return result;
	}

	public String lpop(String key) {

		String result = getJedis().lpop(key);

		return result;
	}

	public String rpop(String key) {

		String result = getJedis().rpop(key);

		return result;
	}

	public Long lrem(String key, long count, String value) {

		Long result = getJedis().lrem(key, count, value);

		return result;
	}

	public String lset(String key, long index, String value) {

		String result = getJedis().lset(key, index, value);

		return result;
	}

	public String lindex(String key, long index) {

		String result = getJedis().lindex(key, index);

		return result;
	}

	public Long sadd(String key, String member) {

		Long result = getJedis().sadd(key, member);

		return result;
	}

	public Long srem(String key, String member) {

		Long result = getJedis().srem(key, member);

		return result;
	}

	public Set<String> smembers(String key) {

		Set<String> result = getJedis().smembers(key);

		return result;
	}

	public Boolean sismember(String key, String member) {

		Boolean result = getJedis().sismember(key, member);

		return result;
	}

	public Long scard(String key) {

		Long result = getJedis().scard(key);

		return result;
	}

	public String spop(String key) {

		String result = getJedis().spop(key);

		return result;
	}

	public String srandmember(String key) {

		String result = getJedis().srandmember(key);

		return result;
	}

	public Long zadd(String key, double score, String member) {

		Long result = null;
		try {
			result = getJedis().zadd(key, score, member);
		} catch (Exception e) {
			log.error("", e);
		} finally {

		}
		return result;
	}

	public void zadd(String key, List<ScoreMember> sms) {

		try {
			for (ScoreMember sm : sms) {
				getJedis().zadd(key, sm.getScore(), sm.getMember());
			}
		} finally {

		}
	}

	public Long zrem(String key, String member) {

		Long result = null;
		try {
			result = getJedis().zrem(key, member);
		} catch (Exception e) {
			log.error("", e);
		} finally {

		}
		return result;
	}

	public Long zcard(String key) {

		Long result = getJedis().zcard(key);

		return result;
	}

	public Long zcount(String key, double min, double max) {

		Long result = getJedis().zcount(key, min, max);

		return result;
	}

	public Double zscore(String key, String member) {

		Double result = null;
		try {
			result = getJedis().zscore(key, member);
		} catch (Exception e) {
			log.error("", e);
		} finally {

		}
		return result;
	}

	public void zincrby(String key, List<ScoreMember> members) {

		try {
			for (ScoreMember sm : members) {
				getJedis().zincrby(key, sm.getScore(), sm.getMember());
			}
		} finally {

		}
	}

	public Double zincrby(String key, double score, String member) {

		Double result = null;
		try {
			result = getJedis().zincrby(key, score, member);
		} catch (Exception e) {
			log.error("", e);
		} finally {

		}
		return result;
	}

	public Set<String> zrange(String key, int start, int end) {

		Set<String> result = getJedis().zrange(key, start, end);

		return result;
	}

	public Set<String> zrevrange(String key, int start, int end) {

		Set<String> result = getJedis().zrevrange(key, start, end);

		return result;
	}

	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {

		Set<String> result = null;
		try {
			result = getJedis().zrevrangeByScore(key, max, min, offset, count);
		} catch (Exception e) {
			log.error("", e);
		} finally {

		}
		return result;
	}

	public Set<String> zrangeByScore(String key, double min, double max) {

		Set<String> result = null;
		try {
			result = getJedis().zrangeByScore(key, min, max);
		} catch (Exception e) {
			log.error("", e);
		} finally {

		}
		return result;
	}

	public Long zrank(String key, String member) {

		Long result = getJedis().zrank(key, member);

		return result;
	}

	public Long zrevrank(String key, String member) {

		Long result = getJedis().zrevrank(key, member);

		return result;
	}

	public Long zremrangeByRank(String key, int start, int end) {

		Long result = getJedis().zremrangeByRank(key, start, end);

		return result;
	}

	public Long zremrangeByScore(String key, double start, double end) {

		Long result = getJedis().zremrangeByScore(key, start, end);

		return result;
	}

	/**
	 * <p>
	 * 通过key获取储存在redis中的value
	 * </p>
	 * <p>
	 * 并释放连接
	 * </p>
	 * 
	 * @param key
	 * @return 成功返回value 失败返回null
	 */
	public String get(String key) {
		String result = null;
		try {
			result = getJedis().get(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return result;
	}
	
	/**
	 * <p>
	 * 通过key获取储存在redis中的value
	 * </p>
	 * <p>
	 * 并释放连接
	 * </p>
	 * 
	 * @param key
	 * @return 成功返回value 失败返回null
	 */
	public byte[] get(byte[] key) {
		byte[] result = null;
		try {
			result = getJedis().get(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return result;
	}

	/**
	 * <p>
	 * 向redis存入key和value,并释放连接资源
	 * </p>
	 * <p>
	 * 如果key已经存在 则覆盖
	 * </p>
	 * 
	 * @param key
	 * @param value
	 * @return 成功 返回OK 失败返回 0
	 */
	public String set(String key, String value) {
		String result = null;
		try {
			result = getJedis().set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			result = "0";
		} 
		return result;
	}
	
	/**
	 * <p>
	 * 向redis存入key和value,并释放连接资源
	 * </p>
	 * <p>
	 * 如果key已经存在 则覆盖
	 * </p>
	 * 
	 * @param key
	 * @param value
	 * @return 成功 返回OK 失败返回 0
	 */
	public String set(byte[] key, byte[] value) {
		String result = null;
		try {
			result = getJedis().set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			result = "0";
		} 
		return result;
	}

	/**
	 * <p>
	 * 设置key value,如果key已经存在则返回0,nx==> not exist
	 * </p>
	 * 
	 * @param key
	 * @param value
	 * @return 成功返回1 如果存在 和 发生异常 返回 0
	 */
	public Long setnx(String key, String value) {
		Long result = 0L;
		try {
			result = getJedis().setnx(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return result;
	}

	public String getSet(String key, String value) {

		String result = getJedis().getSet(key, value);

		return result;
	}

	public Long incrBy(String key, Integer integer) {

		Long result = getJedis().incrBy(key, integer);

		return result;
	}

	public Long strlen(String key) {

		Long result = getJedis().strlen(key);

		return result;
	}

	/**
	 * <p>
	 * 通过key 对value进行加值+1操作,当value不是int类型时会返回错误,当key不存在是则value为1
	 * </p>
	 * 
	 * @param key
	 * @return 加值后的结果
	 */
	public Long incr(String key) {

		Long result = getJedis().incr(key);

		return result;
	}

	/**
	 * <p>
	 * 通过key给指定的field的value加上给定的值
	 * </p>
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public Long hincrBy(String key, String field, long value) {

		Long result = getJedis().hincrBy(key, field, value);

		return result;
	}

	/**
	 * <p>
	 * 对key的值做减减操作,如果key不存在,则设置key为-1
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public Long decr(String key) {

		Long result = getJedis().decr(key);

		return result;
	}

	/**
	 * <p>
	 * 减去指定的值
	 * </p>
	 * 
	 * @param key
	 * @param integer
	 * @return
	 */
	public Long decrBy(String key, Integer integer) {

		Long result = getJedis().decrBy(key, integer);

		return result;
	}

	/**
	 * <p>
	 * 通过key向指定的value值追加值
	 * </p>
	 * 
	 * @param key
	 * @param str
	 * @return 成功返回 添加后value的长度 失败 返回 添加的 value 的长度 异常返回0L
	 */
	public Long append(String key, String str) {

		Long res = null;
		try {

			res = getJedis().append(key, str);
		} catch (Exception e) {

			e.printStackTrace();
			res = 0L;
		} finally {

		}
		return res;
	}

	public String subStr(String key, int Start, int end) {

		String result = getJedis().substr(key, Start, end);

		return result;
	}

	/**
	 * <p>
	 * 设置key value并制定这个键值的有效期
	 * </p>
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 *            单位:秒
	 * @return 成功返回OK 失败和异常返回null
	 */
	public String setex(String key, String value, int seconds) {
		String res = null;
		try {
			res = getJedis().setex(key, seconds, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * <p>
	 * 设置key value并制定这个键值的有效期
	 * </p>
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 *            单位:秒
	 * @return 成功返回OK 失败和异常返回null
	 */
	public String setex(byte[] key, byte[] value, int seconds) {
		String res = null;
		try {
			res = getJedis().setex(key, seconds, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public Long setRange(String key, long offset, String value) {

		Long result = getJedis().setrange(key, offset, value);

		return result;
	}

	public String getRange(String key, long StartOffset, long endOffset) {

		String result = getJedis().getrange(key, StartOffset, endOffset);

		return result;
	}

	public List<String> sort(String key, SortingParams params) {

		List<String> sortedResult = getJedis().sort(key, params);

		return sortedResult;
	}

	public long ttl(String key) {

		long expire = getJedis().ttl(key);

		return expire;
	}

	public JedisCluster getJedisCluster() {
		return jedisCluster;
	}

	public void setJedisCluster(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}




	// 获取集群key所在的主机节点。
	public Map<String, JedisPool> nodeMap = null;
	public TreeMap<Long, String> slotHostMap = null;


	private Boolean isInit = false;
	private Date initTime = new Date();

	private synchronized void  initClusterInfo() {
		// 30分钟重新获取
		if (!isInit || ((new Date()).getTime() - initTime.getTime()) > 1000*60*30) {
			nodeMap = jedisCluster.getClusterNodes();
			String anyHost = nodeMap.keySet().iterator().next();
			slotHostMap = getSlotHostMap(anyHost);
			initTime = new Date();
			isInit = true;
		}
	}

	private static TreeMap<Long, String> getSlotHostMap(String anyHostAndPortStr) {
		TreeMap<Long, String> tree = new TreeMap<Long, String>();
		String parts[] = anyHostAndPortStr.split(":");
		HostAndPort anyHostAndPort = new HostAndPort(parts[0], Integer.parseInt(parts[1]));
		Jedis jedis = null;
		try {
			jedis = new Jedis(anyHostAndPort.getHost(), anyHostAndPort.getPort());
			List<Object> list = jedis.clusterSlots();
			for (Object object : list) {
				List<Object> list1 = (List<Object>) object;
				List<Object> master = (List<Object>) list1.get(2);
				String hostAndPort = new String((byte[]) master.get(0)) + ":" + master.get(1);
				tree.put((Long) list1.get(0), hostAndPort);
				tree.put((Long) list1.get(1), hostAndPort);
			}

		} catch (Exception e) {

		} finally {
			if (jedis != null)
				jedis.close();
		}
		return tree;
	}

	public JedisPool getJedisByKey(String key) {
		if (!isInit || ((new Date()).getTime() - initTime.getTime()) > 1000*60*30) {
			initClusterInfo();
		}
		//获取槽号
		int slot = JedisClusterCRC16.getSlot(key);

		//获取到对应的Jedis对象
		// +1 让开始槽点落在自己的区间。
		Map.Entry<Long, String> entry = slotHostMap.lowerEntry(Long.valueOf(slot) +1);

//		Jedis jedis = nodeMap.get(entry.getValue()).getResource();

		return nodeMap.get(entry.getValue());

//		return jedis;

	}

}
