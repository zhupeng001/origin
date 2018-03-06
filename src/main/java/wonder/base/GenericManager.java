package wonder.base;

import java.io.Serializable;

/**
 * Created by apple on 2018/2/16.
 */
public interface GenericManager<T,PK extends Serializable> {
    T get(PK id);
    public void save(T obj);
}
