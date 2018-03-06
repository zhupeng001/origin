package wonder.service.impl;

import org.springframework.transaction.annotation.Transactional;
import wonder.base.GenericManagerImpl;
import wonder.dao.UserDao;
import wonder.entity.User;
import wonder.service.UserManager;

import javax.annotation.Resource;

/**
 * Created by apple on 2018/2/16.
 */
@Transactional
public class UserManagerImpl extends GenericManagerImpl<User,String> implements UserManager{
    private UserDao userDao;

    public UserManagerImpl(UserDao userDao){
        super(userDao);
        this.userDao=userDao;
    }
    @Override
    public void save(User user){
        super.save(user);
    };

    public User getUserById(Long id) {
        return null;
    }

    public User getUserByName(String userName) {
        return this.userDao.getUser(userName);
    }
    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


}
