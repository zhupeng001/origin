package wonder.base;

import java.io.Serializable;

/**
 * Created by apple on 2018/2/16.
 */
public class GenericManagerImpl<T,PK extends Serializable> implements GenericManager<T,PK> {
    protected GenericDao genericDao;

    public GenericManagerImpl(){}

    public GenericManagerImpl(final GenericDao<T,PK> genericDao){
        this.genericDao=genericDao;
    }


    public T get(PK id) {
        return (T) genericDao.get(id);
    }

    public void save(T obj) {
        genericDao.save(obj);
    }
    public GenericDao getGenericDao() {
        return genericDao;
    }

    public void setGenericDao(GenericDao genericDao) {
        this.genericDao = genericDao;
    }
}