package wonder.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import wonder.base.GenericDao;
import wonder.util.SerializeUtil;
import java.io.Serializable;
import java.util.*;

/**
 * Created by apple on 2018/2/16.
 */
public abstract class RedisCacheManager<T,PK extends Serializable> implements GenericDao<T,PK> {
    protected final Log log= LogFactory.getLog(getClass());

    protected RedisManager redisManager;

    protected String key;

    protected int memExpiry=0;

    protected boolean asSyncLoac=false;

    //并发时同步等待最长时间
    protected int waitSecond=3;

    protected ThreadPool threadPool;

    //加载数据时的锁池
    private static Map<String,Object> lockMap=new HashMap<String, Object>();

    public String getCacheKey(PK id){
        return key+":"+id;
    }

    protected int creatExpiry(){
        if(memExpiry<=0)
            return 0;
        return memExpiry;
    }

    protected long getExpiry(PK id){
        return redisManager.ttl(getCacheKey(id));
    }

    protected void setExpiry(PK id){
        int expiry=creatExpiry();
        if(expiry>0){
            redisManager.expire(getCacheKey(id),creatExpiry());
        }
    }

    /*继承此类需要重载此方法
    * @param id
    * @return
    * */

    abstract protected  T createObject(PK id);
    /*继承此类需要重载此方法
    * @param id
    * @return
    * */
    protected T getObject(PK id){
        return null;
    }

    protected  T getCache(PK id){
        //从redis中回去成功，则返回
        byte[] obj=redisManager.get(this.getCacheKey(id).getBytes());
        if(obj!=null){
            return (T) SerializeUtil.unserialize(obj);
        }
        return null;
    }

