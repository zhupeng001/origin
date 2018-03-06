package wonder.redis.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import wonder.entity.User;
import wonder.redis.RedisCacheManager;
import wonder.service.UserManager;
import wonder.util.SerializeUtil;
import wonder.util.StringUtil;

/**
 * Created by apple on 2018/2/16.
 */
public class UserRedisCacheManager extends RedisCacheManager<User,String> {
    private static final Log log= LogFactory.getLog(UserRedisCacheManager.class);

    private UserManager userManager;

    public UserRedisCacheManager(){
        this.key="User";
    }
    @Override
    protected User createObject(String userName) {
        if(userName==null){
            return null;
        }
        return getUserManager().getUserByName(userName);
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public void save(User user) {
        this.redisManager.getJedis().set(this.getCacheKey(user.getUserName()).getBytes(),SerializeUtil.serialize(user));
    }
    public User getUserInfoByUserName(String userName){
        byte[] user=this.redisManager.getJedis().get(this.getCacheKey(userName).getBytes());
        return (User) SerializeUtil.unserialize(user);
    }
}
