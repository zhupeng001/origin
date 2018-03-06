import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import wonder.entity.User;
import wonder.redis.cache.UserRedisCacheManager;
import wonder.service.UserManager;

import javax.annotation.Resource;
import javax.xml.bind.SchemaOutputResolver;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by apple on 2018/2/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"classpath:applicationContext.xml"} )
//------------如果加入以下代码，所有继承该类的测试类都会遵循该配置，也可以不加，在测试类的方法上///控制事务，参见下一个实例
//这个非常关键，如果不加入这个注解配置，事务控制就会完全失效！
//@Transactional
//这里的事务关联到配置文件中的事务控制器（transactionManager = "transactionManager"），同时//指定自动回滚（defaultRollback = true）。这样做操作的数据才不会污染数据库！
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
//------------
public class RedisTest {
    @Autowired
    private UserManager userManager;
    @Autowired
    private UserRedisCacheManager userRedisCacheManager;

    private String userName;

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public void setUserRedisCacheManager(UserRedisCacheManager userRedisCacheManager) {
        this.userRedisCacheManager = userRedisCacheManager;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    /*@Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;*/

    /*@Resource(name = "hibernateTemplate")

    private HibernateTemplate hibernateTemplate;

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }*/

   /* public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }*/

    @Test
    /*@Transactional   //标明此方法需使用事务*/
    /*@Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚*/
    public void test1(){
        //添加一个key
        /*ValueOperations<String,Object> value=redisTemplate.opsForValue();
        value.set("taici","快过年了");
        System.out.println(value.get("taici"));

        //添加一个hash
        HashOperations<String,Object,Object> hash=redisTemplate.opsForHash();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("name","sanye");
        map.put("age","18");
        hash.putAll("user001",map);
        System.out.println(hash.entries("user001"));*/

        /*//添加一个list列表
        ListOperations<String,Object> list=redisTemplate.opsForList();
        list.rightPush("mobile","huawei");
        list.rightPush("mobile","pingguo");
        list.rightPush("mobile","sanxing");
        System.out.println(list.range("mobile",0,-1));*/

        //添加一个set集合
        /*SetOperations<String,Object> set=redisTemplate.opsForSet();
        set.add("katong","你的名字");
        set.add("katong","秒速五厘米");
        set.add("katong","凹凸世界");
        set.add("katong","我是江小白");
        set.add("katong","画江湖之换世门生");
        System.out.println(set.members("katong"));*/

        //添加有序的set集合
        /*ZSetOperations<String,Object> zset=redisTemplate.opsForZSet();
        zset.add("cat","英国短毛猫",0);
        zset.add("cat","加菲猫",1);
        zset.add("cat","橘猫",2);
        System.out.println(zset.rangeByScore("cat",0,2));*/
    }
    @Test
    public void addUser(){
        User user=new User(UUID.randomUUID().toString(),"string","15859120753","man","yanan");
        userRedisCacheManager.save(user);
    }
    @Test
    public void get(){
        System.out.println("查询用户信息");
        userName="龙";
        User user=userRedisCacheManager.get(userName);
        System.out.println(user);
    }
}
