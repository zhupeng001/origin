package wonder.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wonder.base.GenericManager;
import wonder.entity.User;

/**
 * Created by apple on 2018/2/16.
 */
public interface UserManager extends GenericManager<User,String> {
    public User getUserByName(String userName);
}
