package springboot;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.tomcat.jni.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import springboot.bean.People;

/**
 * @CacheConfig 抽取缓存的公共注解  如@CacheConfig(cacheNames = "users")
 *
 * */
@SpringBootTest
class SpringBootRedis {
	/**
	 * springboot 自带缓存实现缓存规范CacheManager Cache 内部用ConcurrentMap<object, object> 
	 *  @EnableCaching 开启缓存
	 *  @Cacheable  把方法参数和对应结果缓存
	 *  @CachePut   缓存更新
	 *  @CacheEvict 清空缓存  
	 *  @
	 *   */
	
	/**
	 *  @Cacheable
	 *  1.先去cacheable中查找
	 *  2.在执行方法体
	 *  key:id 
	 *  value: user
	 *   */
	@Cacheable(cacheNames = "users")
	public User getUser(String id) {
		return new User();
	}

	/**
	 *  @CachePut
	 *  1.先方法体
	 *  2.再放入cache
	 *  key:user.id
	 *  value:true
	 *   */
	@CachePut(cacheNames = "users", key="#user.id")
	public User updateUser(User user) {
		return user;
	}
	
	/**
	 *  @Caching 组合注解
	 *  
	 *  */
	@Caching(cacheable ={ @Cacheable(cacheNames = "user",key = "#id")},
			put = {@CachePut(cacheNames = "user",key = "#user.email")},
			evict = {}
	)
	public User getEmail(String id) {
		return new User();
	}
	
/******************************* redis *******************************/
	/**
	 * JSR107 缓存规范
	 *   RedisCacheManager    RedisCache
	 *   
	 **/
	
	@Autowired
	StringRedisTemplate strRedis;
	
	@Test
	void contextLoads1() {
		strRedis.boundValueOps("string").set("string key string value test");
		String v1 = strRedis.opsForValue().get("string");
		System.out.println("string ,string "+ v1);
	}

	@Autowired
	RedisTemplate<Object, Object> jacksonRedisTemplate;

	@Test
	void contextLoads2() {
		People people = new People();
		people.setName("haha");
		people.setAge(19);
		System.out.println(people);
		String key1="ha";
		jacksonRedisTemplate.opsForValue().set(key1, people);
		People people1 =(People)jacksonRedisTemplate.opsForValue().get(key1);
		System.out.println("people1---"+people1);
		
		String key2="aa";
		jacksonRedisTemplate.opsForValue().set(key2, "key");
		String str =(String) jacksonRedisTemplate.opsForValue().get(key2);
		System.out.println("str---"+str);
		
	}

	
}