    private T _getObj(PK id){
        try {
            T obj=this.createObject(id);
            if(obj!=null){
                int expiry=creatExpiry();
                if(expiry==0){
                    redisManager.set(getCacheKey(id).getBytes(),SerializeUtil.serialize(obj));
                }else{
                    redisManager.setex(getCacheKey(id).getBytes(),SerializeUtil.serialize(obj),creatExpiry());
                }
            }else{
                redisManager.del(getCacheKey(id));
            }
            return obj;
        } catch (Exception e) {
            log.error(String.format("BaseSortSet._loadAll.exception,params(id:%s)",e));
        }
        return null;
    }
    public void update(PK id,T obj){
        if(obj==null){
            log.error("update the t is null");
            return;
        }
        try {
            int expiry=creatExpiry();
            String key=getCacheKey(id);
            if(expiry==0){
                redisManager.set(key.getBytes(),SerializeUtil.serialize(obj));
            }else{
                redisManager.setex(key.getBytes(),SerializeUtil.serialize(obj),creatExpiry());
            }
        } catch (Exception e) {
            log.error(String.format("redisManager.zadd.exeception,param(id:%s,obj:s%)",this.getCacheKey(id),obj),e);
        }
    }
    /**
     * 确保并发的时候，只有一个线程在
     * @param id
     * @return
   */
    protected T getObj(PK id){
        String cacheKey=getCacheKey(id);
        T object=null;
        Object lock=null;
        boolean isLoading=false;
        try {
            synchronized (lockMap){
                lock=lockMap.get(cacheKey);
                if(lock==null){
                    lock=cacheKey;
                    lockMap.put(cacheKey,lock);
                }else {
                    isLoading=true;
                }
            }
            if(isLoading){
                if(!this.asSyncLoac){
                    synchronized(lock){
                        try {
                            lock.wait(waitSecond*1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    object=getCache(id);
                    if(object==null){
                        object=_getObj(id);
                    }else{
                        object=_getObj(id);
                    }
                }
            }
            else{
                object=_getObj(id);
                if(object!=null){
                    this.update(id,object);
                }
            }
        } catch (Exception e) {
            log.error(String.format("GenericCacheManager.getObj.exception,params(id:%s)",id),e);
        } finally {
            if(!isLoading){
                synchronized(lockMap){
                    lockMap.remove(cacheKey);
                }
                if(!this.asSyncLoac){
                    synchronized(lock){
                        lock.notifyAll();
                    }
                }
            }
        }
        return object;
    }
    protected void getObjSync(final PK id){
        setExpiry(id);
        if(threadPool!=null){
            threadPool.execute(new Runnable() {
                public void run() {
                    getObj(id);
                }
            });
        }else{
            getObj(id);
        }
    }
    /**
     * 批量获取
     * @param keys
     * @return
     */
    public List<T> mget(Set<String> keys){
        //

        Map<JedisPool, Set<String>> jedisMap = new HashMap<JedisPool, Set<String>>();
        for (String key: keys) {
            JedisPool jedisPool = redisManager.getJedisByKey(key);
            Set<String> nowSet = jedisMap.get(jedisPool);
            if (nowSet != null) {

            }
            else {
                nowSet = new HashSet<String>();
            }

            nowSet.add(key);
            jedisMap.put(jedisPool, nowSet);
        }


        List<T> renturnList = new ArrayList<T>();

        for (Map.Entry<JedisPool, Set<String>> sm : jedisMap.entrySet()) {
            Jedis jedis = sm.getKey().getResource();
            Set<String> fkeys = sm.getValue();

            try {
                Pipeline p = jedis.pipelined();
                for (String key : fkeys) {
                    p.get(key.getBytes());
                }
                List<Object> objList = p.syncAndReturnAll();
                for (Object o : objList) {
                    if (o != null) {
                        renturnList.add((T) SerializeUtil.unserialize((byte[]) o));
                    }
                }
//				System.out.println("mget for keys " + fkeys);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }


        return renturnList;
    }


    public T get(final PK id) {
        try {
            if (this.memExpiry > 0) {
                long expiry = this.getExpiry(id);
                if (expiry == -2) {// 第一次加载，同步加载
                    T obj = getObj(id);
                    return obj;
                } else if (expiry == -1) {// 过期重新异步加载
                    getObjSync(id);
                }
                return getCache(id);
            } else {
                T obj = getCache(id);
                if (obj == null) {
                    long expiry = this.getExpiry(id);
                    if (expiry == -2) {// 第一次加载，同步加载
                        obj = getObj(id);
                        return obj;
                    }
                    obj = getCache(id);
                }
                return obj;
            }
        } catch (Exception e) {
            log.error(String.format("redisManager.get.exception, params(id:%s)",
                    this.getCacheKey(id)), e);
        }
        return null;
    }

    public void remove(PK id) {
        try {
            redisManager.del(getCacheKey(id));
        } catch (Exception e) {
            log.error(String.format("redisManager.del.exception, params(id:%s)", this.getCacheKey(id)), e);
        }
    }

    public List<T> get(PK[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<T> getBatch(PK[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    public Map<PK, T> getBatchMap(PK[] ids) {
        // TODO Auto-generated method stub
        return null;
    }


    public Log getLog() {
        return log;
    }
    public RedisManager getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getMemExpiry() {
        return memExpiry;
    }

    public void setMemExpiry(int memExpiry) {
        this.memExpiry = memExpiry;
    }

    public boolean isAsSyncLoac() {
        return asSyncLoac;
    }

    public void setAsSyncLoac(boolean asSyncLoac) {
        this.asSyncLoac = asSyncLoac;
    }

    public int getWaitSecond() {
        return waitSecond;
    }

    public void setWaitSecond(int waitSecond) {
        this.waitSecond = waitSecond;
    }

    public ThreadPool getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ThreadPool threadPool) {
        this.threadPool = threadPool;
    }

    public static Map<String, Object> getLockMap() {
        return lockMap;
    }

    public static void setLockMap(Map<String, Object> lockMap) {
        RedisCacheManager.lockMap = lockMap;
    }
}
