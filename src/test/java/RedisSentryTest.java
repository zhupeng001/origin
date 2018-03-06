import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by apple on 2018/3/3.
 */
public class RedisSentryTest {
    @Test
    public void test(){
        Jedis jedis=new Jedis("127.0.0.1",6380);
        jedis.set("name","tony");
        String name=jedis.get("name");
        System.out.println("返回的结果："+name);
        jedis.close();
    }
}
