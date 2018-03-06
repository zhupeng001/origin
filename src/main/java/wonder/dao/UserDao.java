package wonder.dao;

import wonder.base.GenericDao;
import wonder.entity.User;

/**
 * Created by apple on 2018/2/16.
 */
public interface UserDao extends GenericDao<User,String>{
    public User getUser(String userName);
}
