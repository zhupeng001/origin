import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by apple on 2018/3/3.
 */
public class ArrayTest {
    @Test
    public void asListTest(){
        String[] str=new String[]{ "hello", "world" };
        List list= Arrays.asList(str);
        System.out.println(list.get(0));
        str[0]="stop";
        System.out.println(list.get(0));
    }
}
