package wonder.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by apple on 2018/2/16.
 */
public class GenericDaoHibernate<T,PK extends Serializable> extends HibernateDaoSupport implements GenericDao<T,PK> {
    protected  final Log log= LogFactory.getLog(getClass());
    protected Class<T> persistentClass;

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    public void setPersistentClass(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    public void save(T object) {
        this.getHibernateTemplate().merge(object);
    }
    public T get(PK id) {
        T entity=super.getHibernateTemplate().get(this.persistentClass,id);
        Hibernate.initialize(entity);
        if(entity==null){
            log.warn("Uh oh,"+this.persistentClass+"'Object with id'"+id+"'not found'");
            throw new ObjectRetrievalFailureException(this.persistentClass,id);
        }
        return entity;
    }
}
