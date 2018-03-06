import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wonder.dao.UserDao;
import wonder.redis.JedisClusterFactory;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by apple on 2018/2/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringTest {
    private Logger logger=LoggerFactory.getLogger(SpringTest.class);
    @Autowired
    private JedisClusterFactory jCluster;

    private static int size=1000000;

    private static BloomFilter<Integer> bloomFilter=BloomFilter.create(Funnels.integerFunnel(),size);

    private UserDao userDao;
    @Test
    public void test() throws Exception {
        System.out.println(jCluster.getObject());
    }

    /*@PostConstruct
    public void init(){
        //初始化布隆过滤器
        for(int i=0;i<size;i++){
            bloomFilter.put(i);
        }
    }*/
    @Test
    public void redisClusterTest() throws Exception {
        /*JedisCluster jedisCluster=jedisClusterFactory.getObject();
        String key="ActivityInfo:4";
        System.out.println(jedisCluster.get(key));*/

    }
    @Test
    public void bloomFilterTest1(){
        List<Integer> list=new ArrayList<Integer>(1000);
        System.out.println("list长度:"+list.size());
        for(int i=0;i<size;i++){
            if(!bloomFilter.mightContain(i)){
               list.add(i);
            }
        }

        System.out.println("误伤数量："+list.size());
    }
    @Test
    public void bloomFilterTest2(){
        BloomFilter<String> b = BloomFilter.create(Funnels.stringFunnel(Charset.forName("utf-8")), 1000, 0.000001);
        b.put("121");
        b.put("122");
        b.put("123");
        System.out.println(b.mightContain(UUID.randomUUID().toString()));
        BloomFilter<String> b1 = BloomFilter.create(Funnels.stringFunnel(Charset.forName("utf-8")), 1000, 0.000001);
        b1.put("aba");
        b1.put("abb");
        b1.put("abc");
        b1.putAll(b);
        System.out.println(b1.mightContain("123"));
    }

}