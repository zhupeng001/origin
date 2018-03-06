package wonder.dao.impl;

import wonder.base.GenericDaoHibernate;
import wonder.dao.UserDao;
import wonder.entity.User;

import java.util.List;

/**
 * Created by apple on 2018/2/16.
 */
public class UserDaoHibernate extends GenericDaoHibernate<User,String> implements UserDao {

    public User getUser(String userName) {
        List<User> list= (List<User>) this.getHibernateTemplate().find("from User u where u.userName=?",userName);
        User user=null;
        if(list!=null){
            user=list.get(0);
        }
        return user;
    }
}
