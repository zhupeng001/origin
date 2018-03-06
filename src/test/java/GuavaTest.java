import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import org.junit.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by apple on 2018/2/4.
 */

public class GuavaTest {
    @Test
    public void optionalTest(){
        String a2="10";
        String a1="5";
        Optional<String> a=Optional.fromNullable(a1);
        Optional<String> b= Optional.of(a2);
        System.out.println(Integer.parseInt(a2)+Integer.parseInt(a1));
    }
    @Test
    public void joinerTest(){
        /*
         on:制定拼接符号，如：test1-test2-test3 中的 “-“ 符号
         skipNulls()：忽略NULL,返回一个新的Joiner实例
         useForNull(“Hello”)：NULL的地方都用字符串”Hello”来代替
        */
        StringBuilder sb=new StringBuilder();
        Joiner.on(",").skipNulls().appendTo(sb,"Hello","guava");
        System.out.println(sb);
        System.out.println(Joiner.on(",").useForNull("none").join(1,sb,3));
        System.out.println(Joiner.on(",").skipNulls().join(Arrays.asList(1, 2, 3, 4, null, 6)));
        Map<String,String> map=new HashMap<String,String>();
        map.put("key1","value1");
        map.put("key2","value2");
        map.put("key3","value3");
        System.out.println(Joiner.on(",").withKeyValueSeparator("=").join(map));
    }
    private static Integer sum(Optional<Integer> a,Optional<Integer> b){
        //isPresent():如果Optional包含非null的引用（引用存在），返回true
        Integer value1=a.or(0);  //返回Optional所包含的引用,若引用缺失,返回指定的值
        Integer value2=b.get(); //返回所包含的实例,它必须存在,通常在调用该方法时会调用isPresent()判断是否为nullout
        System.out.println(a.getClass().getName());
        return value1+value2;
    }
}
