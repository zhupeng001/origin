package wonder.base;

import java.io.Serializable;

/**
 * Created by apple on 2018/2/16.
 */
public interface GenericDao<T, PK extends Serializable> {
    public void save(T object);
    T get(PK id);
}
