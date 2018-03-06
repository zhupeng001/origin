package wonder.action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.JedisPoolConfig;
import wonder.base.BaseAction;
import wonder.entity.User;
import wonder.redis.JedisClusterFactory;
import wonder.redis.cache.UserRedisCacheManager;
import wonder.service.UserManager;
import wonder.util.StringUtil;

import java.util.UUID;

public class UserAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	private String userName;

	private UserManager userManager;

	private Long id;

	private UserRedisCacheManager userRedisCacheManager;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public UserRedisCacheManager getUserRedisCacheManager() {
		return userRedisCacheManager;
	}

	public void setUserRedisCacheManager(UserRedisCacheManager userRedisCacheManager) {
		this.userRedisCacheManager = userRedisCacheManager;
	}

	public String addUser(){
		User user=new User(UUID.randomUUID().toString(),"龙","15859120753","男","细守镇");
		userRedisCacheManager.save(user);
		return "success";
	}
	public String get(){
        System.out.println("查询用户信息:"+userName);
		User user=userManager.getUserByName(userName);
		System.out.println(user);
		return "success";
    }
	public String getUserFromRedisCache(){
		User user=userRedisCacheManager.get(userName);
		System.out.println(user);
		return "success";
	}
}
