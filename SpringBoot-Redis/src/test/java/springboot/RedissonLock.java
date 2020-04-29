package springboot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

@SpringBootTest
public class RedissonLock {
//set lock val ex 10 nx 设置lock : val 超时10秒  不存在时
/*	String script =
			"if redis.call('get', KEY[1]) == ARGV[1] then
				return redis.call('del',KEY[1])"
			else
				return 0
			end";
	Jedis.eval(script,co)	
*/		
	
	@Autowired
	RedisTemplate<Object, Object> redisTemplate; 
	
	@Test
	void lockDemo() throws Exception {
		long token = Thread.currentThread().getId();
		//加锁
		Boolean  lock= redisTemplate.opsForValue().setIfAbsent("lock", token, 3, TimeUnit.SECONDS);
		
		if(lock && lock!=null) {
			//业务代码
			System.out.println("处理中。。。。");
			Thread.sleep(3000);
			//删锁
			String lua="if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
			DefaultRedisScript<Long> script = new DefaultRedisScript<Long>(lua, Long.class);
			script.setResultType(Long.class);
			List<Object> list = new ArrayList<Object>();
			list.add("lock");
			long res= redisTemplate.execute(script, list, token);
			System.err.println(res);
		}else {
			lockDemo();
		}
		
	}
	
/*============================ redisson锁测试===============================================*/	

	
	@Autowired
	RedissonClient redisson;

	@Test
	public void sonLock() throws InterruptedException {
		RLock rlock =redisson.getLock("lock");
		
		//加锁
		rlock.lock();
		
		//业务代码
		System.out.println("处理中。。。。");
		Thread.sleep(3000);
		
		//删锁
		rlock.unlock();
		
		
		/**
		 * 在lock后
		 * 其它需要锁的会订阅实时感知删锁
		 *  
		 *  https://github.com/redisson/redisson/wiki/目录
		 *   
		 */
	}

	
}
