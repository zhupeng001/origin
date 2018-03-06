import org.junit.Test;
import wonder.redis.HandMadeRedisCli;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by apple on 2018/3/3.
 */
public class HandMadeRedisCliTest {
    @Test
    public void clientTest() throws IOException {
        HandMadeRedisCli redis=new HandMadeRedisCli();
        redis.set("hello","tony");
        String value=redis.get("hello");
        System.out.println("开始打印结果");
        System.out.println(value);
        System.out.println("打印结束");
    }
}
